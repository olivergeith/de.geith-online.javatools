package de.og.batterycreator.gui.widgets;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RecurseFileSetSelector extends JComboBox<RecurseFileSet> {
	private static final long serialVersionUID = -2767025548199058416L;

	private final OverviewPanel overPane = new OverviewPanel();
	private final Vector<RecurseFileSet> sets = new Vector<RecurseFileSet>();
	private RecurseFileSet selectedSet;
	private final String rootDir = "./custom/XTRAS";

	public RecurseFileSetSelector() {
		super();
		initUI();
	}

	/**
	 * @return the overviewPanel
	 */
	public JPanel getOverviewPanel() {
		return overPane;
	}

	private File[] findCustomDirs(final File dir) {
		if (dir.isDirectory()) {
			final File[] subdirs = dir.listFiles(new FileFilter() {
				@Override
				public boolean accept(final File file) {
					return file.isDirectory();
				}
			});
			return subdirs;
		}
		return null;
	}

	private void initUI() {
		addItem(new RecurseFileSet("Nada"));
		addSetsFromFilesystem();

		setToolTipText("Choose your Fileset");
		System.out.println("Loading Custom File Sets!");
		overPane.add(this, BorderLayout.NORTH);
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final RecurseFileSet selected = (RecurseFileSet) getSelectedItem();
				final int index = getSelectedIndex();
				if (index > 0) {
					selectedSet = selected;

					overPane.setText(selected.getContentHTML());
				} else {
					overPane.setText("   Choose File-Set from Dropdownbox");
					selectedSet = null;
				}
			}
		});
		if (getItemCount() > 0)
			setSelectedIndex(0);
	}

	/**
	 * 
	 */
	private void addSetsFromFilesystem() {
		final File dir = new File(rootDir);
		if (!dir.exists())
			dir.mkdirs();
		// find subdirs with icon sets
		final File[] setDirs = findCustomDirs(dir);
		if (setDirs != null) {
			for (final File setDir : setDirs) {
				final RecurseFileSet set = new RecurseFileSet(setDir.getPath());
				sets.add(set);
				addItem(set);
				// addItem(set.getRepresentivIcon());
			}
		}
	}

	/**
	 * For testing purposes !!!
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {

		final JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Hallo Emmy!!!!!!!");
		f.setBounds(200, 200, 640, 600);
		f.setLayout(new BorderLayout());
		// final IconSetSelector combo = new IconSetSelector("Weather",
		// "./custom/weather/");
		final RecurseFileSetSelector combo = new RecurseFileSetSelector();
		if (combo.getItemCount() > 0)
			combo.setSelectedIndex(0);
		f.add(combo, BorderLayout.NORTH);
		f.add(combo.getOverviewPanel(), BorderLayout.CENTER);

		f.setVisible(true);
	}

	/**
	 * @return the selectedSet
	 */
	public RecurseFileSet getSelectedSet() {
		return selectedSet;
	}

}
