package de.og.batterycreator.creators;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class ArcDecimalCreator extends DefaultCreator {

	protected static String path = "./pngs/decimalarc";
	protected static String name = "DecimalArcBattery";

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
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(3f));

		final int einer = percentage % 10;
		final int zehner = percentage / 10;

		g2d.setColor(settings.getIconColorInActiv());

		g2d.setColor(settings.getActivIconColor(percentage, charge));
		g2d.drawArc(7, 7, 28, 28, 90, -(int) (einer * (360f / 10f)));
		g2d.drawArc(3, 3, 36, 36, 90, -(int) (zehner * (360f / 10f)));

		// Schrift
		drawPercentage(g2d, percentage, charge, img);
		// Filewriting
		writeFile(percentage, charge, img);
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
