package de.og.batterycreator.gui.widgets.lockhandleselector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.cfg.StyleSettings;
import de.og.batterycreator.creators.IconProviderInterface;
import de.og.batterycreator.gui.ConfigPanel;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.OverviewPanel;

public class LockHandleSelector extends JComboBox<ImageIcon> implements IconProviderInterface {
	private static final String PROVIDER_NAME = "Lockhandle";
	private static final String CUSTOM_DIR = "./custom/lockhandles/";
	private static final String CUSTOM_OUT_DIR = "./pngs/deploy/lock/";

	private static final long serialVersionUID = -7712530632645291404L;
	private final ImageIcon nada = IconStore.nothingIcon;
	private static final ImageIcon origlock = new ImageIcon(LockHandleSelector.class.getResource("ic_lockscreen_handle_normal.png"));
	private static final ImageIcon androidlock = new ImageIcon(LockHandleSelector.class.getResource("ic_lock_android.png"));
	private static final ImageIcon entelock = new ImageIcon(LockHandleSelector.class.getResource("ic_lock_ente.png"));

	private final Vector<ImageIcon> handleList = new Vector<ImageIcon>();
	private final ConfigPanel configPane;
	protected OverviewPanel overPane = new OverviewPanel();

	private final Vector<String> filenamesAndPath = new Vector<String>();

	public LockHandleSelector(final ConfigPanel configPane) {
		super();
		this.configPane = configPane;
		initUI();
	}

	private void initUI() {
		fillIconVector();
		overPane.add(this, BorderLayout.NORTH);
		for (final ImageIcon icon : handleList) {
			if (!icon.equals(nada)) {
				final BufferedImage bimg = StaticImageHelper.resizeAdvanced2Height(StaticImageHelper.convertImageIcon(icon), 64);
				addItem(new ImageIcon(bimg));
			} else {
				addItem(icon);
			}
		}
		setPreferredSize(new Dimension(200, 30));
		setMaximumRowCount(8);
		setRenderer(new MyCellRenderer());
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// final int sizeTo =
				// configPane.getSettings().getLockHandleSize();
				// writeSelectedIcon(sizeTo);
				updateOverview();
			}

		});
		setSelectedIndex(0);
	}

	private void updateOverview() {
		final ImageIcon icon = getSelectedIcon();
		if (!icon.equals(nada)) {
			overPane.setOverview(icon);
			overPane.setText("   This will be your lockring");
		} else {
			overPane.setText("   Choose Lockring from Dropdownbox");
			overPane.setOverview(icon);
		}

	}

	@Override
	public void createAllImages(final int size) {
		filenamesAndPath.removeAllElements();
		final File outf = new File(CUSTOM_OUT_DIR + configPane.getSettings().getLockHandleFileName());
		final ImageIcon icon = getSelectedIcon();
		if (!icon.equals(nada)) {
			System.out.println("Creating Lockhandle");
			final File dir = new File(CUSTOM_OUT_DIR);
			if (!dir.exists())
				dir.mkdirs();
			final BufferedImage img = StaticImageHelper.resize2Height(StaticImageHelper.convertImageIcon(icon), size);
			StaticImageHelper.writePNG(img, outf);
			filenamesAndPath.addElement(outf.getPath());
		} else {
			outf.delete();
		}
	}

	private ImageIcon getSelectedIcon() {
		final int index = getSelectedIndex();
		return handleList.get(index);
	}

	/**
	 * @return the overviewPanel
	 */
	public JPanel getOverviewPanel() {
		return overPane;
	}

	/**
	 * @return the filenamesAndPath
	 */
	@Override
	public Vector<String> getAllFilenamesAndPath() {
		return filenamesAndPath;
	}

	private Vector<ImageIcon> fillIconVector() {
		System.out.println("Loading Custom Lockhandles!");
		handleList.add(nada);
		handleList.add(origlock);
		handleList.add(androidlock);
		handleList.add(entelock);
		final File dir = new File(CUSTOM_DIR);
		if (!dir.exists())
			dir.mkdirs();
		if (dir.exists() && dir.isDirectory()) {
			final File[] pngs = dir.listFiles(new FilenameFilter() {

				public boolean accept(final File dir, final String name) {
					return name.toLowerCase().endsWith(".png") && !name.toLowerCase().startsWith("over");
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
					renderer.setText("No Lockhandle");
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

	@Override
	public String getProviderName() {
		return PROVIDER_NAME;
	}

	@Override
	public boolean isActiv() {
		final ImageIcon icon = (ImageIcon) getSelectedItem();
		if (!icon.equals(nada))
			return true;
		return false;
	}

}
