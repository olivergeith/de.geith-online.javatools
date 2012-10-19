package de.og.batterycreator.gui.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.creators.settings.StyleSettings;
import de.og.batterycreator.gui.ConfigPanel;
import de.og.batterycreator.gui.iconstore.IconStore;

public class LockHandleSelector extends JComboBox<ImageIcon> {
	private static final String CUSTOM_DIR = "./custom/lockhandles/";
	private static final String CUSTOM_OUT_DIR = "./pngs/lock/";

	private static final long serialVersionUID = -7712530632645291404L;
	private final ImageIcon nada = IconStore.nothingIcon;
	private static final ImageIcon origlock = new ImageIcon(LockHandleSelector.class.getResource("ic_lockscreen_handle_normal.png"));
	private static final ImageIcon entelock = new ImageIcon(LockHandleSelector.class.getResource("ic_lock_ente.png"));

	private final Vector<ImageIcon> handleList = new Vector<ImageIcon>();
	private final ConfigPanel configPane;

	private final Vector<String> filenamesAndPath = new Vector<String>();

	public LockHandleSelector(final ConfigPanel configPane) {
		super();
		this.configPane = configPane;
		initUI();
	}

	private void initUI() {
		fillIconVector();
		for (final ImageIcon icon : handleList) {
			if (!icon.equals(nada)) {
				final BufferedImage bimg = StaticImageHelper.resizeAdvanced(StaticImageHelper.convertImageIcon(icon), 64);
				addItem(new ImageIcon(bimg));
			} else {
				addItem(icon);
			}
		}
		setMaximumRowCount(8);
		setRenderer(new MyCellRenderer());
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// final ImageIcon icon = (ImageIcon) getSelectedItem();
				writeSelectedIcon();
			}
		});
	}

	protected void writeSelectedIcon() {
		filenamesAndPath.removeAllElements();
		final File outf = new File(CUSTOM_OUT_DIR + configPane.getSettings().getLockHandleFileName());
		final ImageIcon icon = getSelectedIcon();
		if (!icon.equals(nada)) {
			final int sizeTo = configPane.getSettings().getLockHandleSize();
			final File dir = new File(CUSTOM_OUT_DIR);
			if (!dir.exists())
				dir.mkdirs();
			final BufferedImage img = StaticImageHelper.resize(StaticImageHelper.convertImageIcon(icon), sizeTo);
			StaticImageHelper.writePNG(img, outf);
			filenamesAndPath.addElement(outf.getPath());
			System.out.println("LockHandle was created: " + outf.getPath());
		} else {
			outf.delete();
			System.out.println("LockHandle was deleted: " + outf.getPath());
		}
	}

	private ImageIcon getSelectedIcon() {
		final int index = getSelectedIndex();
		return handleList.get(index);
	}

	/**
	 * @return the filenamesAndPath
	 */
	public Vector<String> getFilenamesAndPath() {
		return filenamesAndPath;
	}

	private Vector<ImageIcon> fillIconVector() {
		handleList.add(nada);
		handleList.add(origlock);
		handleList.add(entelock);
		final File dir = new File(CUSTOM_DIR);
		if (!dir.exists())
			dir.mkdirs();
		if (dir.exists() && dir.isDirectory()) {
			final File[] pngs = dir.listFiles(new FilenameFilter() {

				public boolean accept(final File dir, final String name) {
					return name.toLowerCase().endsWith(".png");
				}
			});
			for (final File fi : pngs) {
				handleList.add(new ImageIcon(fi.getPath()));
			}
		}
		return handleList;
	}

	/**
	 * Renderer for WifiCreator-Combo
	 */
	private class MyCellRenderer implements ListCellRenderer<ImageIcon> {
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends ImageIcon> list, final ImageIcon value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof ImageIcon) {
				if (isSelected)
					renderer.setBackground(Color.darkGray.darker());
				else
					renderer.setBackground(Color.black);
				renderer.setForeground(Color.white);
				final ImageIcon icon = value;
				renderer.setIcon(icon);
				if (icon.equals(nada)) {
					renderer.setText("Don't include Lockhandle");
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
		f.setTitle("Hallo Emmy!!!!!!!");
		f.setBounds(200, 200, 300, 80);
		f.setLayout(new BorderLayout());
		final StyleSettings settings = new StyleSettings();
		final ConfigPanel cp = new ConfigPanel();
		cp.setSettings(settings);
		final LockHandleSelector combo = new LockHandleSelector(cp);
		combo.setSelectedIndex(0);
		f.add(combo, BorderLayout.CENTER);

		f.setVisible(true);
	}
}
