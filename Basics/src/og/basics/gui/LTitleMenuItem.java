package og.basics.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JMenuItem;

import og.basics.gui.icon.CommonIconProvider;

public class LTitleMenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;

	public LTitleMenuItem(final String s) {
		super(s + "   ");
		setFont(getFont().deriveFont(Font.BOLD, 14));
		setEnabled(false);
	}

	@Override
	public void paintComponent(final Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
		final int h = getHeight();
		final int w = getWidth();
		// Gradientfüllung für MenüItem Hintergrund
		final Color c1 = new Color(0, 0, 255, 255);
		final Color c2 = new Color(255, 0, 0, 255);
		final GradientPaint gradientFill = new GradientPaint(0, 0, c1, w, 0, c2);
		g2.setPaint(gradientFill);
		g2.fillRect(0, 0, w - 1, h);
		g2.drawImage(CommonIconProvider.BUTTON_ICON_EXIT.getImage(), w - 18, 5, null);
		// g2.drawImage(LIconManager.ICON_WONDOWS_XP_CLOSE.getImage(), w-23, 1,
		// null);
		// Text ausgeben
		g.setColor(Color.white);
		final Font f = getFont();
		final FontMetrics fm = getFontMetrics(f);
		final int fh = fm.getHeight();
		g.setFont(f);
		g.drawString(getText(), 5, h - (h - fh) / 2 - fm.getDescent());
	}

}
