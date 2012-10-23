package og.basics.jgoodies;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import og.basics.gui.Jcolorselectbutton.JColorSelectButton;

public class JGoodiesHelper {

	public JGoodiesHelper() {
		// TODO Auto-generated constructor stub
	}

	public static JButton createClickabelColorLabel(final String text, final String tooltip) {
		final JColorSelectButton label = new JColorSelectButton();
		label.setText(text);
		label.setToolTipText(tooltip);
		return label;
	}

	public static JLabel createGroupLabel(final String txt) {
		final JLabel lab = createColoredFontLabel(txt, new Font(Font.SANS_SERIF, Font.BOLD, 18), Color.BLUE.darker().darker());
		lab.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
		return lab;
	}

	/**
	 * Creates a Label with a very small blue font
	 * 
	 * @param txt
	 * @return
	 */
	public static JLabel createBlueDeviderLabel(final String txt) {
		final JLabel lab = createColoredFontLabel(txt, new Font(Font.SANS_SERIF, Font.BOLD, 10), Color.black);
		lab.setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0));
		return lab;
	}

	/**
	 * Creates a Label with a font and Color
	 * 
	 * @param txt
	 * @param font
	 * @param color
	 * @return
	 */
	public static JLabel createColoredFontLabel(final String txt, final Font font, final Color color) {
		final JLabel label = new JLabel(txt);
		label.setForeground(color);
		label.setFont(font);
		return label;
	}

}
