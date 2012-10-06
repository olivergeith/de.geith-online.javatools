package de.og.batterycreator.creators;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class BatteryVerticalSymbolCreator extends DefaultCreator {

	protected static String path = "./pngs/batteryverticalsymbol";
	protected static String name = "BatteryVerticalSymbol";

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {

		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(41, 41, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = img.createGraphics();
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(Color.gray);
		g2d.fillRect(1, 5, 37, 31); // Battery Border
		g2d.fillRect(38, 10, 3, 21); // Battery Knob

		g2d.setColor(settings.getIconColorInActiv());
		g2d.fillRect(3, 7, 33, 27); // Inner Battery

		final int w = Math.round(31f / 100f * percentage);

		g2d.setColor(settings.getActivIconColor(percentage, charge));
		g2d.fillRect(4, 8, w, 25); // Battery Border

		// Schrift
		drawPercentage(g2d, percentage, charge, img);
		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.DefaultCreator#getPath()
	 */
	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String toString() {
		return name;
	}

}
