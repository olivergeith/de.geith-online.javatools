package og.basics.jgoodies;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class JGoodiesHelper {

	public JGoodiesHelper() {
		// TODO Auto-generated constructor stub
	}

	public static JLabel createGroupLabel(final String txt) {
		final JLabel lab = createColoredFontLabel(txt, new Font(Font.SANS_SERIF, Font.BOLD, 14), Color.BLUE.darker().darker());
		lab.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
		return lab;
	}

	/**
	 * Creates a Label with a very small blue font
	 * 
	 * @param txt
	 * @return
	 */
	public static JLabel createBlackLabel(final String txt) {
		final JLabel lab = createColoredFontLabel(txt, new Font(Font.SANS_SERIF, Font.BOLD, 10), Color.black);
		lab.setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0));
		return lab;
	}

	/**
	 * Creates a Label with a very small blue font
	 * 
	 * @param txt
	 * @return
	 */
	public static JLabel createBlueLabel(final String txt) {
		final JLabel lab = createColoredFontLabel(txt, new Font(Font.SANS_SERIF, Font.BOLD, 10), Color.blue);
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
