package de.og.batterycreator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import og.basics.gui.colorselectorobjects.JColorSelectButton;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.og.batterycreator.creators.StyleSettings;
import de.og.batterycreator.widgets.chargeiconselector.ChargeIconSelector;

public class ConfigPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private StyleSettings settings;

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
	JCheckBox cboxColoredFont = createCheckbox("Different color on low battery", "...");
	JCheckBox cboxColoredIcon = createCheckbox("Different color on low battery", "...");
	JCheckBox cboxShowFont = createCheckbox("Show percentages", "...");
	JCheckBox cboxShowChargeSymbol = createCheckbox("Charge-Symbol", "Show Charge-Symbol when charging");

	JSlider dialLowBatt = new JSlider(0, 30);
	JLabel dialLowValue = new JLabel();
	JSlider dialMedBatt = new JSlider(20, 50);
	JLabel dialMedValue = new JLabel();

	JTextField filepattern = new JTextField();
	JTextField filepatternCharge = new JTextField();
	JTextField folderInZip = new JTextField();
	JCheckBox cboxHDPI = createCheckbox("Set Zip-Output to HDPI", "Set Zip-Output to HDPI...default is XHDPI");

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
		dialLowBatt.setPreferredSize(new Dimension(60, 20));
		dialMedBatt.setPreferredSize(new Dimension(60, 20));

		dialLowBatt.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent arg0) {
				dialLowValue.setText("" + dialLowBatt.getValue());
			}
		});
		dialLowValue.setBorder(new BevelBorder(1));

		dialMedBatt.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent arg0) {
				dialMedValue.setText("" + dialMedBatt.getValue());
			}
		});
		dialMedValue.setBorder(new BevelBorder(1));

		cboxHDPI.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (cboxHDPI.isSelected())
					settings.setFolderWithinZip2Hdpi();
				else
					settings.setFolderWithinZip2Xhdpi();
				folderInZip.setText(settings.getFolderWithinZip());
			}
		});

		folderInZip.setAlignmentX(RIGHT_ALIGNMENT);
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
		builder.add(cboxShowChargeSymbol, cc.xyw(2, ++row, 1));
		builder.add(chargeIconSeletor, cc.xyw(4, row, 1));
		builder.add(fontColor, cc.xyw(2, ++row, 3));
		builder.add(fontColorCharge, cc.xyw(2, ++row, 3));
		builder.add(cboxColoredFont, cc.xyw(2, ++row, 3));
		builder.add(fontColorLowBatt, cc.xyw(2, ++row, 1));
		builder.add(fontColorMedBatt, cc.xyw(4, row, 1));
		builder.add(createGroupLabel("IconColors"), cc.xyw(2, ++row, 3));
		builder.addSeparator("", cc.xyw(2, ++row, 3));
		builder.add(iconColor, cc.xyw(2, ++row, 1));
		builder.add(iconColorInactiv, cc.xyw(4, row, 1));
		builder.add(iconColorCharge, cc.xyw(2, ++row, 3));
		builder.addSeparator("", cc.xyw(2, ++row, 3));
		builder.add(cboxColoredIcon, cc.xyw(2, ++row, 3));
		builder.add(iconColorLowBatt, cc.xyw(2, ++row, 1));
		builder.add(iconColorMedBatt, cc.xyw(4, row, 1));
		builder.add(createGroupLabel("Thresholds..."), cc.xyw(2, ++row, 3));
		builder.addSeparator("", cc.xyw(2, ++row, 3));
		builder.add(createBlueDeviderLabel("...for Low and Med Battery-Levels"), cc.xyw(2, ++row, 3));
		builder.add(dialLowBatt, cc.xyw(2, ++row, 1));
		builder.add(dialLowValue, cc.xyw(4, row, 1));
		builder.add(dialMedBatt, cc.xyw(2, ++row, 1));
		builder.add(dialMedValue, cc.xyw(4, row, 1));
		builder.add(createGroupLabel("Misc Stuff..."), cc.xyw(2, ++row, 3));
		builder.addSeparator("", cc.xyw(2, ++row, 3));
		builder.add(createBlueDeviderLabel("FileName-Pattern"), cc.xyw(2, ++row, 3));
		builder.add(filepattern, cc.xyw(2, ++row, 3));
		builder.add(createBlueDeviderLabel("FileName-Pattern(charge)"), cc.xyw(2, ++row, 3));
		builder.add(filepatternCharge, cc.xyw(2, ++row, 3));
		builder.add(createBlueDeviderLabel("Folder for Icons within flashable-zip"), cc.xyw(2, ++row, 3));
		builder.add(createBlueDeviderLabel("for hdpi ROM's edit here !!!"), cc.xyw(2, ++row, 3));
		builder.add(cboxHDPI, cc.xyw(2, ++row, 3));
		builder.add(folderInZip, cc.xyw(2, ++row, 3));

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
		fontColor.setBackground(settings.getFontColor());
		fontColorLowBatt.setBackground(settings.getFontColorLowBatt());
		fontColorMedBatt.setBackground(settings.getFontColorMedBatt());
		fontColorCharge.setBackground(settings.getFontChargeColor());

		iconColor.setBackground(settings.getIconColor());
		iconColorLowBatt.setBackground(settings.getIconColorLowBatt());
		iconColorMedBatt.setBackground(settings.getIconColorMedBatt());
		iconColorInactiv.setBackground(settings.getIconColorInActiv());
		iconColorCharge.setBackground(settings.getIconChargeColor());

		cboxShowFont.setSelected(settings.isShowFont());
		cboxShowChargeSymbol.setSelected(settings.isShowChargeSymbol());
		cboxColoredFont.setSelected(settings.isColoredFont());
		cboxColoredIcon.setSelected(settings.isColoredIcon());

		dialMedBatt.setValue(settings.getMedBattTheshold());
		dialLowBatt.setValue(settings.getLowBattTheshold());

		if (settings.getChargeIcon() != null)
			chargeIconSeletor.setSelectedItem(settings.getChargeIcon());
		else
			chargeIconSeletor.setSelectedIndex(0);

		filepattern.setText(settings.getFilePattern());
		filepatternCharge.setText(settings.getFilePatternCharge());
		folderInZip.setText(settings.getFolderWithinZip());
		cboxHDPI.setSelected(settings.isHDPI());

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

		settings.setShowFont(cboxShowFont.isSelected());
		settings.setShowChargeSymbol(cboxShowChargeSymbol.isSelected());
		settings.setColoredFont(cboxColoredFont.isSelected());
		settings.setColoredIcon(cboxColoredIcon.isSelected());

		settings.setMedBattTheshold(dialMedBatt.getValue());
		settings.setLowBattTheshold(dialLowBatt.getValue());
		settings.setChargeIcon((ImageIcon) chargeIconSeletor.getSelectedItem());
		settings.setFilePattern(filepattern.getText());
		settings.setFilePatternCharge(filepatternCharge.getText());
		if (cboxHDPI.isSelected())
			settings.setFolderWithinZip2Hdpi();
		else
			settings.setFolderWithinZip2Xhdpi();
		// settings.setFolderWithinZip(folderInZip.getText());
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
		folderInZip.setEnabled(false);

	}
}
