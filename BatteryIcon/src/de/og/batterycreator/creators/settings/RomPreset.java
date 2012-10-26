package de.og.batterycreator.creators.settings;

import java.util.Vector;

import de.og.batterycreator.gui.widgets.DrawableComboBox;

public class RomPreset {

	public static final int TOGGLE_XHDPI = 64;
	public static final int TOGGLE_HDPI = 48;
	public static final int LOCK_XHDPI = 216;
	public static final int LOCK_HDPI = 162;
	public static final int NOTIFICATION_HDPI = 3;
	public static final int NOTIFICATION_XHDPI = 4;

	public static final String APPLY = "Apply Settings for Rom...";
	private static Vector<RomPreset> presets = new Vector<RomPreset>();

	static {
		presets.add(new RomPreset(APPLY, DrawableComboBox.FOLDER_XHDPI, StyleSettings.BATT_ICON_NAME_AOKP, StyleSettings.BATT_ICON_CHARGE_NAME_AOKP,
				LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI));
		presets.add(new RomPreset("Default", DrawableComboBox.FOLDER_XHDPI, StyleSettings.BATT_ICON_NAME_AOKP, StyleSettings.BATT_ICON_CHARGE_NAME_AOKP,
				LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI));
		presets.add(new RomPreset("Resurrection Remix JB 3.x.x", DrawableComboBox.FOLDER_XHDPI, StyleSettings.BATT_ICON_NAME_AOKP,
				StyleSettings.BATT_ICON_CHARGE_NAME_AOKP, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI));
		presets.add(new RomPreset("Resurrection Remix ICS 2.6-2.7", DrawableComboBox.FOLDER_HDPI, StyleSettings.BATT_ICON_NAME_AOKP,
				StyleSettings.BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI));
		presets.add(new RomPreset("HydraH2O ICS 1.x", DrawableComboBox.FOLDER_HDPI, StyleSettings.BATT_ICON_NAME_AOKP,
				StyleSettings.BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI));
		presets.add(new RomPreset("RootBox JB 2.x - 3.x", DrawableComboBox.FOLDER_HDPI, StyleSettings.BATT_ICON_NAME_AOKP,
				StyleSettings.BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI));
		presets.add(new RomPreset("Original AOKP JB", DrawableComboBox.FOLDER_HDPI, StyleSettings.BATT_ICON_NAME_AOKP,
				StyleSettings.BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI));
		presets.add(new RomPreset("Stock ROM ICS incl. JKay", DrawableComboBox.FOLDER_HDPI, StyleSettings.BATT_ICON_NAME_STOCK_ICS_JKAY,
				StyleSettings.BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI));
	}

	private final String romName;
	private final String zipResolutionFolder;
	private final String filePattern;
	private final String filePatternCharge;

	private final int toggleSize;
	private final int lockHandleSize;
	private final int notificationHeight;

	public RomPreset(final String romName, final String zipResolutionFolder, final String filePattern, final String filePatternCharge,
			final int lockHandleSize, final int notificationHeight, final int toggleSize) {
		super();
		this.romName = romName;
		this.zipResolutionFolder = zipResolutionFolder;
		this.filePattern = filePattern;
		this.filePatternCharge = filePatternCharge;
		this.lockHandleSize = lockHandleSize;
		this.notificationHeight = notificationHeight;
		this.toggleSize = toggleSize;
	}

	public static Vector<RomPreset> getPresets() {
		return presets;
	}

	public String getRomName() {
		return romName;
	}

	public String getZipResolutionFolder() {
		return zipResolutionFolder;
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
}
