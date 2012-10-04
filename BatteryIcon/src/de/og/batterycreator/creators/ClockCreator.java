package de.og.batterycreator.creators;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class ClockCreator extends DefaultCreator {

	protected static String path = "./pngs/clockbattery";
	protected static String name = "ClockBattery";

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {

		// Create a graphics contents on the buffered image
		final BufferedImage img = new BufferedImage(41, 41, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = img.createGraphics();
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(4f));

		// g2d.setColor(settings.getIconColorInActiv());
		// g2d.fillArc(2, 2, 37, 37, 0, 360);

		for (int j = 0; j < 100; j = j + 5) {
			drawRect(j, g2d, charge, percentage > j, percentage);
		}
		drawPercentage(g2d, percentage, charge, img);

		// Filewriting
		writeFile(percentage, charge, img);
		return new ImageIcon(img);
	}

	private void drawRect(final int winkel, final Graphics2D g2d, final boolean charge, final boolean activ, final int percentage) {
		Color col = settings.getIconColorInActiv();
		if (activ) {
			col = settings.getActivIconColor(percentage, charge);
		} else {
			// if (charge)
			// col = Color.green.darker();
		}
		g2d.setColor(col);
		final int w = Math.round(180 - (3.6f * winkel));
		final int r = 18;
		final int x = (int) Math.round(r * Math.sin(w * Math.PI / 180));
		final int y = (int) Math.round(r * Math.cos(w * Math.PI / 180));

		g2d.fillArc(19 + x, 19 + y, 4, 4, 0, 360);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.DefaultCreator#getPath()
	 */
	@Override
	public String getPath() {// TODO Auto-generated method stub
		return path;
	}

	@Override
	public String toString() {
		return name;
	}
}
