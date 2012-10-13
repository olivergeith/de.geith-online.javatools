package de.og.batterycreator.creators;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class BatteryVerticalSymbolCreator extends DefaultCreator {

	protected static String name = "BatteryVerticalSymbol";

	@Override
	public boolean supportsFlip() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {

		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(41, 41, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		g2d.setColor(Color.gray);
		final int w = Math.round(31f / 100f * percentage);

		if (!settings.isFlip()) {
			g2d.fillRect(1, 5, 37, 31); // Battery Border
			g2d.fillRect(38, 10, 3, 21); // Battery Knob

			g2d.setColor(settings.getIconColorInActiv());
			g2d.fillRect(3, 7, 33, 27); // Inner Battery
			g2d.setColor(settings.getActivIconColor(percentage, charge));
			g2d.fillRect(4, 8, w, 25); // Battery Level
		} else {
			g2d.fillRect(3, 5, 37, 31); // Battery Border
			g2d.fillRect(1, 10, 3, 21); // Battery Knob

			g2d.setColor(settings.getIconColorInActiv());
			g2d.fillRect(5, 7, 33, 27); // Inner Battery
			g2d.setColor(settings.getActivIconColor(percentage, charge));
			g2d.fillRect(37 - w, 8, w, 25); // Battery Level
		}
		// Schrift
		drawPercentage(g2d, percentage, charge, img);
		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);
	}

	@Override
	public String toString() {
		return name;
	}

}
