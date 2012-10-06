package de.og.batterycreator.creators;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class ArcCreator extends DefaultCreator {

	protected static String path = "./pngs/arc";
	protected static String name = "ArcBattery";

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
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(4f));

		g2d.setColor(settings.getIconColorInActiv());
		g2d.drawArc(2, 2, 37, 37, 0, 360);

		g2d.setColor(settings.getActivIconColor(percentage, charge));
		g2d.drawArc(2, 2, 37, 37, 87, -Math.round(percentage * (360f / 103.5f)));

		drawPercentage(g2d, percentage, charge, img);

		// resize ?
		if (settings.getTargetIconSize() != img.getHeight()) {
			// do the resizing before save

		}

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
