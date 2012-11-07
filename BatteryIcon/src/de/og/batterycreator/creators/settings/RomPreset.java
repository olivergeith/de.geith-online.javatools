package de.og.batterycreator.creators.settings;

import java.util.Vector;

import de.og.batterycreator.gui.widgets.DrawableComboBox;

public class RomPreset {

	// is in frameworkres...
	public static final int WEATHER_XHDPI = 162;
	public static final int WEATHER_HDPI = 144;
	public static final int LOCK_XHDPI = 216;
	public static final int LOCK_HDPI = 162;

	// in systemUI
	public static final int TOGGLE_720DP = 96;
	public static final int TOGGLE_600DP = 84;
	public static final int TOGGLE_XHDPI = 64;
	public static final int TOGGLE_HDPI = 48;
	public static final int NOTIFICATION_HDPI = 3;
	public static final int NOTIFICATION_XHDPI = 4;
	public static final int BATT_ICON_HEIGHT_XHDPI = 36;
	public static final int BATT_ICON_HEIGHT_HDPI = 27;
	public static final int BATT_ICON_HEIGHT_720DP = 48;
	public static final int BATT_ICON_HEIGHT_600DP = 43;

	public static final String APPLY = "Apply Settings for Rom...";
	private static Vector<RomPreset> presets = new Vector<RomPreset>();

	static {
		presets.add(new RomPreset(APPLY, DrawableComboBox.FOLDER_XHDPI, DrawableComboBox.FOLDER_HDPI, StyleSettings.BATT_ICON_NAME_AOKP,
				StyleSettings.BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, WEATHER_HDPI));

		presets.add(new RomPreset("Default", DrawableComboBox.FOLDER_XHDPI, DrawableComboBox.FOLDER_HDPI, StyleSettings.BATT_ICON_NAME_AOKP,
				StyleSettings.BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, WEATHER_HDPI));

		presets.add(new RomPreset("Resurrection Remix JB 3.1.2 and above", DrawableComboBox.FOLDER_XHDPI, DrawableComboBox.FOLDER_HDPI,
				StyleSettings.BATT_ICON_NAME_AOKP, StyleSettings.BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, WEATHER_HDPI));

		presets.add(new RomPreset("Resurrection Remix JB 3.x.x-3.1.1", DrawableComboBox.FOLDER_XHDPI, DrawableComboBox.FOLDER_XHDPI,
				StyleSettings.BATT_ICON_NAME_AOKP, StyleSettings.BATT_ICON_CHARGE_NAME_AOKP, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, WEATHER_XHDPI));

		presets.add(new RomPreset("Resurrection Remix ICS 2.6-2.7", DrawableComboBox.FOLDER_HDPI, DrawableComboBox.FOLDER_HDPI,
				StyleSettings.BATT_ICON_NAME_AOKP, StyleSettings.BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, WEATHER_HDPI));

		presets.add(new RomPreset("HydraH2O ICS 1.x", DrawableComboBox.FOLDER_HDPI, DrawableComboBox.FOLDER_HDPI, StyleSettings.BATT_ICON_NAME_AOKP,
				StyleSettings.BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, WEATHER_HDPI));

		presets.add(new RomPreset("RootBox JB 2.x - 3.x", DrawableComboBox.FOLDER_HDPI, DrawableComboBox.FOLDER_HDPI, StyleSettings.BATT_ICON_NAME_AOKP,
				StyleSettings.BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, WEATHER_HDPI));

		presets.add(new RomPreset("Original AOKP JB", DrawableComboBox.FOLDER_HDPI, DrawableComboBox.FOLDER_HDPI, StyleSettings.BATT_ICON_NAME_AOKP,
				StyleSettings.BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, WEATHER_HDPI));

		presets.add(new RomPreset("Original CM9/CM10 + 1%-Mod!!!", DrawableComboBox.FOLDER_HDPI, DrawableComboBox.FOLDER_HDPI,
				StyleSettings.BATT_ICON_NAME_AOKP, StyleSettings.BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, WEATHER_HDPI));

		presets.add(new RomPreset("Stock ROM ICS incl. JKay", DrawableComboBox.FOLDER_HDPI, DrawableComboBox.FOLDER_HDPI,
				StyleSettings.BATT_ICON_NAME_STOCK_ICS_JKAY, StyleSettings.BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI,
				WEATHER_HDPI));
	}

	private final String romName;
	private final String systemUIDrawableFolder;
	private final String frameworkDrawableFolder;
	private final String filePattern;
	private final String filePatternCharge;
	private final int weatherSize;
	private final int toggleSize;
	private final int lockHandleSize;
	private final int notificationHeight;

	public RomPreset(final String romName, final String systemUIDrawableFolder, final String frameworkDrawableFolder, final String filePattern,
			final String filePatternCharge, final int lockHandleSize, final int notificationHeight, final int toggleSize, final int weatherSize) {
		super();
		this.romName = romName;
		this.systemUIDrawableFolder = systemUIDrawableFolder;
		this.frameworkDrawableFolder = frameworkDrawableFolder;
		this.filePattern = filePattern;
		this.filePatternCharge = filePatternCharge;
		this.lockHandleSize = lockHandleSize;
		this.notificationHeight = notificationHeight;
		this.toggleSize = toggleSize;
		this.weatherSize = weatherSize;
	}

	public static Vector<RomPreset> getPresets() {
		return presets;
	}

	public String getRomName() {
		return romName;
	}

	public String getSystemUIDrawableFolder() {
		return systemUIDrawableFolder;
	}

	public String getFilePattern() {
		return filePattern;
	}

	public String getFilePatternCharge() {
		return filePatternCharge;
	}

	@Override
	public String toString() {
		return romName;
	}

	public int getLockHandleSize() {
		return lockHandleSize;
	}

	public int getNotificationHeight() {
		return notificationHeight;
	}

	/**
	 * @return the toggleSize
	 */
	public int getToggleSize() {
		return toggleSize;
	}

	/**
	 * @return the weatherSize
	 */
	public int getWeatherSize() {
		return weatherSize;
	}

	/**
	 * @return the frameworkDrawableFolder
	 */
	public String getFrameworkDrawableFolder() {
		return frameworkDrawableFolder;
	}
}
