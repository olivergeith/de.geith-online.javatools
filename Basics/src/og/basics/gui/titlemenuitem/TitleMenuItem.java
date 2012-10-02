package og.basics.gui.titlemenuitem;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

public class TitleMenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;
	private final ImageIcon EXIT_ICON = new ImageIcon(this.getClass().getResource("exit.png"));

	public TitleMenuItem(String s) {
		super(s + "   ");
		setFont(getFont().deriveFont(Font.BOLD, 14));
		setEnabled(false);
	}

	@Override
	public void paintComponent(Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
		int h = getHeight();
		int w = getWidth();
		// Gradientfüllung für MenüItem Hintergrund
		Color c1 = new Color(0, 0, 255, 255);
		Color c2 = new Color(255, 0, 0, 255);
		GradientPaint gradientFill = new GradientPaint(0, 0, c1, w, 0, c2);
		g2.setPaint(gradientFill);
		g2.fillRect(0, 0, w - 1, h);
		g2.drawImage(EXIT_ICON.getImage(), w - 18, 5, null);
		// Text ausgeben
		g.setColor(Color.white);
		Font f = getFont();
		FontMetrics fm = getFontMetrics(f);
		int fh = fm.getHeight();
		g.setFont(f);
		g.drawString(getText(), 5, h - (h - fh) / 2 - fm.getDescent());
	}

}
