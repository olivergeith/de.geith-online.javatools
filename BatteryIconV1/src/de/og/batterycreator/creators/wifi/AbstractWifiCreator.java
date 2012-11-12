package de.og.batterycreator.creators.wifi;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;

import de.og.batterycreator.creators.AbstractCreator;
import de.og.batterycreator.main.IconCreatorFrame;

/**
 * @author Oliver
 * 
 */
public abstract class AbstractWifiCreator extends AbstractCreator {

	// ###############################################################################
	// Abstracte Methoden
	// ###############################################################################
	public abstract ImageIcon createImage(int level, boolean fully);

	public abstract ImageIcon createInOutImage(boolean in, boolean out);

	protected Color getConnectColor(final boolean fully) {
		Color col = settings.getWifiColor();
		if (fully == true)
			col = settings.getWifiColorFully();
		return col;
	}

	// ###############################################################################
	// Creating Images
	// ###############################################################################
	@Override
	public void createAllImages() {
		System.out.println("Wifi: Creating Icons!");
		iconMap.removeAllElements();
		filenames.removeAllElements();
		filenamesAndPath.removeAllElements();
		createImages();
		createFullyImages();
		createInOutImages();
		overview = createOverview();
	}

	private void createFullyImages() {
		for (int i = 0; i < 5; i++) {
			filenames.add(getFileName(i, true));
			filenamesAndPath.add(getFilenameAndPath(i, true));
			iconMap.add(createImage(i, true));
		}
	}

	private void createImages() {
		for (int i = 0; i < 5; i++) {
			filenamesAndPath.add(getFilenameAndPath(i, false));
			filenames.add(getFileName(i, false));
			iconMap.add(createImage(i, false));
		}

	}

	private void createInOutImages() {
		filenames.add(settings.getFileWifiIn());
		iconMap.add(createInOutImage(true, false));
		filenames.add(settings.getFileWifiOut());
		iconMap.add(createInOutImage(false, true));
		filenames.add(settings.getFileWifiInOut());
		iconMap.add(createInOutImage(true, true));

		filenamesAndPath.add(getPath() + File.separator + settings.getFileWifiIn());
		filenamesAndPath.add(getPath() + File.separator + settings.getFileWifiOut());
		filenamesAndPath.add(getPath() + File.separator + settings.getFileWifiInOut());

	}

	// ###############################################################################
	// Filename for percentage Images
	// ###############################################################################
	protected String getFileNameInOut(final boolean in, final boolean out) {
		if (in && out)
			return settings.getFileWifiInOut();
		if (in && !out)
			return settings.getFileWifiIn();
		if (!in && out)
			return settings.getFileWifiOut();
		return "";
	}

	protected String getFileName(final int level, final boolean fully) {
		String filename;
		if (!fully)
			filename = settings.getFileWifiPattern() + level + ".png";
		else
			filename = settings.getFileWifiPattern() + level + settings.getFileWifiEXtensionFully() + ".png";

		// Sonderbehandlung f�r null image
		if (fully == true && level == 0)
			filename = settings.getFileWifiPattern() + "null.png";

		return filename;
	}

	private String getFilenameAndPath(final int level, final boolean fully) {
		final String filename = getPath() + File.separator + getFileName(level, fully);
		return filename;
	}

	// ###############################################################################
	// Creating Overview
	// ###############################################################################
	@Override
	public ImageIcon createOverview() {
		System.out.println("Wifi: Creating Overview!");
		if (iconMap != null && iconMap.size() > 0) {
			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();
			final int w = iw * 10 + 21;
			final int offsetOben = 50;
			final int offsetUnten = 35;
			final int h = ih * iconMap.size() + iconMap.size() + 1 + offsetOben + offsetUnten;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, w, h);
			g2d.setColor(Color.white);
			g2d.drawString(getCreatorName(), 2, 20);
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
			g2d.setColor(Color.gray);
			g2d.drawString("Created with ''The Battery Icon Creator'' V" + IconCreatorFrame.VERSION_NR + " by OlliG", 2, 32);
			g2d.drawString("http://forum.xda-developers.com/showthread.php?t=1918500", 2, h - offsetUnten + 20);
			g2d.setColor(Color.white);
			g2d.fillRect(0, 40, w, 2);
			g2d.fillRect(0, h - offsetUnten, w, 2);
			g2d.fillRect(0, h - 2, w, 2);

			g2d.setColor(Color.white);
			for (int i = 0; i < iconMap.size(); i++) {
				final ImageIcon img = iconMap.elementAt(i);
				g2d.drawImage(img.getImage(), 1, 1 + i * (ih + 1) + offsetOben, null);
				g2d.drawString(filenames.elementAt(i), 5 + iw, (i + 1) * (ih + 1) - 4 + offsetOben);
			}

			writeOverviewFile(over);
			return new ImageIcon(over);
		}
		return null;
	}

	protected Polygon createUpDownTriangle(final int x, final int y, final int w, final int h) {
		final Polygon p = new Polygon();
		p.addPoint(x, y);
		p.addPoint(x + w, y);
		p.addPoint(x + w / 2, y + h);
		return p;
	}

	protected Polygon createLeftRightTriangle(final int x, final int y, final int w, final int h) {
		final Polygon p = new Polygon();
		p.addPoint(x, y);
		p.addPoint(x, y + w);
		p.addPoint(x + h, y + w / 2);
		return p;
	}

	public ImageIcon getOverviewIcon() {
		return overview;
	}
}