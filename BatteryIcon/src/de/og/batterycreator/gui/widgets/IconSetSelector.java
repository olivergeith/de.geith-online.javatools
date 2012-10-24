package de.og.batterycreator.gui.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
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
import de.og.batterycreator.creators.IconProviderInterface;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.main.IconCreatorFrame;

public class IconSetSelector extends JComboBox<ImageIcon> implements IconProviderInterface {
	private static final long serialVersionUID = -2767025548199058416L;

	protected OverviewPanel overPane = new OverviewPanel();

	protected final ImageIcon nada = IconStore.nothingIcon;
	protected File[] setDirs;
	protected final Vector<String> filenamesAndPath = new Vector<String>();

	private final String rootDir;
	private final String name;

	public IconSetSelector(final String name, final String rootDir) {
		super();
		this.rootDir = rootDir;
		this.name = name;
		initUI();
	}

	@Override
	public String getProviderName() {
		return name;
	}

	/**
	 * Create Overviews
	 * 
	 * @param iconMap
	 * @param name
	 * @return
	 */
	protected ImageIcon createOverview(final Vector<ImageIcon> iconMap, final String name) {
		if (iconMap != null && iconMap.size() > 0) {
			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();
			final int w = iw * 10 + 11;
			final int offsetOben = 50;
			final int offsetUnten = 35;
			final int volleZehner = iconMap.size() / 10 + 1;

			final int h = ih * volleZehner + (volleZehner + 1) + offsetOben + offsetUnten;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, w, h);
			g2d.setColor(Color.white);
			g2d.drawString(name, 2, 20);
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
			g2d.setColor(Color.gray);
			g2d.drawString("Created with ''The Battery Icon Creator'' V" + IconCreatorFrame.VERSION_NR + " by OlliG", 2, 32);
			g2d.drawString("http://forum.xda-developers.com/showthread.php?t=1918500", 2, h - offsetUnten + 20);
			g2d.setColor(Color.white);
			g2d.fillRect(0, 40, w, 2);
			g2d.fillRect(0, h - offsetUnten, w, 2);
			g2d.fillRect(0, h - 2, w, 2);

			// Lopp über alle Bilder
			for (int i = 0; i < iconMap.size(); i++) {
				final int z = i / 10;
				final int e = i % 10;
				final int index = z * 10 + e;
				final ImageIcon img = iconMap.elementAt(index);
				g2d.drawImage(img.getImage(), 1 + e * (iw + 1), 1 + z * (ih + 1) + offsetOben, null);
			}
			final String filename = rootDir + name + File.separator + "overview_" + name + ".png";
			final File overFile = new File(filename);
			if (!overFile.exists()) {
				System.out.println("Overview does not exist...creating one!");
				System.out.println("Creating : " + filename);
				StaticImageHelper.writePNG(over, overFile);
			}
			return new ImageIcon(over);
		}
		return null;
	}

	protected File[] findPNGs(final File dir) {
		final File[] pngs = dir.listFiles(new FilenameFilter() {

			public boolean accept(final File dir, final String name) {
				return name.toLowerCase().endsWith(".png") && !name.toLowerCase().startsWith("over");
			}
		});
		return pngs;
	}

	/**
	 * @return the overviewPanel
	 */
	public JPanel getOverviewPanel() {
		return overPane;
	}

	protected File[] findCustomDirs(final File dir) {
		if (dir.isDirectory()) {
			final File[] subdirs = dir.listFiles(new FileFilter() {

				@Override
				public boolean accept(final File file) {

					return file.isDirectory() && findPNGs(file).length > 0;
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

	protected void initUI() {
		addItem(nada);

		setRenderer(new MyCellRenderer());
		setToolTipText("Choose your " + name + " Iconset");
		System.out.println("Loading Custom " + name + " Icon Sets!");
		addSetsFromFilesystem();
		overPane.add(this, BorderLayout.NORTH);
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final ImageIcon icon = (ImageIcon) getSelectedItem();
				filenamesAndPath.removeAllElements();
				if (!icon.equals(nada)) {
					final int index = getSelectedIndex();
					final File setDir = setDirs[index - 1];
					final File[] pngs = findPNGs(setDir);
					final Vector<ImageIcon> iconMap = new Vector<ImageIcon>();
					for (final File png : pngs) {
						iconMap.add(new ImageIcon(png.getPath()));
						filenamesAndPath.add(png.getPath());
						// System.out.println("adding: " + png.getPath());
					}
					final ImageIcon over = createOverview(iconMap, setDir.getName());
					overPane.setOverview(over);
					overPane.setText("");
					// System.out.println("Selected Icon:" + icon);
				} else {
					overPane.setOverview(icon);
					overPane.setText("   Choose " + name + "-Set from Dropdownbox");
				}
			}
		});
		if (getItemCount() > 0)
			setSelectedIndex(0);
	}

	/**
	 * 
	 */
	protected void addSetsFromFilesystem() {
		final File dir = new File(rootDir);
		if (!dir.exists())
			dir.mkdirs();
		// find subdirs with icon sets
		setDirs = findCustomDirs(dir);
		if (setDirs != null) {
			for (final File setDir : setDirs) {
				final File[] pngs = findPNGs(setDir);
				if (pngs.length > 0) {
					final ImageIcon icon = new ImageIcon(pngs[0].getPath());
					final BufferedImage bimg = StaticImageHelper.resize(StaticImageHelper.convertImageIcon(icon), 32);
					addItem(new ImageIcon(bimg));
				}
			}
		}
	}

	/**
	 * Renderer for WifiCreator-Combo
	 */
	protected class MyCellRenderer implements ListCellRenderer<ImageIcon> {
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
				if (index > 0)
					renderer.setText(setDirs[index - 1].getName());
				if (icon.equals(nada)) {
					renderer.setText("No " + name + " Icons");
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
		if (combo.getItemCount() > 0)
			combo.setSelectedIndex(0);
		f.add(combo, BorderLayout.NORTH);
		f.add(combo.getOverviewPanel(), BorderLayout.CENTER);

		f.setVisible(true);
	}

	@Override
	public boolean isActiv() {
		final ImageIcon icon = (ImageIcon) getSelectedItem();
		if (!icon.equals(nada))
			return true;
		return false;
	}

}
