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

public class BrickBattNoGapCreator extends DefaultCreator {

	protected static String path = "./pngs/brickbattnogap";
	protected static String name = "BrickNoGapBattery";

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {

		// Create a graphics contents on the buffered image
		final BufferedImage img = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = img.createGraphics();
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		final FontMetrics metrix = g2d.getFontMetrics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (int j = 0; j <= 100; j++) {
			final int h = 4;
			final int w = 4;
			final int x = (j % 10) * 4;
			final int y = 36 - (j / 10) * 4;
			// System.out.println("j=" + j + " x=" + x + " y=" + y);

			final Rectangle rec = new Rectangle(x, y, w, h);
			drawRect(rec, g2d, charge, percentage > j, percentage);
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
	public String getPath() {// TODO Auto-generated method stub
		return path;
	}

	@Override
	public String toString() {
		return name;
	}

}
