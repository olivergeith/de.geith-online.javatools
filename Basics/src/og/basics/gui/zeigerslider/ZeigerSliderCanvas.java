package og.basics.gui.zeigerslider;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

/**
 * Zeichnet das eigentliche Display
 * 
 * @author Geith
 * 
 */
class ZeigerSliderCanvas extends JComponent {
	private static final long serialVersionUID = 1L;

	private int zeigerPos = 1;
	private int value = 0;
	public static Color ZEIGER_COLOR = Color.RED;
	private final Rectangle touchRect = new Rectangle();

	/**
	 * definiert, ob oberhalb des Zeiger-Instuments ein Bargraf angezeigt wird
	 */
	private final boolean showsBarGraf = true;
	/**
	 * definiert, wie sich die isTouched-Mehtode verhält Wenn false, wird nur
	 * eine Selection des Zeigers erlaubt ansosnten kann man überall hinklicken
	 */
	private boolean quickedit = false;

	public ZeigerSliderCanvas() {
		this.setSize(50, 50);
	}

	@Override
	public void paintComponent(Graphics g) {

		if (showsBarGraf) {
			drawHorizontalBarGraf(g, 0, 15);
			drawHorizontalZeigerInstrument(g, 16);
		} else
			drawHorizontalZeigerInstrument(g, 0);
	}

	private void drawHorizontalBarGraf(Graphics g, int yPosition, int hight) {
		int h = this.getSize().height;
		int w = this.getSize().width;
		final Graphics2D g2 = (Graphics2D) g;
		final int barHight = hight;
		g2.setColor(new Color(172, 180, 235, 255));
		g2.fillRect(1, yPosition - barHight, w - 1, h - 1);
		Color b1 = new Color(128, 128, 128, 128);
		Color b2 = new Color(0, 0, 0, 128);
		GradientPaint gradientFill = new GradientPaint(1, h - barHight, b1, w - 1, h, b2);
		g2.setPaint(gradientFill);
		g2.fillRect(1, yPosition - barHight, zeigerPos, h - 1);
		// rahmen + schatten
		g2.setColor(Color.BLACK);
		g2.drawRect(0, yPosition, w - 1, barHight);
		g2.setColor(new Color(128, 128, 128, 192));
		g2.drawRect(1, yPosition + 1, w - 3, barHight - 2);

		g2.setColor(Color.WHITE);
		String v = "" + value;
		Font f = getFont();
		g.setFont(f.deriveFont(Font.BOLD, 14));
		FontMetrics fm = getFontMetrics(f);
		int vWidth = fm.stringWidth(v);
		g.drawString(v, w / 2 - vWidth / 2, 12);

	}

	private void drawHorizontalZeigerInstrument(Graphics g, int yPosition) {
		int h = getParent().getSize().height;
		int w = getParent().getSize().width;
		final Graphics2D g2 = (Graphics2D) g;
		final int barHight = h - yPosition;

		// skala malen
		Color c1 = new Color(192, 200, 255, 255);
		Color c2 = new Color(255, 255, 255, 255);
		GradientPaint gradientFill = new GradientPaint(1, h - barHight, c1, w, h, c2);
		g2.setPaint(gradientFill);
		g2.fillRect(1, h - barHight, w, barHight);

		// Skala-Strichlein
		for (int i = 1; i < 10; i++) {
			int abstand = w / 10;
			g2.setColor(Color.BLACK);
			g2.drawRect(i * abstand, h - 3, 1, 2);
			g2.drawRect(i * abstand, yPosition + 1, 1, 1);
			g2.setColor(new Color(128, 128, 128, 50));
			g2.drawRect(i * abstand, yPosition, 1, h);
			g2.drawRect(i * abstand + 1, h - 3, 1, 2);
			g2.drawRect(i * abstand + 1, yPosition + 1, 1, 2);
		}

		// rahmen + schatten
		g2.setColor(Color.BLACK);
		g2.drawRect(0, h - barHight, w - 1, barHight - 1);
		g2.setColor(new Color(128, 128, 128, 192));
		g2.drawRect(1, h - barHight + 1, w - 3, barHight - 3);

		// Zeiger + schatten
		touchRect.setLocation(zeigerPos - 2, h - barHight);
		touchRect.setSize(4, barHight - 5);
		g2.setColor(ZEIGER_COLOR);
		g2.drawRect(zeigerPos - 1, h - barHight + 1, 1, barHight - 7);
		g2.setColor(new Color(128, 128, 128, 50));
		g2.drawRect(zeigerPos, h - barHight + 1, 3, barHight - 5);
		g2.setColor(new Color(128, 128, 128, 164));
		g2.drawRect(zeigerPos + 1, h - barHight + 1, 1, barHight - 6);

	}

	/**
	 * Gibt zurück, ob das das Panel angefasst wurde Im Quickedit-Mode kommt
	 * immer true zurück. Ansonsten nur, wenn der Zeiger getouched ist.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isTouched(int x, int y) {
		if (isQuickedit())
			return true;
		return touchRect.contains(x, y);
	}

	/**
	 * Set den anzuzeigenden Value
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * 
	 * @param zeigerPos
	 */
	public void setZeigerPos(int zeigerPos) {
		if (zeigerPos > getWidth() - 2)
			this.zeigerPos = getWidth() - 2;
		else if (zeigerPos < 1)
			this.zeigerPos = 1;
		else
			this.zeigerPos = zeigerPos;
	}

	/**
	 * Ist das Canvas im Qickedit-Mode?
	 * 
	 * @return
	 */
	public boolean isQuickedit() {
		return quickedit;
	}

	/**
	 * Setzt den Quickedit-Mode true...das Bedienung ist erlaubt ohne vorher den
	 * Zeiger anzufassen false ...der Zeiger muss angefasst werden um den Wert
	 * zu ändern
	 * 
	 * @param quickedit
	 */
	public void setQuickedit(boolean quickedit) {
		this.quickedit = quickedit;
	}

}
