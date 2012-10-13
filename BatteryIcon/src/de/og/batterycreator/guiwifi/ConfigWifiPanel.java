package de.og.batterycreator.guiwifi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import og.basics.gui.Jcolorselectbutton.JColorSelectButton;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.og.batterycreator.creatorswifi.WifiSettings;

public class ConfigWifiPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private WifiSettings settings;

	public ConfigWifiPanel() {
		initComponents();

		myInit();
	}

	private void initComponents() {
	}

	private void myInit() {
		// setBackground(Color.black);
		setLayout(new BorderLayout());
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 96dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;
		builder.add(createGroupLabel("Percentages..."), cc.xyw(2, ++row, 1));
		builder.addSeparator("", cc.xyw(2, ++row, 1));

		final JPanel cfp = builder.getPanel();
		// cfp.setBackground(Color.black);
		this.add(cfp, BorderLayout.CENTER);
	}

	private JButton createClickabelColorLabel(final String text, final String tooltip) {
		final JColorSelectButton label = new JColorSelectButton();
		label.setText(text);
		label.setToolTipText(tooltip);
		return label;
	}

	private JLabel createGroupLabel(final String txt) {
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
	private JLabel createBlueDeviderLabel(final String txt) {
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
	private JLabel createColoredFontLabel(final String txt, final Font font, final Color color) {
		final JLabel label = new JLabel(txt);
		label.setForeground(color);
		label.setFont(font);
		return label;
	}

	/**
	 * @param text
	 *            Text der Checkbox
	 * @param defaultselection
	 *            Defaultselection
	 * @return
	 */
	private JCheckBox createCheckbox(final String text, final String tooltip) {
		final JCheckBox cbox = new JCheckBox(text);
		cbox.setToolTipText(tooltip);
		cbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				validateControls();
			}
		});
		return cbox;
	}

	public void setSettings(final WifiSettings settings) {
		this.settings = settings;
		validateControls();
		this.repaint();
	}

	public WifiSettings getSettings() {
		return settings;
	}

	protected void validateControls() {
	}
}
