package de.og.batterycreator.creators;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @author Oliver
 * 
 */
public abstract class DefaultCreator {

	@Override
	public abstract String toString();

	/**
	 * Wo soll gepseichert werden?
	 * 
	 * @return
	 */
	public abstract String getPath();

	/**
	 * Creates one Image
	 * 
	 * @param percentage
	 * @param charge
	 */
	public abstract ImageIcon createImage(final int percentage, final boolean charge);

	protected StyleSettings settings = new StyleSettings();

	public StyleSettings getSettings() {
		return settings;
	}

	public void setSettings(final StyleSettings settings) {
		this.settings = settings;
	}

	/**
	 * Erzeugt alle Images
	 */
	public void createAllImages() {
		createImages();
		createChargeImages();

	}

	private void createChargeImages() {
		for (int i = 0; i <= 100; i++)
			createImage(i, false);
	}

	private void createImages() {
		for (int i = 0; i <= 100; i++)
			createImage(i, true);

	}

	protected void drawPercentage(final Graphics2D g2d, final int percentage, final boolean charge, final BufferedImage img) {
		if (settings.isShowFont()) {
			final FontMetrics metrix = g2d.getFontMetrics();
			// Farbe für Schrift
			g2d.setColor(settings.getActivFontColor(percentage, charge));
			final String str = "" + percentage;
			final Rectangle2D strRect = metrix.getStringBounds(str, g2d);
			final int strxpos = img.getWidth() / 2 - (int) strRect.getWidth() / 2;
			final int strypos = img.getHeight() / 2 + 8;

			g2d.drawString(str, strxpos, strypos);
		}
	}

	/**
	 * @param percentage
	 * @param charge
	 * @param img
	 */
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

	/**
	 * @param percentage
	 * @param charge
	 * @param img
	 */
	private void writeFileFull(final boolean charge, final BufferedImage img) {
		final String filename = getFileNameFull(charge);
		final File file = new File(getPath() + File.separator + filename);
		try {
			ImageIO.write(img, "png", file);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the Filename
	 * 
	 * @param percentage
	 *            the percentage
	 * @param charge
	 *            true for charge Images
	 * @return
	 */
	private String getFileNameFull(final boolean charge) {
		String filename;
		if (charge == false)
			filename = "stat_sys_battery_circle_full.png";
		else
			filename = "stat_sys_battery_circle_charge_animfull.png";
		return filename;
	}

	/**
	 * Creates the Filename
	 * 
	 * @param percentage
	 *            the percentage
	 * @param charge
	 *            true for charge Images
	 * @return
	 */
	public String getFileName(final int percentage, final boolean charge) {
		String filename;
		if (charge == false)
			filename = "stat_sys_battery_circle_" + percentage + ".png";
		else
			filename = "stat_sys_battery_circle_charge_anim" + percentage + ".png";
		return filename;
	}

	/**
	 * Besorgt das passende Imageicon
	 * 
	 * @param percentage
	 * @param charge
	 * @return
	 */
	public ImageIcon getIcon(final int percentage, final boolean charge) {
		final String filename = getPath() + File.separator + getFileName(percentage, charge);
		try {
			final ImageIcon icon = new ImageIcon(filename);
			return icon;
		} catch (final Exception e) {
			System.out.println("Icon " + filename + " not found");
		}
		return null;
	}

}
