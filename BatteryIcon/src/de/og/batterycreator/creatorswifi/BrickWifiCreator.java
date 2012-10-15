package de.og.batterycreator.creatorswifi;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class BrickWifiCreator extends AbstractWifiCreator {
	public static String name = "BrickWifiIcons";

	private static final int imgMitte = 20;
	private static final int imgWidth = 46;
	private static final int imgHeight = 41;
	private static final int height = 5;
	private static final int width = 5;
	private static final int gap = 3;

	public BrickWifiCreator() {
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public ImageIcon createImage(final int level, final boolean fully) {
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		if (level == 0 && fully == true) {
			for (int i = 0; i < 5; i++) {
				g2d.setColor(stylSettings.getIconColorInActiv());
				final Rectangle rect = new Rectangle(imgMitte - i * width, imgHeight - (i + 1) * (height + gap), (1 + 2 * i) * width, height);
				g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
			}
		} else {
			for (int i = 0; i < 5; i++) {
				if (fully == true)
					g2d.setColor(stylSettings.getIconColor());
				else
					g2d.setColor(stylSettings.getFontColor());

				final Rectangle rect = new Rectangle(imgMitte - i * width, imgHeight - (i + 1) * (height + gap), (1 + 2 * i) * width, height);
				if (i <= level)
					g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
			}
		}
		g2d.setColor(stylSettings.getIconColorInActiv());

		// in out inactiv
		g2d.drawLine(imgMitte - 1, imgHeight, 2, 10);
		g2d.drawLine(imgMitte + width - 1, imgHeight, imgWidth - 3, 10);

		// Filewriting
		img = writeFile(getFileName(level, fully), img);
		return new ImageIcon(img);
	}

	@Override
	public ImageIcon createInOutImage(final boolean in, final boolean out) {
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		if (in) {
			g2d.setColor(wifiSettings.getInColor());
			g2d.drawLine(imgMitte - 1, imgHeight, 2, 10);
		}
		if (out) {
			g2d.setColor(wifiSettings.getOutColor());
			g2d.drawLine(imgMitte + width, imgHeight, imgWidth - 2, 10);
		}

		// Filewriting
		img = writeFile(getFileNameInOut(in, out), img);
		return new ImageIcon(img);
	}

}