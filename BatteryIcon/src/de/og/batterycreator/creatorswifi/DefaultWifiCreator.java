package de.og.batterycreator.creatorswifi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import og.basics.gui.file.FileDialogs;
import og.basics.gui.image.ImageResizer;
import de.og.batterycreator.creators.StyleSettings;
import de.og.batterycreator.main.IconCreatorFrame;

/**
 * @author Oliver
 * 
 */
public abstract class DefaultWifiCreator {

	private static final String TITLE_WIFI_SETTINGS = "WifiSettings";
	private static final String SETTINGS_EXTENSION = ".wcfg";
	private static final String SETTINGS_DIR = "./settings/";

	private final Vector<ImageIcon> iconMap = new Vector<ImageIcon>();
	private final Vector<String> filenames = new Vector<String>();;
	private ImageIcon overview = null;

	@Override
	public abstract String toString();

	public String getPath() {
		return "./pngs/" + toString();
	}

	public String getName() {
		return toString();
	}

	protected WifiSettings wifiSettings = new WifiSettings();
	protected StyleSettings stylSettings = new StyleSettings();

	// ###############################################################################
	// Settings
	// ###############################################################################
	public WifiSettings getWifiSettings() {
		return wifiSettings;
	}

	public void setWifiSettings(final WifiSettings settings) {
		wifiSettings = settings;
	}

	public StyleSettings getStylSettings() {
		return stylSettings;
	}

	public void setStylSettings(final StyleSettings stylSettings) {
		this.stylSettings = stylSettings;
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

	public void createAllImages() {
		iconMap.removeAllElements();
		filenames.removeAllElements();
		createImages();
		createFullyImages();
		createInOutImages();
		overview = createOverview();
	}

	private void createFullyImages() {
		for (int i = 0; i < 5; i++) {
			filenames.add(getFileName(i, true));
			iconMap.add(createImage(i, true));
		}
	}

	private void createImages() {
		for (int i = 0; i < 4; i++) {
			filenames.add(getFileName(i, false));
			iconMap.add(createImage(i, false));
		}

	}

	private void createInOutImages() {
		filenames.add(wifiSettings.getFileIn());
		iconMap.add(createInOutImage(true, false));
		filenames.add(wifiSettings.getFileOut());
		iconMap.add(createInOutImage(false, true));
		filenames.add(wifiSettings.getFileInOut());
		iconMap.add(createInOutImage(true, true));
	}

	public abstract ImageIcon createImage(int level, boolean fully);

	public abstract ImageIcon createInOutImage(boolean in, boolean out);

	// ###############################################################################
	// Writing Stuff to Filesystem
	// ###############################################################################
	protected BufferedImage writeFile(final String filename, BufferedImage img) {
		// Pfad anlegen falls nicht vorhanden
		final File pa = new File(getPath() + File.separator);
		pa.mkdirs();
		// resize ?
		if (stylSettings.getTargetIconSize() != img.getHeight()) {
			// do the resizing before save
			if (stylSettings.isUseAdvancedResize())
				img = ImageResizer.resizeAdvanced(img, stylSettings.getTargetIconSize());
			else
				img = ImageResizer.resize(img, stylSettings.getTargetIconSize());
		}
		// getting filename
		final File file = new File(getPath() + File.separator + filename);
		// the writing
		try {
			ImageIO.write(img, "png", file);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	private void writeOverviewFile(final BufferedImage overview) {
		final File file = new File(getPath() + File.separator + "overview_" + getName() + ".png");
		try {
			ImageIO.write(overview, "png", file);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	// ###############################################################################
	// Filename for percentage Images
	// ###############################################################################
	public String getFileName(final int level, final boolean fully) {
		String filename;
		if (!fully)
			filename = wifiSettings.getFilePattern() + level + ".png";
		else
			filename = wifiSettings.getFilePattern() + level + wifiSettings.getFileEXtensionFully() + ".png";

		// Sonderbehandlung für null image
		if (fully == true && level == 0)
			filename = wifiSettings.getFilePattern() + "null.png";

		return filename;
	}

	public ImageIcon getIcon(final int percentage, final boolean fully) {
		final String filename = getFilenameAndPath(percentage, fully);
		try {
			final ImageIcon icon = new ImageIcon(filename);
			return icon;
		} catch (final Exception e) {
			System.out.println("Icon " + filename + " not found");
		}
		return null;
	}

	public String getFilenameAndPath(final int level, final boolean fully) {
		final String filename = getPath() + File.separator + getFileName(level, fully);
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
			final File pa = new File(SETTINGS_DIR);
			if (!pa.exists())
				pa.mkdirs();
			final String filename = SETTINGS_DIR + getName() + SETTINGS_EXTENSION;
			final File saveFile = FileDialogs.saveFile(pa, new File(filename), SETTINGS_EXTENSION, TITLE_WIFI_SETTINGS);
			if (saveFile != null) {
				final FileOutputStream file = new FileOutputStream(saveFile);
				final ObjectOutputStream o = new ObjectOutputStream(file);
				o.writeObject(wifiSettings);
				o.close();
			}
		} catch (final IOException e) {
			System.err.println(e);
		}
	}

	public void loadSettings() {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR);
			if (!pa.exists())
				pa.mkdirs();

			// final String filename = "./settings/" + getName() + ".cfg";
			final File loadFile = FileDialogs.chooseFile(pa, SETTINGS_EXTENSION, TITLE_WIFI_SETTINGS);
			if (loadFile != null) {
				final FileInputStream file = new FileInputStream(loadFile);
				final ObjectInputStream o = new ObjectInputStream(file);
				wifiSettings = (WifiSettings) o.readObject();
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
			g2d.drawImage(img.getImage(), 1 + 0 * iw, 1 + 10 * (ih + 1) + offsetOben, null);

			// // Charge Icons
			for (int z = 0; z < 10; z++) {
				for (int e = 0; e < 10; e++) {
					final int index = 101 + z * 10 + e;
					final ImageIcon imgc = iconMap.elementAt(index);
					g2d.drawImage(imgc.getImage(), 1 + 10 * (iw + 1) + e * (iw + 1), 1 + z * (ih + 1) + offsetOben, null);
				}
			}
			final ImageIcon img100c = iconMap.elementAt(201);
			g2d.drawImage(img100c.getImage(), 1 + 10 * (iw + 1) + 0 * iw, 1 + 10 * (ih + 1) + offsetOben, null);

			writeOverviewFile(over);
			return new ImageIcon(over);
		}
		return null;
	}

	public ImageIcon getOverviewIcon() {
		return overview;
	}

	// ###############################################################################
	// Grafics2D
	// ###############################################################################
	protected Graphics2D initGrafics2D(final BufferedImage img) {
		final Graphics2D g2d = img.createGraphics();
		g2d.setFont(stylSettings.getFont());
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(stylSettings.getStrokewidth()));
		return g2d;
	}

}
