package og.basics.gui.file;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FileDialogs {

	/**
	 * Öffnet einen File-Opendialog für Verzeichnisse
	 * 
	 * @param startPath
	 * @param title
	 * @return
	 */
	public static String chooseDir(final String startPath) {
		final File home = new File(startPath);
		final File selected = chooseDir(home);
		if (selected != null)
			return selected.getPath();
		else
			return null;
	}

	/**
	 * Öffnet einen File-Opendialog für Verzeichnisse
	 * 
	 * @param startDir
	 * @param title
	 * @return
	 */
	public static File chooseDir(final File startDir) {
		final JFileChooser chooser = new JFileChooser(startDir);
		chooser.setPreferredSize(new Dimension(800, 600));
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setVisible(true);
		final int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			final File selected = chooser.getSelectedFile();
			return selected;
		} else {
			chooser.setVisible(false);
		}
		return null;
	}

	/**
	 * Öffnet einen File-Opendialog für Dateien
	 * 
	 * @param startDir
	 * @param title
	 * @return
	 */
	public static File chooseFile(final File startDir, final String filter, final String description) {
		final JFileChooser chooser = new JFileChooser(startDir);
		chooser.setPreferredSize(new Dimension(800, 600));
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return description;
			}

			@Override
			public boolean accept(final File f) {
				return f.getName().endsWith(filter);
			}
		});
		chooser.setVisible(true);

		final int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			final File selected = chooser.getSelectedFile();
			return selected;
		} else {
			chooser.setVisible(false);
		}
		return null;
	}

	/**
	 * Öffnet einen File-Opendialog für Dateien
	 * 
	 * @param startDir
	 * @param title
	 * @return
	 */
	public static File saveFile(final File startDir, final File file, final String filter, final String description) {
		final JFileChooser chooser = new JFileChooser(startDir);
		chooser.setSelectedFile(file);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return description;
			}

			@Override
			public boolean accept(final File f) {
				return f.getName().endsWith(filter);
			}
		});
		chooser.setPreferredSize(new Dimension(600, 400));
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setVisible(true);
		final int result = chooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			final File selected = chooser.getSelectedFile();
			return selected;
		} else {
			chooser.setVisible(false);
		}
		return null;
	}

}
