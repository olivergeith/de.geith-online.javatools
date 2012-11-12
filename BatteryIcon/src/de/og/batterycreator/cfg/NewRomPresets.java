package de.og.batterycreator.cfg;

import java.util.Vector;

public class NewRomPresets {

	private static final int HDPI = 0;
	private static final int XHDPI = 1;
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
	private static Vector<NewRomPresets> presets = new Vector<NewRomPresets>();

	private final String romName;
	private String systemUIDrawableFolder;
	private String frameworkDrawableFolder;
	private String filePattern;
	private String filePatternCharge;
	private int weatherSize;
	private int toggleSize;
	private int lockHandleSize;
	private int notificationHeight;
	private int battsize;
	private boolean useLidroid;

	static {
		final NewRomPresets s1 = new NewRomPresets(APPLY, XHDPI);
		s1.setFrameworkDrawableFolder(FOLDER_HDPI);
		s1.setLockHandleSize(LOCK_HDPI);
		s1.setWeatherSize(WEATHER_HDPI);

		final NewRomPresets s2 = new NewRomPresets("Default", XHDPI);
		s2.setFrameworkDrawableFolder(FOLDER_HDPI);
		s2.setLockHandleSize(LOCK_HDPI);
		s2.setWeatherSize(WEATHER_HDPI);

		final NewRomPresets s3 = new NewRomPresets("Resurrection Remix JB 3.1.2 and above", XHDPI);
		s3.setFrameworkDrawableFolder(FOLDER_HDPI);
		s3.setLockHandleSize(LOCK_HDPI);
		s3.setWeatherSize(WEATHER_HDPI);

		final NewRomPresets s4 = new NewRomPresets("Resurrection Remix JB 3.0.0 - 3.1.1 and above", XHDPI);

		final NewRomPresets s5 = new NewRomPresets("Resurrection final Remix ICS 2.6-2.7", HDPI);

		final NewRomPresets s6 = new NewRomPresets("RootBox JB 2.x - 3.x7", HDPI);

		final NewRomPresets s7 = new NewRomPresets("[MORPHOLOGY SOCIETY] Galaxy S3", HDPI);
		s7.setFilePatternCharge(BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY);
		s7.setFilePattern(BATT_ICON_NAME_STOCK_ICS_JKAY);
		s7.setUseLidroid(true);
		s7.setBattsize(BATT_ICON_HEIGHT_HDPI_S3);
		s7.setToggleSize(TOGGLE_XHDPI_S3);

		final NewRomPresets s8 = new NewRomPresets("HydraH2O ICS 1.x", HDPI);

		final NewRomPresets s9 = new NewRomPresets("Original AOKP JB", HDPI);

		final NewRomPresets s10 = new NewRomPresets("Original CM9/CM10 (+1%-Mod!!!)", HDPI);

		final NewRomPresets s11 = new NewRomPresets("Stock ROM ICS incl. JKay", HDPI);
		s11.setFilePatternCharge(BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY);
		s11.setFilePattern(BATT_ICON_NAME_STOCK_ICS_JKAY);

		final NewRomPresets s12 = new NewRomPresets("Full XHDPI Rom AOKP", XHDPI);

		presets.add(s1);
		presets.add(s2);
		presets.add(s3);
		presets.add(s4);
		presets.add(s5);
		presets.add(s6);
		presets.add(s7);
		presets.add(s8);
		presets.add(s9);
		presets.add(s10);
		presets.add(s11);
		presets.add(s12);
	}

	/**
	 * @param romName
	 * @param xhdpi
	 */
	public NewRomPresets(final String romName, final int type) {
		super();

		this.romName = romName;
		filePattern = BATT_ICON_NAME_AOKP;
		filePatternCharge = BATT_ICON_CHARGE_NAME_AOKP;
		useLidroid = false;

		if (type == XHDPI) {
			systemUIDrawableFolder = FOLDER_XHDPI;
			battsize = BATT_ICON_HEIGHT_XHDPI;
			frameworkDrawableFolder = FOLDER_XHDPI;
			lockHandleSize = LOCK_XHDPI;
			notificationHeight = NOTIFICATION_XHDPI;
			toggleSize = TOGGLE_XHDPI;
			weatherSize = WEATHER_XHDPI;
		} else if (type == HDPI) {
			systemUIDrawableFolder = FOLDER_HDPI;
			battsize = BATT_ICON_HEIGHT_HDPI;
			frameworkDrawableFolder = FOLDER_HDPI;
			lockHandleSize = LOCK_HDPI;
			notificationHeight = NOTIFICATION_HDPI;
			toggleSize = TOGGLE_HDPI;
			weatherSize = WEATHER_HDPI;
		}
	}

	public NewRomPresets(final String romName, final String systemUIDrawableFolder, final int battsize, final String frameworkDrawableFolder,
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

	public String getSystemUIDrawableFolder() {
		return systemUIDrawableFolder;
	}

	public void setSystemUIDrawableFolder(final String systemUIDrawableFolder) {
		this.systemUIDrawableFolder = systemUIDrawableFolder;
	}

	public String getFrameworkDrawableFolder() {
		return frameworkDrawableFolder;
	}

	public void setFrameworkDrawableFolder(final String frameworkDrawableFolder) {
		this.frameworkDrawableFolder = frameworkDrawableFolder;
	}

	public String getFilePattern() {
		return filePattern;
	}

	public void setFilePattern(final String filePattern) {
		this.filePattern = filePattern;
	}

	public String getFilePatternCharge() {
		return filePatternCharge;
	}

	public void setFilePatternCharge(final String filePatternCharge) {
		this.filePatternCharge = filePatternCharge;
	}

	public int getWeatherSize() {
		return weatherSize;
	}

	public void setWeatherSize(final int weatherSize) {
		this.weatherSize = weatherSize;
	}

	public int getToggleSize() {
		return toggleSize;
	}

	public void setToggleSize(final int toggleSize) {
		this.toggleSize = toggleSize;
	}

	public int getLockHandleSize() {
		return lockHandleSize;
	}

	public void setLockHandleSize(final int lockHandleSize) {
		this.lockHandleSize = lockHandleSize;
	}

	public int getNotificationHeight() {
		return notificationHeight;
	}

	public void setNotificationHeight(final int notificationHeight) {
		this.notificationHeight = notificationHeight;
	}

	public int getBattsize() {
		return battsize;
	}

	public void setBattsize(final int battsize) {
		this.battsize = battsize;
	}

	public boolean isUseLidroid() {
		return useLidroid;
	}

	public void setUseLidroid(final boolean useLidroid) {
		this.useLidroid = useLidroid;
	}

	public String getRomName() {
		return romName;
	}

}
