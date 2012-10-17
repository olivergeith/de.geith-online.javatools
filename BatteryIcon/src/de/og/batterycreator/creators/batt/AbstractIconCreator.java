package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;

import og.basics.gui.image.ImageResizer;
import de.og.batterycreator.creators.AbstractCreator;
import de.og.batterycreator.main.IconCreatorFrame;

/**
 * @author Oliver
 * 
 */
public abstract class AbstractIconCreator extends AbstractCreator {

	// ###############################################################################
	// Abstracte Methoden
	// ###############################################################################
	public abstract ImageIcon createImage(final int percentage, final boolean charge);

	// ###############################################################################
	// Special features
	// ###############################################################################
	public boolean supportsFlip() {
		return false;
	}

	public boolean supportsStrokeWidth() {
		return false;
	}

	// ###############################################################################
	// Creating Images
	// ###############################################################################
	@Override
	public void createAllImages() {
		iconMap.removeAllElements();
		filenames.removeAllElements();
		filenamesAndPath.removeAllElements();
		createImages();
		createChargeImages();
		overview = createOverview();
	}

	private void createChargeImages() {
		for (int i = 0; i <= 100; i++) {
			filenamesAndPath.add(getFilenameAndPath(i, true));
			filenames.add(getFileName(i, true));
			iconMap.add(createImage(i, true));
		}
		filenamesAndPath.add(getFilenameAndPathFull(true));

	}

	private void createImages() {
		for (int i = 0; i <= 100; i++) {
			filenamesAndPath.add(getFilenameAndPath(i, false));
			filenames.add(getFileName(i, false));
			iconMap.add(createImage(i, false));
		}
		filenamesAndPath.add(getFilenameAndPathFull(false));
	}

	protected void drawPercentage(final Graphics2D g2d, final int percentage, final boolean charge, final BufferedImage img) {
		if (stylSettings.isShowFont()) {
			int yoff = 8;
			if (charge && stylSettings.isShowChargeSymbol()) {
				drawChargeIcon(g2d, img);
			} else {
				// Sonderbehandlung bei 100% --> Schrift kleiner machen
				if (percentage == 100 && stylSettings.getReduceFontOn100() < 0) {
					final Font font = stylSettings.getFont();
					final Font newfont = new Font(font.getName(), font.getStyle(), font.getSize() + stylSettings.getReduceFontOn100());
					g2d.setFont(newfont);
					// offset extra berechnen proportional zur verkleinerten
					// Font
					yoff = 8 + Math.round(stylSettings.getReduceFontOn100() / 2f);
				}
				final FontMetrics metrix = g2d.getFontMetrics();
				// Farbe f�r Schrift
				g2d.setColor(stylSettings.getActivFontColor(percentage, charge));
				final String str = "" + percentage;
				final Rectangle2D strRect = metrix.getStringBounds(str, g2d);
				final int strxpos = 1 + stylSettings.getFontXOffset() + (int) (Math.round(img.getWidth() / 2) - Math.round(strRect.getWidth() / 2));
				final int strypos = img.getHeight() / 2 + yoff + stylSettings.getFontYOffset();

				g2d.drawString(str, strxpos, strypos);
				// Schrift wieder normal machen!!!
				g2d.setFont(stylSettings.getFont());
			}
		} else if (charge && stylSettings.isShowChargeSymbol()) {
			drawChargeIcon(g2d, img);
		}
	}

	private void drawChargeIcon(final Graphics2D g2d, final BufferedImage img) {
		final ImageIcon chargeIcon = stylSettings.getChargeIcon();
		if (chargeIcon != null) {
			// Resize Charge Icon
			BufferedImage resizedChargeIcon = new BufferedImage(chargeIcon.getIconWidth(), chargeIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g = resizedChargeIcon.createGraphics();
			g.drawImage(chargeIcon.getImage(), 1, 1, null);
			if (stylSettings.isResizeChargeSymbol())
				resizedChargeIcon = ImageResizer.resizeAdvanced(resizedChargeIcon, stylSettings.getResizeChargeSymbolHeight());

			final int w = resizedChargeIcon.getWidth();
			final int h = resizedChargeIcon.getHeight();
			final int x = 1 + stylSettings.getIconXOffset() + img.getWidth() / 2 - w / 2;
			final int y = img.getHeight() / 2 - h / 2 + stylSettings.getIconYOffset();
			g2d.drawImage(resizedChargeIcon, x, y, null);
		}
	}

	// ###############################################################################
	// Writing Stuff to Filesystem
	// ###############################################################################
	protected BufferedImage writeFile(final int percentage, final boolean charge, BufferedImage img) {
		// Filename zusammenbasteln
		final String filename = getFileName(percentage, charge);
		img = writeFile(filename, img);
		// Sonderbehandlung um das Full Image zu schreiben
		if (percentage == 100) {
			writeFileFull(charge, img);
		}
		return img;
	}

	private void writeFileFull(final boolean charge, final BufferedImage img) {
		final File file = new File(getFilenameAndPathFull(charge));
		// hier schreiben wir direct, weil img schon resized ist!
		writePNG(img, file);
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
			filename = stylSettings.getFilePattern() + "_full.png";
		else
			filename = stylSettings.getFilePatternCharge() + "full.png";
		return filename;
	}

	// ###############################################################################
	// Filename for percentage Images
	// ###############################################################################
	public String getFileName(final int percentage, final boolean charge) {
		String filename;
		if (charge == false)
			filename = stylSettings.getFilePattern() + percentage + ".png";
		else
			filename = stylSettings.getFilePatternCharge() + percentage + ".png";
		return filename;
	}

	public String getFilenameAndPath(final int percentage, final boolean charge) {
		final String filename = getPath() + File.separator + getFileName(percentage, charge);
		return filename;
	}

	// ###############################################################################
	// Creating Overview
	// ###############################################################################
	@Override
	public ImageIcon createOverview() {
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

}