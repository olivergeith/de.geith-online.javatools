package de.og.batterycreator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import og.basics.gui.colorselectorobjects.JColorSelectButton;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.og.batterycreator.creators.StyleSettings;

public class ConfigPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private StyleSettings settings;

	JButton labelFontColor;
	JButton labelFontColorLowBatt;
	JButton labelFontColorMedBatt;
	JButton labelIconColor;
	JButton labelIconColorLowBatt;
	JButton labelIconColorMedBatt;
	JButton labelIconColorInactiv;
	JButton labelChargeColor;
	JCheckBox cboxColoredFont = createCheckbox("Percentages changes color", "...");
	JCheckBox cboxColoredIcon = createCheckbox("Icon changes color", "...");
	JCheckBox cboxShowFont = createCheckbox("Show percentages", "...");

	JSlider dialLowBatt = new JSlider(0, 30);
	JSlider dialMedBatt = new JSlider(30, 50);

	public ConfigPanel() {
		initComponents();

		myInit();
	}

	private void initComponents() {
		labelFontColor = createClickabelColorLabel("Main Color", "Color when normal battery-level");
		labelFontColorLowBatt = createClickabelColorLabel("LowBatt", "Color when low battery");
		labelFontColorMedBatt = createClickabelColorLabel("MedBatt", "Color when Med battery");

		labelIconColor = createClickabelColorLabel("Main Color", "Color when normal battery-level");
		labelIconColorInactiv = createClickabelColorLabel("Background", "Color for Background");

		labelIconColorLowBatt = createClickabelColorLabel("LowBatt", "Color when low battery");
		labelIconColorMedBatt = createClickabelColorLabel("MedBatt", "Color when Med battery");
		labelChargeColor = createClickabelColorLabel("ChargeColor", "Color when charging");
		dialLowBatt.setPreferredSize(new Dimension(50, 40));
		dialMedBatt.setPreferredSize(new Dimension(50, 40));

		dialLowBatt.setMajorTickSpacing(10);
		dialLowBatt.setMinorTickSpacing(5);
		dialLowBatt.setPaintTicks(true);
		dialLowBatt.setPaintLabels(true);
		dialLowBatt.setSnapToTicks(true);

		dialMedBatt.setMajorTickSpacing(10);
		dialMedBatt.setMinorTickSpacing(5);
		dialMedBatt.setPaintTicks(true);
		dialMedBatt.setPaintLabels(true);
		dialMedBatt.setSnapToTicks(true);

	}

	private void myInit() {
		// setBackground(Color.black);
		setLayout(new BorderLayout());
		final FormLayout layout = new FormLayout("2dlu, pref, 2dlu, pref, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;
		builder.add(createGroupLabel("FontColors"), cc.xyw(2, ++row, 3));
		builder.addSeparator("", cc.xyw(2, ++row, 3));
		builder.add(cboxShowFont, cc.xyw(2, ++row, 3));
		builder.add(labelFontColor, cc.xyw(2, ++row, 3));
		builder.add(cboxColoredFont, cc.xyw(2, ++row, 3));
		builder.add(labelFontColorLowBatt, cc.xyw(2, ++row, 1));
		builder.add(labelFontColorMedBatt, cc.xyw(4, row, 1));
		builder.add(createGroupLabel("IconColors"), cc.xyw(2, ++row, 3));
		builder.addSeparator("", cc.xyw(2, ++row, 3));
		builder.add(labelIconColor, cc.xyw(2, ++row, 1));
		builder.add(labelIconColorInactiv, cc.xyw(4, row, 1));
		builder.add(labelChargeColor, cc.xyw(2, ++row, 3));
		builder.addSeparator("", cc.xyw(2, ++row, 3));
		builder.add(cboxColoredIcon, cc.xyw(2, ++row, 3));
		builder.add(labelIconColorLowBatt, cc.xyw(2, ++row, 1));
		builder.add(labelIconColorMedBatt, cc.xyw(4, row, 1));
		builder.add(createGroupLabel("Thresholds..."), cc.xyw(2, ++row, 3));
		builder.addSeparator("", cc.xyw(2, ++row, 3));
		builder.add(createBlueDeviderLabel("...for Low and Med Battery-Levels"), cc.xyw(2, ++row, 3));
		builder.add(dialLowBatt, cc.xyw(2, ++row, 1));
		builder.add(dialMedBatt, cc.xyw(4, row, 1));

		final JPanel cfp = builder.getPanel();
		// cfp.setBackground(Color.black);
		this.add(cfp, BorderLayout.CENTER);
		this.add(createLogo(), BorderLayout.SOUTH);
	}

	private JButton createClickabelColorLabel(final String text, final String tooltip) {
		final JColorSelectButton label = new JColorSelectButton();
		label.setText(text);
		label.setToolTipText(tooltip);
		return label;
	}

	private JLabel createGroupLabel(final String txt) {
		return createColoredFontLabel(txt, new Font(Font.SANS_SERIF, Font.BOLD, 20), Color.BLUE.darker());
	}

	/**
	 * Creates a Label with a very small blue font
	 * 
	 * @param txt
	 * @return
	 */
	private JLabel createBlueDeviderLabel(final String txt) {
		return createColoredFontLabel(txt, new Font(Font.SANS_SERIF, Font.BOLD, 10), Color.BLUE.darker());
	}

	/**
	 * Erzeugt das Froschlogo mit dem hiddenfeature für Expertmode
	 * 
	 * @return
	 */
	private JLabel createLogo() {
		final JLabel logo = new JLabel(IconCreatorFrame.getLogoicon());
		return logo;
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

	public void setSettings(final StyleSettings settings) {
		this.settings = settings;
		labelFontColor.setBackground(settings.getFontColor());
		labelFontColorLowBatt.setBackground(settings.getFontColorLowBatt());
		labelFontColorMedBatt.setBackground(settings.getFontColorMedBatt());
		labelIconColor.setBackground(settings.getIconColor());
		labelIconColorLowBatt.setBackground(settings.getIconColorLowBatt());
		labelIconColorMedBatt.setBackground(settings.getIconColorMedBatt());
		labelIconColorInactiv.setBackground(settings.getIconColorInActiv());
		labelChargeColor.setBackground(settings.getChargeColor());
		cboxShowFont.setSelected(settings.isShowFont());
		cboxColoredFont.setSelected(settings.isColoredFont());
		cboxColoredIcon.setSelected(settings.isColoredIcon());
		dialMedBatt.setValue(settings.getMedBattTheshold());
		dialLowBatt.setValue(settings.getLowBattTheshold());
		validateControls();
		this.repaint();
	}

	public StyleSettings getSettings() {
		settings.setFontColor(labelFontColor.getBackground());
		settings.setFontColorLowBatt(labelFontColorLowBatt.getBackground());
		settings.setFontColorMedBatt(labelFontColorMedBatt.getBackground());
		settings.setIconColor(labelIconColor.getBackground());
		settings.setIconColorLowBatt(labelIconColorLowBatt.getBackground());
		settings.setIconColorMedBatt(labelIconColorMedBatt.getBackground());
		settings.setIconColorInActiv(labelIconColorInactiv.getBackground());
		settings.setChargeColor(labelChargeColor.getBackground());
		settings.setShowFont(cboxShowFont.isSelected());
		settings.setColoredFont(cboxColoredFont.isSelected());
		settings.setColoredIcon(cboxColoredIcon.isSelected());
		settings.setMedBattTheshold(dialMedBatt.getValue());
		settings.setLowBattTheshold(dialLowBatt.getValue());
		return settings;
	}

	protected void validateControls() {
		labelFontColor.setEnabled(cboxShowFont.isSelected());
		labelFontColorMedBatt.setEnabled(cboxShowFont.isSelected() && cboxColoredFont.isSelected());
		labelFontColorLowBatt.setEnabled(cboxShowFont.isSelected() && cboxColoredFont.isSelected());
		cboxColoredFont.setEnabled(cboxShowFont.isSelected());
		labelIconColorMedBatt.setEnabled(cboxColoredIcon.isSelected());
		labelIconColorLowBatt.setEnabled(cboxColoredIcon.isSelected());
	}

}
