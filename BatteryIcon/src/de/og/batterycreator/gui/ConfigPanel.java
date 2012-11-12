package de.og.batterycreator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import og.basics.gui.Jcolorselectbutton.JColorSelectButton;
import og.basics.gui.jfontchooser.JFontChooserButton;
import og.basics.jgoodies.JGoodiesHelper;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.og.batterycreator.cfg.RomPreset;
import de.og.batterycreator.cfg.StyleSettings;
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

	JColorSelectButton fontColor = new JColorSelectButton("Main Color", "Color when normal battery-level");
	JColorSelectButton fontColorLowBatt = new JColorSelectButton("LowBatt", "Color when low battery");
	JColorSelectButton fontColorMedBatt = new JColorSelectButton("MedBatt", "Color when Med battery");
	JColorSelectButton fontColorCharge = new JColorSelectButton("Charge Color", "Color when charging");
	ChargeIconSelector chargeIconSeletor = new ChargeIconSelector();

	JCheckBox cboxTransparentBgrnd = createCheckbox("Transparent Background (switchOff = experimental !)",
			"Use this, when your statusbar Background is not black!");
	JColorSelectButton backgroundColor = new JColorSelectButton("Background Color", "Color if not transparent");

	JColorSelectButton iconColor = new JColorSelectButton("Main Color", "Color when normal battery-level");
	JColorSelectButton iconColorLowBatt = new JColorSelectButton("LowBatt", "Color when low battery");
	JColorSelectButton iconColorMedBatt = new JColorSelectButton("MedBatt", "Color when Med battery");
	JColorSelectButton iconColorInactiv = new JColorSelectButton("Inactiv", "Color for inactiv Iconelements");
	JColorSelectButton iconColorCharge = new JColorSelectButton("Charge Color", "Color when charging");

	SliderAndLabel sliderStroke = new SliderAndLabel(1, 10);
	JCheckBox cboxFlip = createCheckbox("Flip Icon", "Mirror's the Icon...ony has effect on a few styls!");

	JCheckBox cboxColoredFont = createCheckbox("Low battery Colors", "...");
	JCheckBox cboxColoredIcon = createCheckbox("Low battery Colors", "...");
	JCheckBox cboxShowFont = createCheckbox("Show percentages", "...");
	JCheckBox cboxShowChargeSymbol = createCheckbox("Charge-Symbol", "Show Charge-Symbol when charging");
	JCheckBox cboxUseGradientMediumLevels = createCheckbox("Gradient for Medium levels", "Use Gradient Colors between Low and Med Batterylevels");
	JCheckBox cboxUseGradientNormalLevels = createCheckbox("Gradient for Normal levels", "Use Gradient Colors between Med and 100% Batterylevels");

	SliderAndLabel sliderLowBatt = new SliderAndLabel(0, 30);
	SliderAndLabel sliderMedBatt = new SliderAndLabel(0, 100);

	SliderAndLabel sliderFontXOffset = new SliderAndLabel(-12, 12);
	SliderAndLabel sliderFontYOffset = new SliderAndLabel(-12, 12);
	SliderAndLabel slidericonXOffset = new SliderAndLabel(-12, 12);
	SliderAndLabel slidericonYOffset = new SliderAndLabel(-12, 12);

	SliderAndLabel sliderReduceOn100 = new SliderAndLabel(-5, 0);

	SliderAndLabel sliderResizeChargeSymbol = new SliderAndLabel(15, 30);
	JCheckBox cboxResizeChargeSymbol = createCheckbox("resize Charge Symbol to: (pixel)", " Resize the Charge Symbol to make it fit better");

	JCheckBox cboxUseAdvResize = createCheckbox("Use advanced Resize-Algorithm",
			"(Experimental) Advanced Resize-Algorith...might give better results on small imagesizes!?");

	JTextField filepattern = new JTextField();
	JTextField filepatternCharge = new JTextField();

	JFontChooserButton fontButton = new JFontChooserButton("Choose Font", fontSizes);

	// Battery
	DrawableComboBox systemUIDrawableFolderCombo = new DrawableComboBox();
	DrawableComboBox frameworkDrawableFolderCombo = new DrawableComboBox();
	SliderAndLabel sliderResize = systemUIDrawableFolderCombo.getSizeSlider();
	JComboBox<RomPreset> romPresetCombo = new JComboBox<RomPreset>(RomPreset.getPresets());

	// Lockhandle
	JTextField lockHandleFileName = new JTextField();
	SliderAndLabel lockHandleSize = new SliderAndLabel(130, 250);

	// Toggles
	SliderAndLabel toggleSize = new SliderAndLabel(32, 100);
	JCheckBox cboxUseLidroid = createCheckbox("lidroid-res.apk for Toggles (S3 only)",
			"Morph Toggles to framework/lidroid-res.apk (Galaxy S3 only dont play with this on S2 !)");

	// Weather
	SliderAndLabel weatherSize = new SliderAndLabel(100, 200);

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
	private final JColorSelectButton inWifiColor = new JColorSelectButton("ColorDataIn", "Color when Data comes in ;-)");
	private final JColorSelectButton outWifiColor = new JColorSelectButton("ColorDataOut", "Color when Data comes in ;-)");
	private final JColorSelectButton wifiColor = new JColorSelectButton("Connected", "Color when connected");
	private final JColorSelectButton wifiColorFully = new JColorSelectButton("Fully Connected", "Color when fully connected");

	// Construktor
	public ConfigPanel() {
		initComponents();

		myInit();
	}

	private void initComponents() {
		cboxUseLidroid.setForeground(Color.red.darker());
		cboxUseLidroid.setVisible(false);

		sliderLowBatt.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent arg0) {
				final int low = sliderLowBatt.getValue();
				final int med = sliderMedBatt.getValue();
				if (low > med)
					sliderMedBatt.setValue(low);
			}
		});

		sliderMedBatt.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent arg0) {
				final int low = sliderLowBatt.getValue();
				final int med = sliderMedBatt.getValue();
				if (med < low)
					sliderLowBatt.setValue(med);
			}
		});

		romPresetCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final RomPreset pre = (RomPreset) romPresetCombo.getSelectedItem();
				if (!pre.getRomName().equals(RomPreset.APPLY)) {
					systemUIDrawableFolderCombo.setSelectedItem(pre.getSystemUIDrawableFolder());
					frameworkDrawableFolderCombo.setSelectedItem(pre.getFrameworkDrawableFolder());
					filepattern.setText(pre.getFilePattern());
					filepatternCharge.setText(pre.getFilePatternCharge());
					romPresetCombo.setSelectedIndex(0);
					lockHandleSize.setValue(pre.getLockHandleSize());
					notificationHeight.setText("" + pre.getNotificationHeight());
					toggleSize.setValue(pre.getToggleSize());
					weatherSize.setValue(pre.getWeatherSize());
					sliderResize.setValue(pre.getBattsize());
					cboxUseLidroid.setSelected(pre.isUseLidroid());
					cboxUseLidroid.setVisible(pre.isUseLidroid());
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
		tabPane.addTab("Wifi/Signal Settings", IconStore.colorwifiIcon, tab2, "Special Colors for Wifi Icon");
		tabPane.addTab("ROM Settings", IconStore.cfgIcon, tab9, "Output Settings like Filenames, Resize...");

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
		builder.add(JGoodiesHelper.createBlackLabel("For having Battery and Wifi/Signal Icons in 'harmony', some Colors are used from Battery Icon:"),
				cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel(" - Icon BackgroundColor == Background"), cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel(" - Icon InactivColor == Inaktiv Color"), cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createGroupLabel("Wifi / Signal specific Colors"), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Colors for Connection"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Colors for Data Activity"), cc.xyw(6, row, 3));
		builder.add(wifiColor, cc.xyw(2, ++row, 1));
		builder.add(wifiColorFully, cc.xyw(4, row, 1));
		builder.add(inWifiColor, cc.xyw(6, row, 1));
		builder.add(outWifiColor, cc.xyw(8, row, 1));

		builder.add(JGoodiesHelper.createGroupLabel("Wifi Filenames & Output ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Filename Data In"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Filename Data Out"), cc.xyw(6, row, 3));
		builder.add(fileNameWifiIn, cc.xyw(2, ++row, 3));
		builder.add(fileNameWifiOut, cc.xyw(6, row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Filename Data InOut"), cc.xyw(2, ++row, 3));
		builder.add(fileNameWifiInOut, cc.xyw(2, ++row, 3));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Filename Pattern"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Fileextens. 'fully'"), cc.xyw(6, row, 3));
		builder.add(fileWifiPattern, cc.xyw(2, ++row, 3));
		builder.add(fileWifiPatternFully, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Signal Filenames & Output ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Filename Data In"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Filename Data Out"), cc.xyw(6, row, 3));
		builder.add(fileNameSignalIn, cc.xyw(2, ++row, 3));
		builder.add(fileNameSignalOut, cc.xyw(6, row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Filename Data InOut"), cc.xyw(2, ++row, 3));
		builder.add(fileNameSignalInOut, cc.xyw(2, ++row, 3));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Filename Pattern"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Fileextens. 'fully'"), cc.xyw(6, row, 3));
		builder.add(fileSignalPattern, cc.xyw(2, ++row, 3));
		builder.add(fileSignalPatternFully, cc.xyw(6, row, 3));

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

		builder.add(JGoodiesHelper.createBlackLabel("Reduce font on 100% by <x> pixel"), cc.xyw(6, ++row, 3));
		builder.add(fontButton, cc.xyw(2, ++row, 3));
		builder.add(sliderReduceOn100, cc.xyw(6, row, 1));
		builder.add(sliderReduceOn100.getValueLabel(), cc.xyw(8, row, 1));

		builder.add(JGoodiesHelper.createBlackLabel("Font Pixel Position Offsets X"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Font Pixel Position Offsets Y"), cc.xyw(6, row, 3));
		builder.add(sliderFontXOffset, cc.xyw(2, ++row, 1));
		builder.add(sliderFontXOffset.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(sliderFontYOffset, cc.xyw(6, row, 1));
		builder.add(sliderFontYOffset.getValueLabel(), cc.xyw(8, row, 1));

		builder.add(JGoodiesHelper.createGroupLabel("Charge Icon..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));

		builder.add(cboxShowChargeSymbol, cc.xyw(2, ++row, 1));
		builder.add(chargeIconSeletor, cc.xyw(4, row, 1));

		builder.add(JGoodiesHelper.createBlackLabel("ChargeIcon Pixel Position Offsets X"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("ChargeIcon Pixel Position Offsets Y"), cc.xyw(6, row, 3));
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
		builder.add(JGoodiesHelper.createBlackLabel("...for Low Battery-Levels"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("...for Med Battery-Levels"), cc.xyw(6, row, 3));
		builder.add(sliderLowBatt, cc.xyw(2, ++row, 1));
		builder.add(sliderLowBatt.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(sliderMedBatt, cc.xyw(6, row, 1));
		builder.add(sliderMedBatt.getValueLabel(), cc.xyw(8, row, 1));
		builder.add(cboxUseGradientMediumLevels, cc.xyw(2, ++row, 3));
		builder.add(cboxUseGradientNormalLevels, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Misc Options ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("These settings only work on some styls"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Stroke Width"), cc.xyw(6, row, 3));
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

		builder.add(JGoodiesHelper.createGroupLabel("Rom-Settings..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Rom Presets"), cc.xyw(2, ++row, 3));
		builder.add(romPresetCombo, cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createGroupLabel("Resizing..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Choose your SystemUI's resolution"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Choose your Framework's resolution"), cc.xyw(6, row, 3));
		builder.add(systemUIDrawableFolderCombo, cc.xyw(2, ++row, 3));
		builder.add(frameworkDrawableFolderCombo, cc.xyw(6, row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Battery, Wifi, Signal Size (hight)"), cc.xyw(2, ++row, 7));
		builder.add(sliderResize, cc.xyw(2, ++row, 1));
		builder.add(sliderResize.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(cboxUseAdvResize, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Battery Filenames & Output ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("FileName-Pattern Nomal / Charge"), cc.xyw(2, ++row, 7));
		builder.add(filepattern, cc.xyw(2, ++row, 3));
		builder.add(filepatternCharge, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Lockhandle Filename & Size ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Lockhandle Filename"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Size"), cc.xyw(6, row, 3));
		builder.add(lockHandleFileName, cc.xyw(2, ++row, 3));
		builder.add(lockHandleSize, cc.xyw(6, row, 1));
		builder.add(lockHandleSize.getValueLabel(), cc.xyw(8, row, 1));

		builder.add(JGoodiesHelper.createGroupLabel("Toggle & Weather Size ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("ToggleSize (is set via Rom Presets)"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("WeatherSize (is set via Rom Presets)"), cc.xyw(6, row, 3));
		builder.add(toggleSize, cc.xyw(2, ++row, 1));
		builder.add(toggleSize.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(weatherSize, cc.xyw(6, row, 1));
		builder.add(weatherSize.getValueLabel(), cc.xyw(8, row, 1));
		builder.add(cboxUseLidroid, cc.xyw(2, ++row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Notification BG Filename & Size ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Notification BG Filename"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Size (height)"), cc.xyw(6, row, 3));
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
		fontColor.setColor(settings.getFontColor());
		fontColorLowBatt.setColor(settings.getFontColorLowBatt());
		fontColorMedBatt.setColor(settings.getFontColorMedBatt());
		fontColorCharge.setColor(settings.getFontChargeColor());

		iconColor.setColor(settings.getIconColor());
		iconColorLowBatt.setColor(settings.getIconColorLowBatt());
		iconColorMedBatt.setColor(settings.getIconColorMedBatt());
		iconColorInactiv.setColor(settings.getIconColorInActiv());
		iconColorCharge.setColor(settings.getIconChargeColor());

		backgroundColor.setColor(settings.getBackgroundColor());
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
		systemUIDrawableFolderCombo.setSelectedItem(settings.getSystemUIDrawableFolder());
		frameworkDrawableFolderCombo.setSelectedItem(settings.getFrameworkDrawableFolder());
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
		inWifiColor.setColor(settings.getInWifiColor());
		outWifiColor.setColor(settings.getOutWifiColor());
		wifiColor.setColor(settings.getWifiColor());
		wifiColorFully.setColor(settings.getWifiColorFully());

		// Lockhandle
		lockHandleFileName.setText(settings.getLockHandleFileName());
		lockHandleSize.setValue(settings.getLockHandleSize());
		// Notification
		notificationFileName.setText(settings.getNotificationBGFilename());
		notificationHeight.setText("" + settings.getNotificationHeight());
		// toggle
		toggleSize.setValue(settings.getToggleSize());
		cboxUseLidroid.setSelected(settings.isUseLidroid());
		// weather
		weatherSize.setValue(settings.getWeatherSize());

		validateControls();
		this.repaint();
	}

	public StyleSettings getSettings() {
		settings.setFontColor(fontColor.getColor());
		settings.setFontColorLowBatt(fontColorLowBatt.getColor());
		settings.setFontColorMedBatt(fontColorMedBatt.getColor());
		settings.setFontChargeColor(fontColorCharge.getColor());

		settings.setIconColor(iconColor.getColor());
		settings.setIconColorLowBatt(iconColorLowBatt.getColor());
		settings.setIconColorMedBatt(iconColorMedBatt.getColor());
		settings.setIconColorInActiv(iconColorInactiv.getColor());
		settings.setIconChargeColor(iconColorCharge.getColor());

		settings.setBackgroundColor(backgroundColor.getColor());
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
		settings.setSystemUIDrawableFolder((String) systemUIDrawableFolderCombo.getSelectedItem());
		settings.setFrameworkDrawableFolder((String) frameworkDrawableFolderCombo.getSelectedItem());
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
		settings.setInWifiColor(inWifiColor.getColor());
		settings.setOutWifiColor(outWifiColor.getColor());
		settings.setWifiColor(wifiColor.getColor());
		settings.setWifiColorFully(wifiColorFully.getColor());
		// Lockhandle
		settings.setLockHandleFileName(lockHandleFileName.getText());
		settings.setLockHandleSize(lockHandleSize.getValue());
		// Notification
		settings.setNotificationBGFilename(notificationFileName.getText());
		settings.setNotificationHeight(Integer.parseInt(notificationHeight.getText()));
		// toggle
		settings.setToggleSize(toggleSize.getValue());
		settings.setUseLidroid(cboxUseLidroid.isSelected());
		// weather
		settings.setWeatherSize(weatherSize.getValue());
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
