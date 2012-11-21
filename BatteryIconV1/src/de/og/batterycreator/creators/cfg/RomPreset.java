package de.og.batterycreator.creators.cfg;

import java.util.Vector;

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
	public static final int TOGGLE_XHDPI_S3 = 72;

	public static final int NOTIFICATION_HDPI = 3;
	public static final int NOTIFICATION_XHDPI = 4;

	public static final int BATT_ICON_HEIGHT_XHDPI = 36;
	public static final int BATT_ICON_HEIGHT_HDPI = 27;
	public static final int BATT_ICON_HEIGHT_HDPI_S3 = 38;
	public static final int BATT_ICON_HEIGHT_720DP = 48;
	public static final int BATT_ICON_HEIGHT_600DP = 43;
	public static final String BATT_ICON_CHARGE_NAME_AOKP = "stat_sys_battery_circle_charge_anim";
	public static final String BATT_ICON_NAME_AOKP = "stat_sys_battery_circle_";
	public static final String BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY = "stat_sys_battery_charge_anim";
	public static final String BATT_ICON_NAME_STOCK_ICS_JKAY = "stat_sys_battery_";

	public static final String FOLDER_XHDPI = "drawable-xhdpi";
	public static final String FOLDER_HDPI = "drawable-hdpi";
	public static final String FOLDER_HDPI_S3 = "drawable-hdpi";
	public static final String FOLDER_720DP = "drawable-sw720dp-xhdpi";
	public static final String FOLDER_600DP = "drawable-sw600dp-xhdpi";

	public static final String APPLY = "Apply Settings for Rom...";
	private static Vector<RomPreset> presets = new Vector<RomPreset>();

	static {
		presets.add(new RomPreset(APPLY, FOLDER_XHDPI, BATT_ICON_HEIGHT_XHDPI, FOLDER_HDPI, BATT_ICON_NAME_AOKP, BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI,
				NOTIFICATION_XHDPI, TOGGLE_XHDPI, false, WEATHER_HDPI));

		presets.add(new RomPreset("Default", FOLDER_XHDPI, BATT_ICON_HEIGHT_XHDPI, FOLDER_HDPI, BATT_ICON_NAME_AOKP, BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI,
				NOTIFICATION_XHDPI, TOGGLE_XHDPI, false, WEATHER_HDPI));

		presets.add(new RomPreset("Resurrection Remix JB 3.1.2 and above", FOLDER_XHDPI, BATT_ICON_HEIGHT_XHDPI, FOLDER_HDPI, BATT_ICON_NAME_AOKP,
				BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, false, WEATHER_HDPI));

		presets.add(new RomPreset("Resurrection Remix JB 3.x.x-3.1.1", FOLDER_XHDPI, BATT_ICON_HEIGHT_XHDPI, FOLDER_XHDPI, BATT_ICON_NAME_AOKP,
				BATT_ICON_CHARGE_NAME_AOKP, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, false, WEATHER_XHDPI));

		presets.add(new RomPreset("Resurrection Remix ICS 2.6-2.7", FOLDER_HDPI, BATT_ICON_HEIGHT_HDPI, FOLDER_HDPI, BATT_ICON_NAME_AOKP,
				BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI));

		presets.add(new RomPreset("RootBox JB 2.x - 3.x", FOLDER_HDPI, BATT_ICON_HEIGHT_HDPI, FOLDER_HDPI, BATT_ICON_NAME_AOKP, BATT_ICON_CHARGE_NAME_AOKP,
				LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI));

		presets.add(new RomPreset("[MORPHOLOGY SOCIETY] Galaxy S3", FOLDER_HDPI, BATT_ICON_HEIGHT_HDPI_S3, FOLDER_HDPI, BATT_ICON_NAME_STOCK_ICS_JKAY,
				BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI_S3, true, WEATHER_XHDPI));

		presets.add(new RomPreset("HydraH2O ICS 1.x", FOLDER_HDPI, BATT_ICON_HEIGHT_HDPI, FOLDER_HDPI, BATT_ICON_NAME_AOKP, BATT_ICON_CHARGE_NAME_AOKP,
				LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI));

		presets.add(new RomPreset("Original AOKP JB", FOLDER_HDPI, BATT_ICON_HEIGHT_HDPI, FOLDER_HDPI, BATT_ICON_NAME_AOKP, BATT_ICON_CHARGE_NAME_AOKP,
				LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI));

		presets.add(new RomPreset("Original CM9/CM10 + 1%-Mod!!!", FOLDER_HDPI, BATT_ICON_HEIGHT_HDPI, FOLDER_HDPI, BATT_ICON_NAME_AOKP,
				BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI));

		presets.add(new RomPreset("Stock ROM ICS incl. JKay", FOLDER_HDPI, BATT_ICON_HEIGHT_HDPI, FOLDER_HDPI, BATT_ICON_NAME_STOCK_ICS_JKAY,
				BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI));

		presets.add(new RomPreset("Full xhdpi AOKP ROM", FOLDER_XHDPI, BATT_ICON_HEIGHT_XHDPI, FOLDER_XHDPI, BATT_ICON_NAME_AOKP, BATT_ICON_CHARGE_NAME_AOKP,
				LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, false, WEATHER_XHDPI));

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
	private final int battsize;
	private final boolean useLidroid;

	public RomPreset(final String romName, final String systemUIDrawableFolder, final int battsize, final String frameworkDrawableFolder,
			final String filePattern, final String filePatternCharge, final int lockHandleSize, final int notificationHeight, final int toggleSize,
			final boolean useLidroid, final int weatherSize) {
		super();
		this.romName = romName;
		this.systemUIDrawableFolder = systemUIDrawableFolder;
		this.battsize = battsize;
		this.frameworkDrawableFolder = frameworkDrawableFolder;
		this.filePattern = filePattern;
		this.filePatternCharge = filePatternCharge;
		this.lockHandleSize = lockHandleSize;
		this.notificationHeight = notificationHeight;
		this.toggleSize = toggleSize;
		this.useLidroid = useLidroid;
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

	/**
	 * @return the battsize
	 */
	public int getBattsize() {
		return battsize;
	}

	/**
	 * @return the useLidroid
	 */
	public boolean isUseLidroid() {
		return useLidroid;
	}
}
