package de.og.batterycreator.creators;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
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

/**
 * @author Oliver
 * 
 */
public abstract class DefaultCreator {

	private final Vector<ImageIcon> iconMap = new Vector<ImageIcon>();
	private final Vector<String> filenames = new Vector<String>();;

	@Override
	public abstract String toString();

	/**
	 * Wo soll gepseichert werden?
	 * 
	 * @return
	 */
	public abstract String getPath();

	public String getName() {
		return toString();
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
				// Farbe f�r Schrift
				g2d.setColor(settings.getActivFontColor(percentage, charge));
				final String str = "" + percentage;
				final Rectangle2D strRect = metrix.getStringBounds(str, g2d);
				final int strxpos = img.getWidth() / 2 - (int) strRect.getWidth() / 2;
				final int strypos = img.getHeight() / 2 + 8;

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
			final int x = img.getWidth() / 2 - w / 2;
			final int y = img.getHeight() / 2 - h / 2;
			g2d.drawImage(chargeIcon.getImage(), x, y, null);
		}
	}

	// ###############################################################################
	// Writing Stuff to Filesystem
	// ###############################################################################
	protected void writeFile(final int percentage, final boolean charge, final BufferedImage img) {
		// Pfad anlegen falls nicht vorhanden
		final File pa = new File(getPath() + File.separator);
		pa.mkdirs();
		final String filename = getFileName(percentage, charge);
		final File file = new File(getPath() + File.separator + filename);
		try {
			ImageIO.write(img, "png", file);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		// Sonderbehandlung um das Full Image zu schreiben
		if (percentage == 100) {
			writeFileFull(charge, img);
		}
	}

	private void writeFileFull(final boolean charge, final BufferedImage img) {
		final File file = new File(getFilenameAndPathFull(charge));
		try {
			ImageIO.write(img, "png", file);
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

			final String filename = "./settings/" + getName() + ".cfg";

			final FileOutputStream file = new FileOutputStream(filename);
			final ObjectOutputStream o = new ObjectOutputStream(file);
			o.writeObject(settings);
			o.close();
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

			final String filename = "./settings/" + getName() + ".cfg";

			final FileInputStream file = new FileInputStream(filename);
			final ObjectInputStream o = new ObjectInputStream(file);
			settings = (StyleSettings) o.readObject();
			o.close();
		} catch (final IOException e) {
			System.err.println(e);
		} catch (final ClassNotFoundException e) {
			System.err.println(e);
		}
	}

}
