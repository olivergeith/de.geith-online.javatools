package og.basics.grafics;

import java.awt.Graphics2D;

public class Draw2DFunktions {

	public Draw2DFunktions() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param g2d
	 *            the grafics context
	 * @param x
	 *            the center.x
	 * @param y
	 *            the center.y
	 * @param radius
	 *            the radius
	 * @param startWinkel
	 *            the startwinkel
	 * @param endWinkel
	 *            the endwinkel
	 */
	public static void drawCircle(final Graphics2D g2d, final int x, final int y, final int radius, final int startWinkel, final int endWinkel) {
		g2d.drawArc(x - radius, y - radius, 2 * radius, 2 * radius, startWinkel, endWinkel);
	}

	/**
	 * @param g2d
	 *            the grafics context
	 * @param x
	 *            the center.x
	 * @param y
	 *            the center.y
	 * @param radius
	 *            the radius
	 * @param startWinkel
	 *            the startwinkel
	 * @param endWinkel
	 *            the endwinkel
	 */
	public static void fillCircle(final Graphics2D g2d, final int x, final int y, final int radius, final int startWinkel, final int endWinkel) {
		g2d.fillArc(x - radius, y - radius, 2 * radius, 2 * radius, startWinkel, endWinkel);
	}

}
