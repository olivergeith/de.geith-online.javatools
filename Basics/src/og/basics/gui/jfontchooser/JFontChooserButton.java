package og.basics.gui.jfontchooser;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class JFontChooserButton extends JButton {
	private static final long serialVersionUID = 2100124217291023608L;
	private JFontChooser fontChooser = null;
	private final ImageIcon fontIcon = new ImageIcon(this.getClass().getResource("font.png"));
	private final ImageIcon fontsizeIcon = new ImageIcon(this.getClass().getResource("fontsize.png"));

	public JFontChooserButton(final String text, final String[] fontSizeStrings) {
		super(text);
		fontChooser = new JFontChooser(fontSizeStrings);
		initUI();
	}

	private void initUI() {
		setIcon(fontIcon);
		setToolTipText("Please choose a font!");
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				fontChooser.setSelectedFont(getFont());
				final int result = fontChooser.showDialog(JFontChooserButton.this);
				if (result == JFontChooser.OK_OPTION) {
					final Font font = fontChooser.getSelectedFont();
					setFont(font);
				}
			}
		});
	}
}
