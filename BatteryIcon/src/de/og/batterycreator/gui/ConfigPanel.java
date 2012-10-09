package de.og.batterycreator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import og.basics.gui.colorselectorobjects.JColorSelectButton;
import og.basics.gui.jfontchooser.JFontChooserButton;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.og.batterycreator.creators.RomPreset;
import de.og.batterycreator.creators.StyleSettings;
import de.og.batterycreator.widgets.ChargeIconSelector;
import de.og.batterycreator.widgets.SliderLabel;

public class ConfigPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private StyleSettings settings;
	final String fontSizes[] = {
			"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32"
	};

	JButton fontColor;
	JButton fontColorLowBatt;
	JButton fontColorMedBatt;
	JButton fontColorCharge;
	ChargeIconSelector chargeIconSeletor = new ChargeIconSelector();

	JButton iconColor;
	JButton iconColorLowBatt;
	JButton iconColorMedBatt;
	JButton iconColorInactiv;
	JButton iconColorCharge;

	SliderLabel sliderStroke = new SliderLabel(1, 10);
	JCheckBox cboxFlip = createCheckbox("Flip Icon", "Mirror's the Icon...ony has effect on a few styls!");

	JCheckBox cboxColoredFont = createCheckbox("Low battery Colors", "...");
	JCheckBox cboxColoredIcon = createCheckbox("Low battery Colors", "...");
	JCheckBox cboxShowFont = createCheckbox("Show percentages", "...");
	JCheckBox cboxShowChargeSymbol = createCheckbox("Charge-Symbol", "Show Charge-Symbol when charging");
	JCheckBox cboxUseGradientMediumLevels = createCheckbox("Gradient for Medium levels", "Use Gradient Colors between Low and Med Batterylevels");
	JCheckBox cboxUseGradientNormalLevels = createCheckbox("Gradient for Normal levels", "Use Gradient Colors between Med and 100% Batterylevels");

	SliderLabel sliderLowBatt = new SliderLabel(0, 30);
	SliderLabel sliderMedBatt = new SliderLabel(20, 100);

	SliderLabel sliderFontXOffset = new SliderLabel(-4, 4);
	SliderLabel sliderFontYOffset = new SliderLabel(-4, 4);

	SliderLabel sliderResize = new SliderLabel(25, 50);
	JCheckBox cboxUseAdvResize = createCheckbox("Use advanced Resize-Algorithm",
			"(Experimental) Advanced Resize-Algorith...might give better results on small imagesizes!?");

	JTextField filepattern = new JTextField();
	JTextField filepatternCharge = new JTextField();

	JFontChooserButton fontButton = new JFontChooserButton("Choose Font", fontSizes);

	JComboBox<String> zipResolutionFolderCombo = new JComboBox<String>();

	JComboBox<RomPreset> romPresetCombo = new JComboBox<RomPreset>(RomPreset.getPresets());

	// JTextField zipResolutionFolderTextBox = new JTextField();
	// JCheckBox cboxHDPI = createCheckbox("Set Zip-Output to HDPI",
	// "Set Zip-Output to HDPI...default is XHDPI");

	public ConfigPanel() {
		initComponents();

		myInit();
	}

	private void initComponents() {
		fontColor = createClickabelColorLabel("Main Color", "Color when normal battery-level");
		fontColorLowBatt = createClickabelColorLabel("LowBatt", "Color when low battery");
		fontColorMedBatt = createClickabelColorLabel("MedBatt", "Color when Med battery");
		fontColorCharge = createClickabelColorLabel("Charge Color", "Color when charging");

		iconColor = createClickabelColorLabel("Main Color", "Color when normal battery-level");
		iconColorInactiv = createClickabelColorLabel("Background", "Color for Background");

		iconColorLowBatt = createClickabelColorLabel("LowBatt", "Color when low battery");
		iconColorMedBatt = createClickabelColorLabel("MedBatt", "Color when Med battery");
		iconColorCharge = createClickabelColorLabel("Charge Color", "Color when charging");
		// Adding items
		zipResolutionFolderCombo.addItem(StyleSettings.FOLDER_XHDPI);
		zipResolutionFolderCombo.addItem(StyleSettings.FOLDER_HDPI);
		zipResolutionFolderCombo.addItem(StyleSettings.FOLDER_600DP);
		zipResolutionFolderCombo.addItem(StyleSettings.FOLDER_720DP);
		zipResolutionFolderCombo.setEditable(false);
		zipResolutionFolderCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (zipResolutionFolderCombo.getSelectedItem().equals(StyleSettings.FOLDER_XHDPI)) {
					sliderResize.setValue(StyleSettings.ICON_HEIGHT_XHDPI);
				} else if (zipResolutionFolderCombo.getSelectedItem().equals(StyleSettings.FOLDER_HDPI)) {
					sliderResize.setValue(StyleSettings.ICON_HEIGHT_HDPI);
				} else if (zipResolutionFolderCombo.getSelectedItem().equals(StyleSettings.FOLDER_720DP)) {
					sliderResize.setValue(StyleSettings.ICON_HEIGHT_720DP);
				} else if (zipResolutionFolderCombo.getSelectedItem().equals(StyleSettings.FOLDER_600DP)) {
					sliderResize.setValue(StyleSettings.ICON_HEIGHT_600DP);
				}
			}
		});

		romPresetCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final RomPreset pre = (RomPreset) romPresetCombo.getSelectedItem();
				if (!pre.getRomName().equals(RomPreset.APPLY)) {
					zipResolutionFolderCombo.setSelectedItem(pre.getZipResolutionFolder());
					filepattern.setText(pre.getFilePattern());
					filepatternCharge.setText(pre.getFilePatternCharge());
					romPresetCombo.setSelectedIndex(0);
				}
			}
		});
		romPresetCombo.setSelectedIndex(0);
	}

	private void myInit() {
		// setBackground(Color.black);
		setLayout(new BorderLayout());
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;
		builder.add(createGroupLabel("Percentages..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));

		builder.add(cboxShowChargeSymbol, cc.xyw(2, ++row, 3));

		builder.add(cboxShowFont, cc.xyw(2, ++row, 3));
		builder.add(cboxColoredFont, cc.xyw(6, row, 3));

		builder.add(fontColor, cc.xyw(2, ++row, 1));
		builder.add(fontColorCharge, cc.xyw(4, row, 1));
		builder.add(fontColorLowBatt, cc.xyw(6, row, 1));
		builder.add(fontColorMedBatt, cc.xyw(8, row, 1));

		builder.add(fontButton, cc.xyw(2, ++row, 3));
		builder.add(cboxShowChargeSymbol, cc.xyw(6, row, 1));
		builder.add(chargeIconSeletor, cc.xyw(8, row, 1));

		builder.add(createBlueDeviderLabel("Font Pixel Position Offsets X"), cc.xyw(2, ++row, 3));
		builder.add(createBlueDeviderLabel("Font Pixel Position Offsets Y"), cc.xyw(6, row, 3));
		builder.add(sliderFontXOffset, cc.xyw(2, ++row, 3));
		builder.add(sliderFontYOffset, cc.xyw(6, row, 3));

		builder.add(createGroupLabel("Battery Icon..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));

		builder.add(iconColorInactiv, cc.xyw(2, ++row, 1));
		builder.add(cboxColoredIcon, cc.xyw(6, row, 3));
		builder.add(iconColorCharge, cc.xyw(2, ++row, 1));
		builder.add(iconColor, cc.xyw(4, row, 1));
		builder.add(iconColorLowBatt, cc.xyw(6, row, 1));
		builder.add(iconColorMedBatt, cc.xyw(8, row, 1));

		builder.add(createGroupLabel("Thresholds..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(createBlueDeviderLabel("...for Low Battery-Levels"), cc.xyw(2, ++row, 3));
		builder.add(createBlueDeviderLabel("...for Med Battery-Levels"), cc.xyw(6, row, 3));
		builder.add(sliderLowBatt, cc.xyw(2, ++row, 3));
		builder.add(sliderMedBatt, cc.xyw(6, row, 3));
		builder.add(cboxUseGradientMediumLevels, cc.xyw(2, ++row, 3));
		builder.add(cboxUseGradientNormalLevels, cc.xyw(6, row, 3));

		builder.add(createGroupLabel("Misc Options ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(createBlueDeviderLabel("These settings only work on some styls"), cc.xyw(2, ++row, 7));
		builder.add(cboxFlip, cc.xyw(2, ++row, 3));
		builder.add(sliderStroke, cc.xyw(6, row, 3));

		builder.add(createGroupLabel("Resizing, Filenames, Output ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(createBlueDeviderLabel("Choose your ROM's resolution"), cc.xyw(2, ++row, 7));
		builder.add(zipResolutionFolderCombo, cc.xyw(2, ++row, 3));
		builder.add(romPresetCombo, cc.xyw(6, row, 3));
		builder.add(createBlueDeviderLabel("FileName-Pattern Nomal / Charge"), cc.xyw(2, ++row, 7));
		builder.add(filepattern, cc.xyw(2, ++row, 3));
		builder.add(filepatternCharge, cc.xyw(6, row, 3));

		builder.add(createBlueDeviderLabel("Resize Icon to (hight)"), cc.xyw(2, ++row, 7));
		builder.add(sliderResize, cc.xyw(2, ++row, 3));
		builder.add(cboxUseAdvResize, cc.xyw(6, row, 3));

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
		lab.setBorder(BorderFactory.createEmptyBorder(6, 1, 1, 1));
		return lab;
	}

	/**
	 * Creates a Label with a very small blue font
	 * 
	 * @param txt
	 * @return
	 */
	private JLabel createBlueDeviderLabel(final String txt) {
		final JLabel lab = createColoredFontLabel(txt, new Font(Font.SANS_SERIF, Font.BOLD, 10), Color.BLUE.darker());
		lab.setBorder(BorderFactory.createEmptyBorder(2, 1, 1, 1));
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

	public void setSettings(final StyleSettings settings) {
		this.settings = settings;
		fontColor.setBackground(settings.getFontColor());
		fontColorLowBatt.setBackground(settings.getFontColorLowBatt());
		fontColorMedBatt.setBackground(settings.getFontColorMedBatt());
		fontColorCharge.setBackground(settings.getFontChargeColor());

		iconColor.setBackground(settings.getIconColor());
		iconColorLowBatt.setBackground(settings.getIconColorLowBatt());
		iconColorMedBatt.setBackground(settings.getIconColorMedBatt());
		iconColorInactiv.setBackground(settings.getIconColorInActiv());
		iconColorCharge.setBackground(settings.getIconChargeColor());

		cboxFlip.setSelected(settings.isFlip());
		sliderStroke.setValue(settings.getStrokewidth());

		cboxShowFont.setSelected(settings.isShowFont());
		cboxShowChargeSymbol.setSelected(settings.isShowChargeSymbol());
		cboxColoredFont.setSelected(settings.isColoredFont());
		cboxColoredIcon.setSelected(settings.isColoredIcon());

		sliderMedBatt.setValue(settings.getMedBattTheshold());
		sliderLowBatt.setValue(settings.getLowBattTheshold());

		sliderResize.setValue(settings.getTargetIconSize());
		cboxUseAdvResize.setSelected(settings.isUseAdvancedResize());
		if (settings.getChargeIcon() != null)
			chargeIconSeletor.setSelectedItem(settings.getChargeIcon());
		else
			chargeIconSeletor.setSelectedIndex(1);

		filepattern.setText(settings.getFilePattern());
		filepatternCharge.setText(settings.getFilePatternCharge());
		zipResolutionFolderCombo.setSelectedItem(settings.getZipResolutionFolder());
		cboxUseGradientMediumLevels.setSelected(settings.isUseGradiantForMediumColor());
		cboxUseGradientNormalLevels.setSelected(settings.isUseGradiantForNormalColor());
		fontButton.setFont(settings.getFont());

		sliderFontXOffset.setValue(settings.getFontXOffset());
		sliderFontYOffset.setValue(settings.getFontYOffset());
		validateControls();
		this.repaint();
	}

	public StyleSettings getSettings() {
		settings.setFontColor(fontColor.getBackground());
		settings.setFontColorLowBatt(fontColorLowBatt.getBackground());
		settings.setFontColorMedBatt(fontColorMedBatt.getBackground());
		settings.setFontChargeColor(fontColorCharge.getBackground());

		settings.setIconColor(iconColor.getBackground());
		settings.setIconColorLowBatt(iconColorLowBatt.getBackground());
		settings.setIconColorMedBatt(iconColorMedBatt.getBackground());
		settings.setIconColorInActiv(iconColorInactiv.getBackground());
		settings.setIconChargeColor(iconColorCharge.getBackground());

		settings.setFlip(cboxFlip.isSelected());
		settings.setStrokewidth(sliderStroke.getValue());

		settings.setShowFont(cboxShowFont.isSelected());
		settings.setShowChargeSymbol(cboxShowChargeSymbol.isSelected());
		settings.setColoredFont(cboxColoredFont.isSelected());
		settings.setColoredIcon(cboxColoredIcon.isSelected());

		settings.setMedBattTheshold(sliderMedBatt.getValue());
		settings.setLowBattTheshold(sliderLowBatt.getValue());

		settings.setTargetIconSize(sliderResize.getValue());
		settings.setUseAdvancedResize(cboxUseAdvResize.isSelected());
		settings.setChargeIcon((ImageIcon) chargeIconSeletor.getSelectedItem());
		settings.setFilePattern(filepattern.getText());
		settings.setFilePatternCharge(filepatternCharge.getText());
		settings.setZipResolutionFolder((String) zipResolutionFolderCombo.getSelectedItem());
		settings.setUseGradiantForMediumColor(cboxUseGradientMediumLevels.isSelected());
		settings.setUseGradiantForNormalColor(cboxUseGradientNormalLevels.isSelected());

		settings.setFont(fontButton.getFont());
		settings.setFontXOffset(sliderFontXOffset.getValue());
		settings.setFontYOffset(sliderFontYOffset.getValue());
		return settings;
	}

	protected void validateControls() {
		fontColor.setEnabled(cboxShowFont.isSelected());
		fontColorMedBatt.setEnabled(cboxShowFont.isSelected() && cboxColoredFont.isSelected());
		fontColorLowBatt.setEnabled(cboxShowFont.isSelected() && cboxColoredFont.isSelected());
		fontColorCharge.setEnabled(cboxShowFont.isSelected());
		cboxColoredFont.setEnabled(cboxShowFont.isSelected());
		iconColorMedBatt.setEnabled(cboxColoredIcon.isSelected());
		iconColorLowBatt.setEnabled(cboxColoredIcon.isSelected());
		chargeIconSeletor.setEnabled(cboxShowChargeSymbol.isSelected());
		fontButton.setEnabled(cboxShowFont.isSelected());
		romPresetCombo.setSelectedIndex(0);
	}

	public void enableSupportedFeatures(final boolean supportsFlip, final boolean suppoertsStrokewidth) {
		cboxFlip.setEnabled(supportsFlip);
		sliderStroke.setVisible(suppoertsStrokewidth);
	}
}
