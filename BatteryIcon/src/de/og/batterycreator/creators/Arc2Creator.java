package de.og.batterycreator.creators;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Arc2Creator extends DefaultCreator {

	protected static String path = "./pngs/arc2";
	protected static String name = "Arc2Battery";

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

		g2d.setColor(settings.getIconColorInActiv());
		g2d.setStroke(new BasicStroke(2f));
		g2d.drawArc(2, 2, 37, 37, 0, 360);

		g2d.setStroke(new BasicStroke(4f));
		g2d.setColor(settings.getActivIconColor(percentage, charge));
		if (settings.isFlip())
			g2d.drawArc(2, 2, 37, 37, 90, -Math.round(percentage * (360f / 103.5f)));
		else
			g2d.drawArc(2, 2, 37, 37, 90, +Math.round(percentage * (360f / 103.5f)));

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
