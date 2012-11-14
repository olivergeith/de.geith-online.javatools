package de.og.batterycreator.gui.panels.iconset;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;

import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.OverviewPanel;

public class IconSetSelector extends JPanel {
	private static final long serialVersionUID = -2767025548199058416L;

	private final JComboBox<ImageIcon> combo = new JComboBox<ImageIcon>();
	private final OverviewPanel overPane = new OverviewPanel();
	private final ImageIcon nada = IconStore.nothingIcon;
	private Vector<String> filenamesAndPath = new Vector<String>();
	private final Vector<IconSet> iconSets = new Vector<IconSet>();

	private final String rootDir;
	private final String setTypeName;

	public IconSetSelector(final String setTypeName, final String rootDir) {
		super();
		this.rootDir = rootDir;
		this.setTypeName = setTypeName;
		initUI();
	}

	public String getProviderName() {
		return setTypeName;
	}

	private File[] findCustomDirs(final File dir) {
		if (dir.isDirectory()) {
			final File[] subdirs = dir.listFiles(new FileFilter() {
				@Override
				public boolean accept(final File file) {
					return file.isDirectory() && IconSet.findPNGs(file).length > 0;
				}
			});
			return subdirs;
		}
		return null;
	}

	/**
	 * @return the filenamesAndPath
	 */
	public Vector<String> getAllFilenamesAndPath() {
		return filenamesAndPath;
	}

	private void initUI() {
		combo.addItem(nada);
		addSetsFromFilesystem();

		combo.setRenderer(new MyCellRenderer());
		combo.setToolTipText("Choose your " + setTypeName + " Iconset");
		System.out.println("Loading Custom " + setTypeName + " Icon Sets!");
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final ImageIcon icon = (ImageIcon) combo.getSelectedItem();
				if (!icon.equals(nada)) {
					final int index = combo.getSelectedIndex();
					final IconSet set = iconSets.elementAt(index - 1);
					overPane.setOverview(set.getOverviewsmall());
					overPane.setText("");
				} else {
					overPane.setOverview(icon);
					overPane.setText("   Choose " + setTypeName + "-Set from Dropdownbox");
				}
			}
		});
		combo.setSelectedIndex(0);
		combo.setMaximumSize(new Dimension(400, 40));
		combo.setMaximumRowCount(10);

		setLayout(new BorderLayout());
		this.add(overPane, BorderLayout.CENTER);
		makeButtonBar();
	}

	/**
	 * Creating buttonbar
	 */
	private void makeButtonBar() {
		final JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.add(combo);
		this.add(toolBar, BorderLayout.NORTH);
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
				final IconSet set = new IconSet(setDir);
				iconSets.add(set);
				combo.addItem(set.getOverviewStripe());
				// addItem(set.getRepresentivIcon());
			}
		}
	}

	/**
	 * Renderer for WifiCreator-Combo
	 */
	private class MyCellRenderer implements ListCellRenderer<ImageIcon> {
		private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends ImageIcon> list, final ImageIcon value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof ImageIcon) {
				// if (isSelected)
				// renderer.setBackground(Color.darkGray.darker());
				// else
				// renderer.setBackground(Color.black);
				// renderer.setForeground(Color.white);
				final ImageIcon icon = value;
				renderer.setIcon(icon);
				if (index > 0) {
					final IconSet set = iconSets.elementAt(index - 1);
					renderer.setText(set.getName());
				}
				if (icon.equals(nada)) {
					renderer.setText("No " + setTypeName + " Icons");
				}
			}
			return renderer;
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
		final IconSetSelector combo = new IconSetSelector("Toggle", "./custom/toggles/");
		f.add(combo, BorderLayout.CENTER);

		f.setVisible(true);
	}

	public void createAllImages(final int size) {
		final ImageIcon icon = (ImageIcon) combo.getSelectedItem();
		if (!icon.equals(nada)) {
			final int index = combo.getSelectedIndex();
			final IconSet set = iconSets.elementAt(index - 1);

			final IconSetDeployer depl = new IconSetDeployer(set, setTypeName);
			depl.createAllImages(size);
			filenamesAndPath = depl.getAllFilenamesAndPath();
		} else {
			filenamesAndPath = new Vector<String>();
		}
	}
}
