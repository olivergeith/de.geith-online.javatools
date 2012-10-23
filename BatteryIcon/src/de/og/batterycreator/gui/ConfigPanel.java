package de.og.batterycreator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import og.basics.gui.jfontchooser.JFontChooserButton;
import og.basics.jgoodies.JGoodiesHelper;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.og.batterycreator.creators.settings.RomPreset;
import de.og.batterycreator.creators.settings.StyleSettings;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.DrawableComboBox;
import de.og.batterycreator.gui.widgets.SliderAndLabel;
import de.og.batterycreator.gui.widgets.chargeiconselector.ChargeIconSelector;

public class ConfigPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private StyleSettings settings;
	private final String fontSizes[] = {
			"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32"
	};

	JButton fontColor = JGoodiesHelper.createClickabelColorLabel("Main Color", "Color when normal battery-level");
	JButton fontColorLowBatt = JGoodiesHelper.createClickabelColorLabel("LowBatt", "Color when low battery");
	JButton fontColorMedBatt = JGoodiesHelper.createClickabelColorLabel("MedBatt", "Color when Med battery");
	JButton fontColorCharge = JGoodiesHelper.createClickabelColorLabel("Charge Color", "Color when charging");
	ChargeIconSelector chargeIconSeletor = new ChargeIconSelector();

	JCheckBox cboxTransparentBgrnd = createCheckbox("Transparent Background (switchOff = experimental !)",
			"Use this, when your statusbar Background is not black!");
	JButton backgroundColor = JGoodiesHelper.createClickabelColorLabel("Background Color", "Color if not transparent");

	JButton iconColor = JGoodiesHelper.createClickabelColorLabel("Main Color", "Color when normal battery-level");
	JButton iconColorLowBatt = JGoodiesHelper.createClickabelColorLabel("LowBatt", "Color when low battery");
	JButton iconColorMedBatt = JGoodiesHelper.createClickabelColorLabel("MedBatt", "Color when Med battery");
	JButton iconColorInactiv = JGoodiesHelper.createClickabelColorLabel("Inactiv", "Color for inactiv Iconelements");
	JButton iconColorCharge = JGoodiesHelper.createClickabelColorLabel("Charge Color", "Color when charging");

	SliderAndLabel sliderStroke = new SliderAndLabel(1, 10);
	JCheckBox cboxFlip = createCheckbox("Flip Icon", "Mirror's the Icon...ony has effect on a few styls!");

	JCheckBox cboxColoredFont = createCheckbox("Low battery Colors", "...");
	JCheckBox cboxColoredIcon = createCheckbox("Low battery Colors", "...");
	JCheckBox cboxShowFont = createCheckbox("Show percentages", "...");
	JCheckBox cboxShowChargeSymbol = createCheckbox("Charge-Symbol", "Show Charge-Symbol when charging");
	JCheckBox cboxUseGradientMediumLevels = createCheckbox("Gradient for Medium levels", "Use Gradient Colors between Low and Med Batterylevels");
	JCheckBox cboxUseGradientNormalLevels = createCheckbox("Gradient for Normal levels", "Use Gradient Colors between Med and 100% Batterylevels");

	SliderAndLabel sliderLowBatt = new SliderAndLabel(0, 30);
	SliderAndLabel sliderMedBatt = new SliderAndLabel(20, 100);

	SliderAndLabel sliderFontXOffset = new SliderAndLabel(-12, 12);
	SliderAndLabel sliderFontYOffset = new SliderAndLabel(-12, 12);
	SliderAndLabel slidericonXOffset = new SliderAndLabel(-4, 4);
	SliderAndLabel slidericonYOffset = new SliderAndLabel(-4, 4);

	SliderAndLabel sliderReduceOn100 = new SliderAndLabel(-5, 0);

	SliderAndLabel sliderResizeChargeSymbol = new SliderAndLabel(15, 30);
	JCheckBox cboxResizeChargeSymbol = createCheckbox("resize Charge Symbol to: (pixel)", " Resize the Charge Symbol to make it fit better");

	JCheckBox cboxUseAdvResize = createCheckbox("Use advanced Resize-Algorithm",
			"(Experimental) Advanced Resize-Algorith...might give better results on small imagesizes!?");

	JTextField filepattern = new JTextField();
	JTextField filepatternCharge = new JTextField();

	JFontChooserButton fontButton = new JFontChooserButton("Choose Font", fontSizes);

	//
	DrawableComboBox zipResolutionFolderCombo = new DrawableComboBox();
	SliderAndLabel sliderResize = zipResolutionFolderCombo.getSizeSlider();
	JComboBox<RomPreset> romPresetCombo = new JComboBox<RomPreset>(RomPreset.getPresets());

	// Lockhandle
	JTextField lockHandleFileName = new JTextField();
	JLabel lockHandleSize = new JLabel();

	// Notification
	JTextField notificationFileName = new JTextField();
	JLabel notificationHeight = new JLabel();

	// Signal stuff
	private final JTextField fileNameSignalIn = new JTextField();
	private final JTextField fileNameSignalOut = new JTextField();
	private final JTextField fileNameSignalInOut = new JTextField();
	private final JTextField fileSignalPattern = new JTextField();
	private final JTextField fileSignalPatternFully = new JTextField();

	// Wifi stuff
	private final JTextField fileNameWifiIn = new JTextField();
	private final JTextField fileNameWifiOut = new JTextField();
	private final JTextField fileNameWifiInOut = new JTextField();
	private final JTextField fileWifiPattern = new JTextField();
	private final JTextField fileWifiPatternFully = new JTextField();
	private final JButton inWifiColor = JGoodiesHelper.createClickabelColorLabel("ColorDataIn", "Color when Data comes in ;-)");
	private final JButton outWifiColor = JGoodiesHelper.createClickabelColorLabel("ColorDataOut", "Color when Data comes in ;-)");
	private final JButton wifiColor = JGoodiesHelper.createClickabelColorLabel("Connected", "Color when connected");
	private final JButton wifiColorFully = JGoodiesHelper.createClickabelColorLabel("Fully Connected", "Color when fully connected");

	// Construktor
	public ConfigPanel() {
		initComponents();

		myInit();
	}

	private void initComponents() {

		romPresetCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final RomPreset pre = (RomPreset) romPresetCombo.getSelectedItem();
				if (!pre.getRomName().equals(RomPreset.APPLY)) {
					zipResolutionFolderCombo.setSelectedItem(pre.getZipResolutionFolder());
					filepattern.setText(pre.getFilePattern());
					filepatternCharge.setText(pre.getFilePatternCharge());
					romPresetCombo.setSelectedIndex(0);
					lockHandleSize.setText("" + pre.getLockHandleSize());
					notificationHeight.setText("" + pre.getNotificationHeight());
				}
			}
		});
		romPresetCombo.setSelectedIndex(0);
	}

	private void myInit() {
		// setBackground(Color.black);
		setLayout(new BorderLayout());

		final JTabbedPane tabPane = new JTabbedPane();
		tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		final JPanel tab1 = new JPanel(new BorderLayout());
		final JPanel tab2 = new JPanel(new BorderLayout());
		final JPanel tab9 = new JPanel(new BorderLayout());
		tab1.add(createTabPaneBattSettings(), BorderLayout.CENTER);
		tab2.add(createTabPaneWifiColors(), BorderLayout.CENTER);
		tab9.add(createTabPaneMoreSettings(), BorderLayout.CENTER);

		tabPane.addTab("Battery Settings", IconStore.colorIcon, tab1, "Color & Font Settings for Icons");
		tabPane.addTab("Wifi/Signal Colors", IconStore.colorwifiIcon, tab2, "Special Colors for Wifi Icon");
		tabPane.addTab("Output Settings", IconStore.cfgIcon, tab9, "Output Settings like Filenames, Resize...");

		this.add(tabPane, BorderLayout.CENTER);
	}

	public JPanel createTabPaneWifiColors() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createGroupLabel("Colors"), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("For having Battery and Wifi/Signal Icons in 'harmony', some Colors are used from Battery Icon:"),
				cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlueDeviderLabel(" - Icon BackgroundColor == Background"), cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlueDeviderLabel(" - Icon InactivColor == Inaktiv Color"), cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createGroupLabel("Wifi / Signal specific Colors"), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Colors for Connection"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Colors for Data Activity"), cc.xyw(6, row, 3));
		builder.add(wifiColor, cc.xyw(2, ++row, 1));
		builder.add(wifiColorFully, cc.xyw(4, row, 1));
		builder.add(inWifiColor, cc.xyw(6, row, 1));
		builder.add(outWifiColor, cc.xyw(8, row, 1));

		final JPanel cfp = builder.getPanel();
		return cfp;
	}

	public JPanel createTabPaneBattSettings() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;
		builder.add(JGoodiesHelper.createGroupLabel("Percentages..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));

		builder.add(cboxShowChargeSymbol, cc.xyw(2, ++row, 3));

		builder.add(cboxShowFont, cc.xyw(2, ++row, 3));
		builder.add(cboxColoredFont, cc.xyw(6, row, 3));

		builder.add(fontColor, cc.xyw(2, ++row, 1));
		builder.add(fontColorCharge, cc.xyw(4, row, 1));
		builder.add(fontColorLowBatt, cc.xyw(6, row, 1));
		builder.add(fontColorMedBatt, cc.xyw(8, row, 1));

		builder.add(JGoodiesHelper.createBlueDeviderLabel("Reduce font on 100% by <x> pixel"), cc.xyw(6, ++row, 3));
		builder.add(fontButton, cc.xyw(2, ++row, 3));
		builder.add(sliderReduceOn100, cc.xyw(6, row, 1));
		builder.add(sliderReduceOn100.getValueLabel(), cc.xyw(8, row, 1));

		builder.add(JGoodiesHelper.createBlueDeviderLabel("Font Pixel Position Offsets X"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Font Pixel Position Offsets Y"), cc.xyw(6, row, 3));
		builder.add(sliderFontXOffset, cc.xyw(2, ++row, 1));
		builder.add(sliderFontXOffset.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(sliderFontYOffset, cc.xyw(6, row, 1));
		builder.add(sliderFontYOffset.getValueLabel(), cc.xyw(8, row, 1));

		builder.add(JGoodiesHelper.createGroupLabel("Charge Icon..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));

		builder.add(cboxShowChargeSymbol, cc.xyw(2, ++row, 1));
		builder.add(chargeIconSeletor, cc.xyw(4, row, 1));

		builder.add(JGoodiesHelper.createBlueDeviderLabel("ChargeIcon Pixel Position Offsets X"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("ChargeIcon Pixel Position Offsets Y"), cc.xyw(6, row, 3));
		builder.add(slidericonXOffset, cc.xyw(2, ++row, 1));
		builder.add(slidericonXOffset.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(slidericonYOffset, cc.xyw(6, row, 1));
		builder.add(slidericonYOffset.getValueLabel(), cc.xyw(8, row, 1));

		builder.add(cboxResizeChargeSymbol, cc.xyw(2, ++row, 3));
		builder.add(sliderResizeChargeSymbol, cc.xyw(6, row, 1));
		builder.add(sliderResizeChargeSymbol.getValueLabel(), cc.xyw(8, row, 1));

		builder.add(JGoodiesHelper.createGroupLabel("Battery Icon..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));

		builder.add(iconColorInactiv, cc.xyw(2, ++row, 1));
		builder.add(backgroundColor, cc.xyw(4, row, 1));
		builder.add(cboxColoredIcon, cc.xyw(6, row, 3));
		builder.add(iconColor, cc.xyw(2, ++row, 1));
		builder.add(iconColorCharge, cc.xyw(4, row, 1));
		builder.add(iconColorLowBatt, cc.xyw(6, row, 1));
		builder.add(iconColorMedBatt, cc.xyw(8, row, 1));
		builder.add(cboxTransparentBgrnd, cc.xyw(1, ++row, 6));

		builder.add(JGoodiesHelper.createGroupLabel("Thresholds..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("...for Low Battery-Levels"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("...for Med Battery-Levels"), cc.xyw(6, row, 3));
		builder.add(sliderLowBatt, cc.xyw(2, ++row, 1));
		builder.add(sliderLowBatt.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(sliderMedBatt, cc.xyw(6, row, 1));
		builder.add(sliderMedBatt.getValueLabel(), cc.xyw(8, row, 1));
		builder.add(cboxUseGradientMediumLevels, cc.xyw(2, ++row, 3));
		builder.add(cboxUseGradientNormalLevels, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Misc Options ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("These settings only work on some styls"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Stroke Width"), cc.xyw(6, row, 3));
		builder.add(cboxFlip, cc.xyw(2, ++row, 3));
		builder.add(sliderStroke, cc.xyw(6, row, 1));
		builder.add(sliderStroke.getValueLabel(), cc.xyw(8, row, 1));

		final JPanel cfp = builder.getPanel();
		return cfp;
	}

	public JPanel createTabPaneMoreSettings() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createGroupLabel("Resizing..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Choose your ROM's resolution"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Rom Presets"), cc.xyw(6, row, 3));
		builder.add(zipResolutionFolderCombo, cc.xyw(2, ++row, 3));
		builder.add(romPresetCombo, cc.xyw(6, row, 3));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Resize Icon to (hight)"), cc.xyw(2, ++row, 7));
		builder.add(sliderResize, cc.xyw(2, ++row, 1));
		builder.add(sliderResize.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(cboxUseAdvResize, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Battery Filenames & Output ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("FileName-Pattern Nomal / Charge"), cc.xyw(2, ++row, 7));
		builder.add(filepattern, cc.xyw(2, ++row, 3));
		builder.add(filepatternCharge, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Wifi Filenames & Output ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Filename Data In"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Filename Data Out"), cc.xyw(6, row, 3));
		builder.add(fileNameWifiIn, cc.xyw(2, ++row, 3));
		builder.add(fileNameWifiOut, cc.xyw(6, row, 3));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Filename Data InOut"), cc.xyw(2, ++row, 3));
		builder.add(fileNameWifiInOut, cc.xyw(2, ++row, 3));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Filename Pattern"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Fileextens. 'fully'"), cc.xyw(6, row, 3));
		builder.add(fileWifiPattern, cc.xyw(2, ++row, 3));
		builder.add(fileWifiPatternFully, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Signal Filenames & Output ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Filename Data In"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Filename Data Out"), cc.xyw(6, row, 3));
		builder.add(fileNameSignalIn, cc.xyw(2, ++row, 3));
		builder.add(fileNameSignalOut, cc.xyw(6, row, 3));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Filename Data InOut"), cc.xyw(2, ++row, 3));
		builder.add(fileNameSignalInOut, cc.xyw(2, ++row, 3));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Filename Pattern"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Fileextens. 'fully'"), cc.xyw(6, row, 3));
		builder.add(fileSignalPattern, cc.xyw(2, ++row, 3));
		builder.add(fileSignalPatternFully, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Lockhandle Filename & Size ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Lockhandle Filename"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Size"), cc.xyw(6, row, 3));
		builder.add(lockHandleFileName, cc.xyw(2, ++row, 3));
		builder.add(lockHandleSize, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Notification BG Filename & Size ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Notification BG Filename"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlueDeviderLabel("Size (height)"), cc.xyw(6, row, 3));
		builder.add(notificationFileName, cc.xyw(2, ++row, 3));
		builder.add(notificationHeight, cc.xyw(6, row, 3));

		final JPanel cfp = builder.getPanel();
		return cfp;
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

		backgroundColor.setBackground(settings.getBackgroundColor());
		cboxTransparentBgrnd.setSelected(settings.isTransparentBackground());

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

		slidericonXOffset.setValue(settings.getIconXOffset());
		slidericonYOffset.setValue(settings.getIconYOffset());

		sliderFontXOffset.setValue(settings.getFontXOffset());
		sliderFontYOffset.setValue(settings.getFontYOffset());
		sliderReduceOn100.setValue(settings.getReduceFontOn100());

		sliderResizeChargeSymbol.setValue(settings.getResizeChargeSymbolHeight());
		cboxResizeChargeSymbol.setSelected(settings.isResizeChargeSymbol());

		// Signal stuff
		fileNameSignalIn.setText(settings.getFileSignalIn());
		fileNameSignalOut.setText(settings.getFileSignalOut());
		fileNameSignalInOut.setText(settings.getFileSignalInOut());
		fileSignalPattern.setText(settings.getFileSignalPattern());
		fileSignalPatternFully.setText(settings.getFileSignalEXtensionFully());

		// Wifi stuff
		fileNameWifiIn.setText(settings.getFileWifiIn());
		fileNameWifiOut.setText(settings.getFileWifiOut());
		fileNameWifiInOut.setText(settings.getFileWifiInOut());
		fileWifiPattern.setText(settings.getFileWifiPattern());
		fileWifiPatternFully.setText(settings.getFileWifiEXtensionFully());

		// Colors for Wifi and Signal
		inWifiColor.setBackground(settings.getInWifiColor());
		outWifiColor.setBackground(settings.getOutWifiColor());
		wifiColor.setBackground(settings.getWifiColor());
		wifiColorFully.setBackground(settings.getWifiColorFully());

		// Lockhandle
		lockHandleFileName.setText(settings.getLockHandleFileName());
		lockHandleSize.setText("" + settings.getLockHandleSize());
		// Notification
		notificationFileName.setText(settings.getNotificationBGFilename());
		notificationHeight.setText("" + settings.getNotificationHeight());

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

		settings.setBackgroundColor(backgroundColor.getBackground());
		settings.setTransparentBackground(cboxTransparentBgrnd.isSelected());

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
		settings.setReduceFontOn100(sliderReduceOn100.getValue());
		settings.setIconXOffset(slidericonXOffset.getValue());
		settings.setIconYOffset(slidericonYOffset.getValue());

		settings.setResizeChargeSymbol(cboxResizeChargeSymbol.isSelected());
		settings.setResizeChargeSymbolHeight(sliderResizeChargeSymbol.getValue());

		// Signal stuff
		settings.setFileSignalIn(fileNameSignalIn.getText());
		settings.setFileSignalOut(fileNameSignalOut.getText());
		settings.setFileSignalInOut(fileNameSignalInOut.getText());
		settings.setFileSignalPattern(fileSignalPattern.getText());
		settings.setFileSignalEXtensionFully(fileSignalPatternFully.getText());
		// Wifi stuff
		settings.setFileWifiIn(fileNameWifiIn.getText());
		settings.setFileWifiOut(fileNameWifiOut.getText());
		settings.setFileWifiInOut(fileNameWifiInOut.getText());
		settings.setFileWifiPattern(fileWifiPattern.getText());
		settings.setFileWifiEXtensionFully(fileWifiPatternFully.getText());
		// Wifi and Signal Colors
		settings.setInWifiColor(inWifiColor.getBackground());
		settings.setOutWifiColor(outWifiColor.getBackground());
		settings.setWifiColor(wifiColor.getBackground());
		settings.setWifiColorFully(wifiColorFully.getBackground());
		// Lockhandle
		settings.setLockHandleFileName(lockHandleFileName.getText());
		settings.setLockHandleSize(Integer.parseInt(lockHandleSize.getText()));
		// Notification
		settings.setNotificationBGFilename(notificationFileName.getText());
		settings.setNotificationHeight(Integer.parseInt(notificationHeight.getText()));
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
		slidericonXOffset.setEnabled(cboxShowChargeSymbol.isSelected());
		slidericonYOffset.setEnabled(cboxShowChargeSymbol.isSelected());
		sliderFontXOffset.setEnabled(cboxShowFont.isSelected());
		sliderFontYOffset.setEnabled(cboxShowFont.isSelected());
		sliderReduceOn100.setEnabled(cboxShowFont.isSelected());
		romPresetCombo.setSelectedIndex(0);
		sliderResizeChargeSymbol.setEnabled(cboxShowChargeSymbol.isSelected() && cboxResizeChargeSymbol.isSelected());
		cboxResizeChargeSymbol.setEnabled(cboxShowChargeSymbol.isSelected());
		// transparent Backround special behaviour
		backgroundColor.setEnabled(!cboxTransparentBgrnd.isSelected());
		backgroundColor.setVisible(!cboxTransparentBgrnd.isSelected());
		if (!backgroundColor.isEnabled()) {
			backgroundColor.setBackground(Color.black);
		}
	}

	public void enableSupportedFeatures(final boolean supportsFlip, final boolean suppoertsStrokewidth) {
		cboxFlip.setEnabled(supportsFlip);
		sliderStroke.setEnabled(suppoertsStrokewidth);
	}
}
