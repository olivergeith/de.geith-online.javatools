package de.og.batterycreator.cfg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import og.basics.gui.file.FileDialogs;

public class SettingsPersistor {
	private static final String SETTINGS_EXTENSION = ".cfg";
	private static final String SETTINGS_DIR = "./stylSettings/";

	// ###############################################################################
	// Persisting Settings
	// ###############################################################################
	public static void saveRomSettings(final String name, final RomSettings settings) {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR);
			if (!pa.exists())
				pa.mkdirs();
			final String filename = SETTINGS_DIR + name + SETTINGS_EXTENSION;
			final File saveFile = FileDialogs.saveFile(pa, new File(filename), SETTINGS_EXTENSION, "RomSettings");
			if (saveFile != null) {
				final FileOutputStream file = new FileOutputStream(saveFile);
				final ObjectOutputStream o = new ObjectOutputStream(file);
				o.writeObject(settings);
				o.close();
			}
		} catch (final IOException e) {
			System.err.println(e);
		}
	}

	public static RomSettings loadRomSettings() {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR);
			if (!pa.exists())
				pa.mkdirs();

			// final String filename = "./stylSettings/" + getName() + ".cfg";
			final File loadFile = FileDialogs.chooseFile(pa, SETTINGS_EXTENSION, "IconSettings");
			if (loadFile != null) {
				final FileInputStream file = new FileInputStream(loadFile);
				final ObjectInputStream o = new ObjectInputStream(file);
				final RomSettings settings = (RomSettings) o.readObject();
				o.close();
				return settings;
			}
		} catch (final IOException e) {
			System.err.println(e);
		} catch (final ClassNotFoundException e) {
			System.err.println(e);
		}
		return null;
	}

}
