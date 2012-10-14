package de.og.batterycreator.creatorswifi;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class BrickWifiCreator extends AbstractWifiCreator {
	public static String name = "BrickWifiIcons";

	public BrickWifiCreator() {
	}

	@Override
	public String toString() {
		return name;
	}

	final int height = 5;
	final int width = 5;
	final int gap = 3;

	@Override
	public ImageIcon createImage(final int level, final boolean fully) {
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(45, 41, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);
		g2d.setStroke(new BasicStroke(2f));

		if (level == 0 && fully == true) {
			for (int i = 0; i < 5; i++) {
				g2d.setColor(stylSettings.getIconColorInActiv());
				final Rectangle rect = new Rectangle(19 - i * width, 41 - (i + 1) * (height + gap), (1 + 2 * i) * width, height);
				g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
			}
		} else {
			for (int i = 0; i < 5; i++) {
				if (fully == true)
					g2d.setColor(stylSettings.getIconColor());
				else
					g2d.setColor(stylSettings.getIconColorInActiv());

				final Rectangle rect = new Rectangle(19 - i * width, 41 - (i + 1) * (height + gap), (1 + 2 * i) * width, height);
				if (i <= level)
					g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
			}
		}
		// Filewriting
		img = writeFile(getFileName(level, fully), img);
		return new ImageIcon(img);
	}

	@Override
	public ImageIcon createInOutImage(final boolean in, final boolean out) {
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(41, 41, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		final Rectangle rectin = new Rectangle(10, 41 - (height + 1), width, height);
		final Rectangle rectout = new Rectangle(31 - width, 41 - (height + 1), width, height);
		if (in) {
			g2d.setColor(wifiSettings.getInColor());
			g2d.fillRect(rectin.x, rectin.y, rectin.width, rectin.height);
		}
		if (out) {
			g2d.setColor(wifiSettings.getOutColor());
			g2d.fillRect(rectout.x, rectout.y, rectout.width, rectout.height);
		}

		// Filewriting
		img = writeFile(getFileNameInOut(in, out), img);
		return new ImageIcon(img);
	}

}
