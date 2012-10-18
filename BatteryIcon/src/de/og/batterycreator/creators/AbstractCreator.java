package de.og.batterycreator.creators;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import og.basics.gui.file.FileDialogs;
import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.creators.settings.StyleSettings;

public abstract class AbstractCreator {
	private static final String SETTINGS_EXTENSION = ".cfg";
	private static final String SETTINGS_DIR = "./stylSettings/";

	protected final Vector<ImageIcon> iconMap = new Vector<ImageIcon>();
	protected final Vector<String> filenames = new Vector<String>();;
	protected final Vector<String> filenamesAndPath = new Vector<String>();;
	protected ImageIcon overview = null;
	protected StyleSettings stylSettings = new StyleSettings();

	// ###############################################################################
	// Abstracte Methoden
	// ###############################################################################
	@Override
	public abstract String toString();

	public abstract void createAllImages();

	public abstract ImageIcon createOverview();

	// ###############################################################################
	// Settings
	// ###############################################################################

	public StyleSettings getStylSettings() {
		return stylSettings;
	}

	public void setStylSettings(final StyleSettings stylSettings) {
		this.stylSettings = stylSettings;
	}

	// ###############################################################################
	// Writing Stuff to Filesystem
	// ###############################################################################
	protected BufferedImage writeFile(final String filename, final BufferedImage img) {
		// getting filename
		final File file = new File(getPath() + File.separator + filename);
		return writeFile(file, img);
	}

	protected BufferedImage writeFile(final File file, BufferedImage img) {
		// Pfad anlegen falls nicht vorhanden
		final File pa = new File(getPath() + File.separator);
		pa.mkdirs();
		// resize ?
		if (stylSettings.getTargetIconSize() != img.getHeight()) {
			// do the resizing before save
			if (stylSettings.isUseAdvancedResize())
				img = StaticImageHelper.resizeAdvanced(img, stylSettings.getTargetIconSize());
			else
				img = StaticImageHelper.resize(img, stylSettings.getTargetIconSize());
		}
		// the writing
		writePNG(img, file);
		return img;
	}

	protected void writeOverviewFile(final BufferedImage overview) {
		final File file = new File(getPath() + File.separator + "overview_" + getName() + ".png");
		writePNG(overview, file);
	}

	public void writePNG(final BufferedImage img, final File file) {
		try {
			file.createNewFile();
			while (!file.canWrite()) {
				// do nothing until it can write...
			}
			ImageIO.write(img, "png", file);
		} catch (final IOException e) {
			System.out.println("Oh, Icon could not be written!!...Trying again!");
			try {
				ImageIO.write(img, "png", file);
			} catch (final IOException e1) {
				System.out.println("Hm that's strange...still not writable!...giving up!");
			}
		}
	}

	// ###############################################################################
	// Persisting Settings
	// ###############################################################################
	public void persistSettings() {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR);
			if (!pa.exists())
				pa.mkdirs();
			final String filename = SETTINGS_DIR + getName() + SETTINGS_EXTENSION;
			final File saveFile = FileDialogs.saveFile(pa, new File(filename), SETTINGS_EXTENSION, "IconSettings");
			if (saveFile != null) {
				final FileOutputStream file = new FileOutputStream(saveFile);
				final ObjectOutputStream o = new ObjectOutputStream(file);
				o.writeObject(stylSettings);
				o.close();
			}
		} catch (final IOException e) {
			System.err.println(e);
		}
	}

	public void loadSettings() {
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
				stylSettings = (StyleSettings) o.readObject();
				o.close();
			}
		} catch (final IOException e) {
			System.err.println(e);
		} catch (final ClassNotFoundException e) {
			System.err.println(e);
		}
	}

	// ###############################################################################
	// All filenames and Icons
	// ###############################################################################
	public Vector<String> getAllFilenamesAndPath() {
		return filenamesAndPath;
	}

	public Vector<String> getFilenames() {
		return filenames;
	}

	public Vector<ImageIcon> getIcons() {
		return iconMap;
	}

	// ###############################################################################
	// primitive Getter
	// ###############################################################################
	public String getPath() {
		return "./pngs/" + toString();
	}

	public String getName() {
		return toString();
	}

	// ###############################################################################
	// Grafics2D
	// ###############################################################################
	protected Graphics2D initGrafics2D(final BufferedImage img) {
		final Graphics2D g2d = img.createGraphics();
		g2d.setFont(stylSettings.getFont());
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(stylSettings.getStrokewidth()));
		return g2d;
	}

}
