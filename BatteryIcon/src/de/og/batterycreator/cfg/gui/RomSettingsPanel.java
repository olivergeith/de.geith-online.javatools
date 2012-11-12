package de.og.batterycreator.cfg.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import og.basics.jgoodies.JGoodiesHelper;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.og.batterycreator.cfg.RomPreset;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.gui.widgets.DrawableComboBox;
import de.og.batterycreator.gui.widgets.SliderAndLabel;

public class RomSettingsPanel extends SettingsPanel {
	private static final long serialVersionUID = 1L;

	private RomSettings settings;

	JCheckBox cboxUseAdvResize = createCheckbox("Use advanced Resize-Algorithm",
			"(Experimental) Advanced Resize-Algorith...might give better results on small imagesizes!?");

	// Presets
	JComboBox<RomPreset> romPresetCombo = new JComboBox<RomPreset>(RomPreset.getPresets());

	// Drawable
	DrawableComboBox frameworkDrawableFolderCombo = new DrawableComboBox();
	DrawableComboBox systemUIDrawableFolderCombo = new DrawableComboBox();
	// Battery
	SliderAndLabel sliderBattSize = systemUIDrawableFolderCombo.getSliderBattSize();
	SliderAndLabel sliderWifiSize = systemUIDrawableFolderCombo.getSliderWifiSize();
	SliderAndLabel sliderSignalSize = systemUIDrawableFolderCombo.getSliderSignalSize();
	JTextField filepattern = new JTextField();
	JTextField filepatternCharge = new JTextField();

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

	// Construktor
	public RomSettingsPanel() {
		initComponents();
		myInit();
	}

	/**
	 * Initialize Components
	 */
	private void initComponents() {
		cboxUseLidroid.setForeground(Color.red.darker());
		cboxUseLidroid.setVisible(false);

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
					sliderBattSize.setValue(pre.getBattsize());
					cboxUseLidroid.setSelected(pre.isUseLidroid());
					cboxUseLidroid.setVisible(pre.isUseLidroid());
				}
			}
		});
		romPresetCombo.setSelectedIndex(0);
	}

	private void myInit() {
		setLayout(new BorderLayout());
		this.add(createTabPaneWifiColors(), BorderLayout.EAST);
		this.add(createTabPaneRomSettings(), BorderLayout.CENTER);
	}

	public JPanel createTabPaneWifiColors() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

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

	public JPanel createTabPaneRomSettings() {
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

		builder.add(JGoodiesHelper.createBlackLabel("Battery Size (hight)"), cc.xyw(2, ++row, 7));
		builder.add(sliderBattSize, cc.xyw(2, ++row, 1));
		builder.add(sliderBattSize.getValueLabel(), cc.xyw(4, row, 1));

		builder.add(JGoodiesHelper.createBlackLabel("Wifi, Size (hight)"), cc.xyw(2, ++row, 7));
		builder.add(sliderWifiSize, cc.xyw(2, ++row, 1));
		builder.add(sliderWifiSize.getValueLabel(), cc.xyw(4, row, 1));

		builder.add(JGoodiesHelper.createBlackLabel("Signal Size (hight)"), cc.xyw(2, ++row, 7));
		builder.add(sliderSignalSize, cc.xyw(2, ++row, 1));
		builder.add(sliderSignalSize.getValueLabel(), cc.xyw(4, row, 1));

		builder.add(cboxUseAdvResize, cc.xyw(2, ++row, 3));

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

	public void setSettings(final RomSettings settings) {
		this.settings = settings;

		cboxUseAdvResize.setSelected(settings.isUseAdvancedResize());

		// Drawables
		systemUIDrawableFolderCombo.setSelectedItem(settings.getSystemUIDrawableFolder());
		frameworkDrawableFolderCombo.setSelectedItem(settings.getFrameworkDrawableFolder());
		// Battery
		filepattern.setText(settings.getFilePattern());
		filepatternCharge.setText(settings.getFilePatternCharge());
		sliderBattSize.setValue(settings.getBattIconSize());

		// Signal stuff
		fileNameSignalIn.setText(settings.getFileSignalIn());
		fileNameSignalOut.setText(settings.getFileSignalOut());
		fileNameSignalInOut.setText(settings.getFileSignalInOut());
		fileSignalPattern.setText(settings.getFileSignalPattern());
		fileSignalPatternFully.setText(settings.getFileSignalEXtensionFully());
		sliderSignalSize.setValue(settings.getSignalIconSize());

		// Wifi stuff
		fileNameWifiIn.setText(settings.getFileWifiIn());
		fileNameWifiOut.setText(settings.getFileWifiOut());
		fileNameWifiInOut.setText(settings.getFileWifiInOut());
		fileWifiPattern.setText(settings.getFileWifiPattern());
		fileWifiPatternFully.setText(settings.getFileWifiEXtensionFully());
		sliderWifiSize.setValue(settings.getWifiIconSize());

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

	public RomSettings getSettings() {
		settings.setUseAdvancedResize(cboxUseAdvResize.isSelected());

		// Drawables
		settings.setSystemUIDrawableFolder((String) systemUIDrawableFolderCombo.getSelectedItem());
		settings.setFrameworkDrawableFolder((String) frameworkDrawableFolderCombo.getSelectedItem());
		// Battery
		settings.setFilePattern(filepattern.getText());
		settings.setFilePatternCharge(filepatternCharge.getText());
		settings.setBattIconSize(sliderBattSize.getValue());

		// Signal stuff
		settings.setFileSignalIn(fileNameSignalIn.getText());
		settings.setFileSignalOut(fileNameSignalOut.getText());
		settings.setFileSignalInOut(fileNameSignalInOut.getText());
		settings.setFileSignalPattern(fileSignalPattern.getText());
		settings.setFileSignalEXtensionFully(fileSignalPatternFully.getText());
		settings.setSignalIconSize(sliderSignalSize.getValue());
		// Wifi stuff
		settings.setFileWifiIn(fileNameWifiIn.getText());
		settings.setFileWifiOut(fileNameWifiOut.getText());
		settings.setFileWifiInOut(fileNameWifiInOut.getText());
		settings.setFileWifiPattern(fileWifiPattern.getText());
		settings.setFileWifiEXtensionFully(fileWifiPatternFully.getText());
		settings.setWifiIconSize(sliderWifiSize.getValue());
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

	@Override
	protected void validateControls() {

	}
}
