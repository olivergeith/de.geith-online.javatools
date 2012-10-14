package de.og.batterycreator.creators;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class BatterySymbolCreator extends AbstractIconCreator {

	protected static String name = "BatterySymbol";

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
		g2d.fillRect(5, 3, 31, 37); // Battery Border
		g2d.fillRect(10, 0, 21, 3); // Battery Knob

		g2d.setColor(settings.getIconColorInActiv());
		g2d.fillRect(7, 5, 27, 33); // Inner Battery

		final int h = Math.round(31f / 100f * percentage);

		g2d.setColor(settings.getActivIconColor(percentage, charge));
		g2d.fillRect(8, 6 + 31 - h, 25, h); // Battery Border

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
