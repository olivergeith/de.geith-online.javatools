package de.og.batterycreator.creators;

import java.util.Vector;

public class RomPreset {

	public static final String APPLY = "Apply Settings for Rom...";
	private static Vector<RomPreset> presets = new Vector<RomPreset>();

	static {
		presets.add(new RomPreset(APPLY, StyleSettings.FOLDER_XHDPI, StyleSettings.ICON_NAME_AOKP, StyleSettings.ICON_CHARGE_NAME_AOKP));
		presets.add(new RomPreset("Default", StyleSettings.FOLDER_XHDPI, StyleSettings.ICON_NAME_AOKP, StyleSettings.ICON_CHARGE_NAME_AOKP));
		presets.add(new RomPreset("Resurrection Remix JB 3.x.x", StyleSettings.FOLDER_XHDPI, StyleSettings.ICON_NAME_AOKP, StyleSettings.ICON_CHARGE_NAME_AOKP));
		presets.add(new RomPreset("Resurrection Remix ICS 2.6-2.7", StyleSettings.FOLDER_HDPI, StyleSettings.ICON_NAME_AOKP,
				StyleSettings.ICON_CHARGE_NAME_AOKP));
		presets.add(new RomPreset("HydraH2O 1.x", StyleSettings.FOLDER_HDPI, StyleSettings.ICON_NAME_AOKP, StyleSettings.ICON_CHARGE_NAME_AOKP));
		presets.add(new RomPreset("Stock ROM ICS incl. JKay", StyleSettings.FOLDER_HDPI, StyleSettings.ICON_NAME_STOCK_ICS_JKAY,
				StyleSettings.ICON_CHARGE_NAME_STOCK_ICS_JKAY));
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
