package de.og.batterycreator.creators;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import og.basics.gui.file.FileDialogs;
import sun.awt.image.BufferedImageGraphicsConfig;
import de.og.batterycreator.main.IconCreatorFrame;

/**
 * @author Oliver
 * 
 */
public abstract class DefaultCreator {

	private final Vector<ImageIcon> iconMap = new Vector<ImageIcon>();
	private final Vector<String> filenames = new Vector<String>();;
	private ImageIcon overview = null;

	@Override
	public abstract String toString();

	public abstract String getPath();

	public String getName() {
		return toString();
	}

	public boolean supportsFlip() {
		return false;
	}

	public boolean supportsStrokeWidth() {
		return false;
	}

	protected StyleSettings settings = new StyleSettings();

	// ###############################################################################
	// Settings
	// ###############################################################################
	public StyleSettings getSettings() {
		return settings;
	}

	public void setSettings(final StyleSettings settings) {
		this.settings = settings;
	}

	// ###############################################################################
	// Creating Images
	// ###############################################################################
	/**
	 * Creates one Image
	 * 
	 * @param percentage
	 * @param charge
	 */
	public abstract ImageIcon createImage(final int percentage, final boolean charge);

	public void createAllImages() {
		iconMap.removeAllElements();
		filenames.removeAllElements();
		createImages();
		createChargeImages();
		overview = createOverview();
	}

	private void createChargeImages() {
		for (int i = 0; i <= 100; i++) {
			filenames.add(getFileName(i, true));
			iconMap.add(createImage(i, true));
		}
	}

	private void createImages() {
		for (int i = 0; i <= 100; i++) {
			filenames.add(getFileName(i, false));
			iconMap.add(createImage(i, false));
		}

	}

	protected void drawPercentage(final Graphics2D g2d, final int percentage, final boolean charge, final BufferedImage img) {
		if (settings.isShowFont()) {
			if (charge && settings.isShowChargeSymbol()) {
				drawChargeIcon(g2d, img);
			} else {
				final FontMetrics metrix = g2d.getFontMetrics();
				// Farbe für Schrift
				g2d.setColor(settings.getActivFontColor(percentage, charge));
				final String str = "" + percentage;
				final Rectangle2D strRect = metrix.getStringBounds(str, g2d);
				final int strxpos = 1 + settings.getFontXOffset() + (int) (Math.round(img.getWidth() / 2) - Math.round(strRect.getWidth() / 2));
				final int strypos = img.getHeight() / 2 + 8 + settings.getFontYOffset();

				g2d.drawString(str, strxpos, strypos);
			}
		} else if (charge && settings.isShowChargeSymbol()) {
			drawChargeIcon(g2d, img);
		}
	}

	private void drawChargeIcon(final Graphics2D g2d, final BufferedImage img) {
		final ImageIcon chargeIcon = settings.getChargeIcon();
		if (chargeIcon != null) {
			final int w = chargeIcon.getIconWidth();
			final int h = chargeIcon.getIconHeight();
			final int x = 1 + img.getWidth() / 2 - w / 2;
			final int y = img.getHeight() / 2 - h / 2;
			g2d.drawImage(chargeIcon.getImage(), x, y, null);
		}
	}

	// ###############################################################################
	// Writing Stuff to Filesystem
	// ###############################################################################
	protected BufferedImage writeFile(final int percentage, final boolean charge, BufferedImage img) {
		// Pfad anlegen falls nicht vorhanden
		final File pa = new File(getPath() + File.separator);
		pa.mkdirs();
		// resize ?
		if (settings.getTargetIconSize() != img.getHeight()) {
			// do the resizing before save
			if (settings.isUseAdvancedResize())
				img = resizeTrick(img, settings.getTargetIconSize());
			else
				img = resize(img, settings.getTargetIconSize());
		}
		// getting filename
		final String filename = getFileName(percentage, charge);
		final File file = new File(getPath() + File.separator + filename);
		// the writing
		try {
			ImageIO.write(img, "png", file);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		// Sonderbehandlung um das Full Image zu schreiben
		if (percentage == 100) {
			writeFileFull(charge, img);
		}
		return img;
	}

	private void writeFileFull(final boolean charge, final BufferedImage img) {
		final File file = new File(getFilenameAndPathFull(charge));
		try {
			ImageIO.write(img, "png", file);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private void writeOverviewFile(final BufferedImage overview) {
		final File file = new File(getPath() + File.separator + "overview.png");
		try {
			ImageIO.write(overview, "png", file);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	// ###############################################################################
	// Filename for Full Image
	// ###############################################################################
	public String getFilenameAndPathFull(final boolean charge) {
		return getPath() + File.separator + getFileNameFull(charge);
	}

	private String getFileNameFull(final boolean charge) {
		String filename;
		if (charge == false)
			filename = settings.getFilePattern() + "_full.png";
		else
			filename = settings.getFilePatternCharge() + "full.png";
		return filename;
	}

	// ###############################################################################
	// Filename for percentage Images
	// ###############################################################################
	public String getFileName(final int percentage, final boolean charge) {
		String filename;
		if (charge == false)
			filename = settings.getFilePattern() + percentage + ".png";
		else
			filename = settings.getFilePatternCharge() + percentage + ".png";
		return filename;
	}

	public ImageIcon getIcon(final int percentage, final boolean charge) {
		final String filename = getFilenameAndPath(percentage, charge);
		try {
			final ImageIcon icon = new ImageIcon(filename);
			return icon;
		} catch (final Exception e) {
			System.out.println("Icon " + filename + " not found");
		}
		return null;
	}

	public String getFilenameAndPath(final int percentage, final boolean charge) {
		final String filename = getPath() + File.separator + getFileName(percentage, charge);
		return filename;
	}

	// ###############################################################################
	// All filenames and Icons
	// ###############################################################################
	public Vector<String> getFilenames() {
		return filenames;
	}

	public Vector<ImageIcon> getIcons() {
		return iconMap;
	}

	// ###############################################################################
	// Persisting Settings
	// ###############################################################################
	public void persistSettings() {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File("./settings/");
			if (!pa.exists())
				pa.mkdirs();
			final String filename = "./settings/" + getName() + ".icfg";
			final File saveFile = FileDialogs.saveFile(pa, new File(filename), ".icfg", "IconSettings");
			if (saveFile != null) {
				final FileOutputStream file = new FileOutputStream(saveFile);
				final ObjectOutputStream o = new ObjectOutputStream(file);
				o.writeObject(settings);
				o.close();
			}
		} catch (final IOException e) {
			System.err.println(e);
		}
	}

	public void loadSettings() {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File("./settings/");
			if (!pa.exists())
				pa.mkdirs();

			// final String filename = "./settings/" + getName() + ".cfg";
			final File loadFile = FileDialogs.chooseFile(pa, ".icfg", "IconSettings");
			if (loadFile != null) {
				final FileInputStream file = new FileInputStream(loadFile);
				final ObjectInputStream o = new ObjectInputStream(file);
				settings = (StyleSettings) o.readObject();
				o.close();
			}
		} catch (final IOException e) {
			System.err.println(e);
		} catch (final ClassNotFoundException e) {
			System.err.println(e);
		}
	}

	// ###############################################################################
	// Creating Overview
	// ###############################################################################
	private ImageIcon createOverview() {
		if (iconMap != null && iconMap.size() > 100) {
			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();
			final int w = iw * 20 + 21;
			final int offsetOben = 50;
			final int offsetUnten = 35;
			final int h = ih * 11 + 12 + offsetOben + offsetUnten;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, w, h);
			g2d.setColor(Color.white);
			g2d.drawString(getName(), 2, 20);
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
			g2d.setColor(Color.gray);
			g2d.drawString("Created with ''The Battery Icon Creator'' V" + IconCreatorFrame.VERSION_NR + " by OlliG", 2, 32);
			g2d.drawString("http://forum.xda-developers.com/showthread.php?t=1918500", 2, h - offsetUnten + 20);
			g2d.setColor(Color.white);
			g2d.fillRect(0, 40, w, 2);
			g2d.fillRect(0, h - offsetUnten, w, 2);
			g2d.fillRect(0, h - 2, w, 2);

			for (int z = 0; z < 10; z++) {
				for (int e = 0; e < 10; e++) {
					final int index = z * 10 + e;
					final ImageIcon img = iconMap.elementAt(index);
					g2d.drawImage(img.getImage(), 1 + e * (iw + 1), 1 + z * (ih + 1) + offsetOben, null);
				}
			}
			final ImageIcon img = iconMap.elementAt(100);
			g2d.drawImage(img.getImage(), 1 + 0 * iw, 10 * (ih + 1) + offsetOben, null);

			// // Charge Icons
			for (int z = 0; z < 10; z++) {
				for (int e = 0; e < 10; e++) {
					final int index = 101 + z * 10 + e;
					final ImageIcon imgc = iconMap.elementAt(index);
					g2d.drawImage(imgc.getImage(), 1 + 10 * (iw + 1) + e * (iw + 1), 1 + z * (ih + 1) + offsetOben, null);
				}
			}
			final ImageIcon img100c = iconMap.elementAt(201);
			g2d.drawImage(img100c.getImage(), 1 + 10 * (iw + 1) + 0 * iw, 10 * (ih + 1) + offsetOben, null);

			writeOverviewFile(over);
			return new ImageIcon(over);
		}
		return null;
	}

	public ImageIcon getOverviewIcon() {
		return overview;
	}

	/**
	 * Method to resize an Image
	 * 
	 * @param img
	 * @param width
	 * @param height
	 * @return
	 */
	protected BufferedImage resize(final BufferedImage img, final int height) {
		// get the aspect ratio right...
		final int width = Math.round((float) img.getWidth() / (float) img.getHeight() * height);

		final int type = img.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : img.getType();
		final BufferedImage resizedImage = new BufferedImage(width, height, type);
		final Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(img, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	protected BufferedImage resizeTrick(BufferedImage image, final int height) {
		image = createCompatibleImage(image);
		image = resize(image, 100);
		image = blurImage(image);
		image = resize(image, height);
		return image;
	}

	private BufferedImage blurImage(final BufferedImage image) {
		final float ninth = 1.0f / 9.0f;
		final float[] blurKernel = {
				ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth
		};

		final Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
		map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		final RenderingHints hints = new RenderingHints(map);
		final BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, hints);
		return op.filter(image, null);
	}

	private static BufferedImage createCompatibleImage(final BufferedImage image) {
		final GraphicsConfiguration gc = BufferedImageGraphicsConfig.getConfig(image);
		final int w = image.getWidth();
		final int h = image.getHeight();
		final BufferedImage result = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
		final Graphics2D g2 = result.createGraphics();
		g2.drawRenderedImage(image, null);
		g2.dispose();
		return result;
	}

	// ###############################################################################
	// Grafics2D
	// ###############################################################################
	protected Graphics2D initGrafics2D(final BufferedImage img) {
		final Graphics2D g2d = img.createGraphics();
		g2d.setFont(settings.getFont());
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(settings.getStrokewidth()));
		return g2d;
	}

}
