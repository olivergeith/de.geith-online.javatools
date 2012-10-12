package de.og.batterycreator.creators;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class DeletedArc2Creator extends DefaultCreator {

	protected static String path = "./pngs/arc2";
	protected static String name = "Arc2Battery";

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

		g2d.setColor(settings.getIconColorInActiv());
		g2d.setStroke(new BasicStroke(3f));
		final int diameter = 36;
		final int corner = 2;
		g2d.drawArc(corner, corner, diameter, diameter, 0, 360);

		g2d.setStroke(new BasicStroke(5f));
		g2d.setColor(settings.getActivIconColor(percentage, charge));
		if (settings.isFlip())
			g2d.drawArc(corner, corner, diameter, diameter, 93, -Math.round(percentage * (360f / 103f) - 3));
		else
			g2d.drawArc(corner, corner, diameter, diameter, 93, +Math.round(percentage * (360f / 103f) - 3));

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
