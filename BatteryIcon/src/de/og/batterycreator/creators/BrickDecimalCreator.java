package de.og.batterycreator.creators;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class BrickDecimalCreator extends DefaultCreator {

	protected static String path = "./pngs/decimalbrick";
	protected static String name = "DecimalBrickBattery";

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
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
		final FontMetrics metrix = g2d.getFontMetrics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		final int e = percentage % 10;
		final int z = percentage / 10;
		final int hd = percentage / 100;

		final int h = 3;

		for (int einer = 0; einer < 10; einer++) {
			final int y = 41 - (einer * 4 + 4);
			final int x = 33;
			final int w = 6;
			final Rectangle rec = new Rectangle(x, y, w, h);
			drawRect(rec, g2d, charge, e > einer, percentage);
		}
		for (int zehner = 0; zehner < 10; zehner++) {
			final int y = 41 - (zehner * 4 + 4);
			final int x = 1;
			final int w = 30;
			final Rectangle rec = new Rectangle(x, y, w, h);
			drawRect(rec, g2d, charge, z > zehner, percentage);
		}
		if (hd == 1) {
			final Rectangle rec = new Rectangle(1, 1, 39, 39);
			drawRect(rec, g2d, charge, true, percentage);
		}

		// Farbe für Schrift
		g2d.setColor(settings.getActivFontColor(percentage, charge));
		final String str = "" + percentage;
		final Rectangle2D strRect = metrix.getStringBounds(str, g2d);
		final int strxpos = 20 - (int) strRect.getWidth() / 2;
		final int strypos = 28;

		System.out.println("strypos=" + strypos + " strRect.getHeight()=" + strRect.getHeight());
		g2d.drawString(str, strxpos, strypos);
		// Filewriting
		writeFile(percentage, charge, img);
		return new ImageIcon(img);
	}

	private void drawRect(final Rectangle rect, final Graphics2D g2d, final boolean charge, final boolean activ, final int percentage) {
		Color col = settings.getIconColorInActiv();

		if (activ) {
			col = settings.getActivIconColor(percentage, charge);
		} else {
			// if (charge)
			// col = Color.green.darker();
		}
		g2d.setColor(col);
		g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
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
