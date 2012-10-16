package de.og.batterycreator.creators;

import java.util.Vector;

import de.og.batterycreator.settings.StyleSettings;
import de.og.batterycreator.widgets.DrawableComboBox;

public class RomPreset {

	public static final String APPLY = "Apply Settings for Rom...";
	private static Vector<RomPreset> presets = new Vector<RomPreset>();

	static {
		presets.add(new RomPreset(APPLY, DrawableComboBox.FOLDER_XHDPI, StyleSettings.BATT_ICON_NAME_AOKP, StyleSettings.BATT_ICON_CHARGE_NAME_AOKP));
		presets.add(new RomPreset("Default", DrawableComboBox.FOLDER_XHDPI, StyleSettings.BATT_ICON_NAME_AOKP, StyleSettings.BATT_ICON_CHARGE_NAME_AOKP));
		presets.add(new RomPreset("Resurrection Remix JB 3.x.x", DrawableComboBox.FOLDER_XHDPI, StyleSettings.BATT_ICON_NAME_AOKP,
				StyleSettings.BATT_ICON_CHARGE_NAME_AOKP));
		presets.add(new RomPreset("Resurrection Remix ICS 2.6-2.7", DrawableComboBox.FOLDER_HDPI, StyleSettings.BATT_ICON_NAME_AOKP,
				StyleSettings.BATT_ICON_CHARGE_NAME_AOKP));
		presets.add(new RomPreset("HydraH2O ICS 1.x", DrawableComboBox.FOLDER_HDPI, StyleSettings.BATT_ICON_NAME_AOKP, StyleSettings.BATT_ICON_CHARGE_NAME_AOKP));
		presets.add(new RomPreset("RootBox JB 2.x", DrawableComboBox.FOLDER_HDPI, StyleSettings.BATT_ICON_NAME_AOKP, StyleSettings.BATT_ICON_CHARGE_NAME_AOKP));
		presets.add(new RomPreset("Original AOKP JB", DrawableComboBox.FOLDER_HDPI, StyleSettings.BATT_ICON_NAME_AOKP, StyleSettings.BATT_ICON_CHARGE_NAME_AOKP));
		presets.add(new RomPreset("Stock ROM ICS incl. JKay", DrawableComboBox.FOLDER_HDPI, StyleSettings.BATT_ICON_NAME_STOCK_ICS_JKAY,
				StyleSettings.BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY));
	}

	private final String romName;
	private final String zipResolutionFolder;
	private final String filePattern;
	private final String filePatternCharge;

	public RomPreset(final String romName, final String zipResolutionFolder, final String filePattern, final String filePatternCharge) {
		super();
		this.romName = romName;
		this.zipResolutionFolder = zipResolutionFolder;
		this.filePattern = filePattern;
		this.filePatternCharge = filePatternCharge;
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
}
