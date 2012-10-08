package de.og.batterycreator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import og.basics.gui.about.UniversalAboutDialog;
import og.basics.gui.about.VersionDetails;
import og.basics.gui.icon.CommonIconProvider;
import og.basics.gui.tracepanel.DefaultTextFileSaveHandler;
import og.basics.gui.tracepanel.TracePanel;
import de.og.batterycreator.creators.Arc2Creator;
import de.og.batterycreator.creators.ArcCreator;
import de.og.batterycreator.creators.ArcDecimalCreator;
import de.og.batterycreator.creators.ArcQuaterCreator2;
import de.og.batterycreator.creators.ArcSunCreator;
import de.og.batterycreator.creators.BatterySymbolCreator;
import de.og.batterycreator.creators.BatteryVerticalSymbolCreator;
import de.og.batterycreator.creators.BinaryBarsCreator;
import de.og.batterycreator.creators.BinarySquaresCreator;
import de.og.batterycreator.creators.BrickBattCreator;
import de.og.batterycreator.creators.BrickBattNoGapCreator;
import de.og.batterycreator.creators.BrickDecimal2Creator;
import de.og.batterycreator.creators.BrickDecimalCreator;
import de.og.batterycreator.creators.ClockCreator;
import de.og.batterycreator.creators.ClockPointerCreator;
import de.og.batterycreator.creators.DefaultCreator;
import de.og.batterycreator.zipcreator.ZipMaker;

public class IconCreatorFrame extends JFrame {

	private static final String APP_NAME = "Battery Icon Creator";
	public static final String VERSION_NR = "6.0";

	private static final long serialVersionUID = 1L;
	private static final ImageIcon logoIcon = new ImageIcon(ConfigPanel.class.getResource("logo.png"));
	private final ImageIcon errorIcon = new ImageIcon(this.getClass().getResource("error.png"));
	private final ImageIcon cfgIcon = new ImageIcon(this.getClass().getResource("cfg.png"));
	private final ImageIcon zipIcon = new ImageIcon(this.getClass().getResource("zip.png"));

	private static IconCreatorFrame frame;
	private final TracePanel tracer = new TracePanel(new DefaultTextFileSaveHandler(".", "Logging", ".txt", "Tracefile"));
	private final JTabbedPane tabPane = new JTabbedPane();

	private final Vector<DefaultCreator> creators = new Vector<DefaultCreator>();

	private final JMenuBar menuBar = new JMenuBar();
	private final JToolBar toolBar = new JToolBar();
	private BeendenAktion beendenAktion;
	private AboutAktion aboutAktion;
	private LoadAktion loadAktion;
	private SaveAktion saveAktion;
	private CreateAktion createAktion;
	private ZipAktion zipAktion;
	private final JList<String> list = new JList<String>();
	private JComboBox<DefaultCreator> creatorBox;
	private final ConfigPanel configPane = new ConfigPanel();
	private final JPanel mainPanel = new JPanel();

	private final JPanel overviewPanel = new JPanel();
	private final JLabel overviewLabel = new JLabel();

	private DefaultCreator activCreator = null;

	public static void main(final String[] args) {
		frame = new IconCreatorFrame();

	}

	/**
	 * Zip the flashable Zip!
	 */
	private void doZip() {
		tracer.clear();
		tabPane.setSelectedIndex(2);
		if (activCreator == null) {
			tracer.appendErrorText("No icons to zip ...please create some (play-button)");
			return;
		}
		final ZipMaker zipper = new ZipMaker(tracer);
		final Vector<String> files2add = new Vector<String>();

		for (int i = 0; i <= 100; i++) {
			files2add.add(activCreator.getFilenameAndPath(i, false));
			files2add.add(activCreator.getFilenameAndPath(i, true));
		}
		files2add.add(activCreator.getFilenameAndPathFull(false));
		files2add.add(activCreator.getFilenameAndPathFull(true));

		try {
			zipper.addFilesToArchive(files2add, activCreator.getSettings().getFolderWithinZip(), activCreator.getName());
		} catch (final Exception e) {
			tracer.appendErrorText("There was a Problem creating the Zip: " + e.getMessage());
			tracer.appendErrorText("There was a Problem creating the Zip: " + e.getStackTrace());
			e.printStackTrace();
		}
		tracer.appendSuccessText("Everything seems ok....:-)");
	}

	/**
	 * Creates the desired Icons ;-)
	 */
	private void create() {
		activCreator = (DefaultCreator) creatorBox.getSelectedItem();
		activCreator.setSettings(configPane.getSettings());
		activCreator.createAllImages();

		list.removeAll();
		list.setListData(activCreator.getFilenames());
		list.repaint();
		overviewLabel.setIcon(activCreator.getOverviewIcon());
		pack();
	}

	public IconCreatorFrame() {
		super();
		tracer.appendInfoText("Starting " + APP_NAME + " ----- Version " + VERSION_NR);
		setTitle(APP_NAME + " ----- Version " + VERSION_NR);
		setIconImage(logoIcon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 100, 300, 800);

		creators.add(new ArcCreator());
		creators.add(new Arc2Creator());
		creators.add(new ArcSunCreator());
		creators.add(new ArcQuaterCreator2());
		creators.add(new ArcDecimalCreator());
		creators.add(new BrickBattCreator());
		creators.add(new BrickBattNoGapCreator());
		creators.add(new BrickDecimalCreator());
		creators.add(new BrickDecimal2Creator());
		creators.add(new BinaryBarsCreator());
		creators.add(new BinarySquaresCreator());
		creators.add(new BatterySymbolCreator());
		creators.add(new BatteryVerticalSymbolCreator());
		creators.add(new ClockCreator());
		creators.add(new ClockPointerCreator());

		initUI();
		setVisible(true);

		pack();
	}

	/**
	 * Gui Init
	 */
	private void initUI() {
		getContentPane().setLayout(new BorderLayout());

		overviewPanel.setLayout(new BorderLayout());
		mainPanel.setLayout(new BorderLayout());
		toolBar.setFloatable(false);
		setJMenuBar(menuBar);
		mainPanel.add(toolBar, BorderLayout.NORTH);

		// Icon Liste
		list.setCellRenderer(new IconListCellRenderer());
		list.setBackground(Color.black);
		final JScrollPane scroller = new JScrollPane();
		scroller.add(list);
		scroller.getViewport().setView(list);
		scroller.setPreferredSize(new Dimension(400, 600));

		final JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(scroller, BorderLayout.CENTER);
		// centerPanel.add(new JLabel(logoIcon), BorderLayout.SOUTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(configPane, BorderLayout.WEST);

		// Comobox mit creatoren anzeigen
		creatorBox = new JComboBox<DefaultCreator>(creators);
		creatorBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final DefaultCreator cre = (DefaultCreator) creatorBox.getSelectedItem();

				if (cre != null) {
					configPane.setSettings(cre.getSettings());
				}
			}
		});
		creatorBox.setSelectedIndex(0);
		creatorBox.setToolTipText("Choose your IconCreator...then press play-button");
		creatorBox.setRenderer(new CreatorListCellRenderer());
		creatorBox.setMaximumRowCount(15);
		// Menü und Buttonbar erzeugen
		createAktionen();
		makeMenuAndButtonBar();
		getContentPane().add(createTabbedPane());
		overviewPanel.setBackground(Color.black);
		overviewPanel.add(overviewLabel, BorderLayout.CENTER);
	}

	private JTabbedPane createTabbedPane() {
		// Tabpane zusammenbasteln
		tabPane.addTab("IconCreating", cfgIcon, mainPanel, "Create your icons here...");
		tabPane.addTab("Overview-Image", cfgIcon, overviewPanel, "Get an Overview of your icons");
		tabPane.addTab("TraceLog", errorIcon, tracer, "TraceLog");
		return tabPane;
	}

	/**
	 * Aktionen erzeugen und in einem Vector mit Aktionen ablegen
	 */
	private void createAktionen() {
		beendenAktion = new BeendenAktion("Beenden", CommonIconProvider.getInstance().BUTTON_ICON_CANCEL);
		aboutAktion = new AboutAktion("About", CommonIconProvider.getInstance().BUTTON_ICON_INFO);
		loadAktion = new LoadAktion("Load Settings for selected Creator", CommonIconProvider.getInstance().BUTTON_ICON_OPEN);
		saveAktion = new SaveAktion("Save Settings for selected Creator", CommonIconProvider.getInstance().BUTTON_ICON_SAVE);
		createAktion = new CreateAktion("Create Icons", CommonIconProvider.getInstance().BUTTON_ICON_START);
		zipAktion = new ZipAktion("Create flashable Zip", zipIcon);
	}

	/**
	 * Menüleiste Zusammensetzen
	 */
	private void makeMenuAndButtonBar() {
		final JMenu dateiMenu = new JMenu("Datei");
		menuBar.add(dateiMenu);
		dateiMenu.add(createAktion);
		dateiMenu.add(zipAktion);
		dateiMenu.add(loadAktion);
		dateiMenu.add(saveAktion);
		dateiMenu.addSeparator();
		dateiMenu.add(beendenAktion);
		dateiMenu.add(aboutAktion);
		toolBar.add(beendenAktion);
		toolBar.add(loadAktion);
		toolBar.add(saveAktion);
		toolBar.addSeparator();
		toolBar.add(creatorBox);
		toolBar.add(createAktion);
		toolBar.add(zipAktion);
		toolBar.addSeparator();
		toolBar.add(aboutAktion);
	}

	/**
	 * Renderer für IconList
	 */
	private class IconListCellRenderer implements ListCellRenderer<Object> {
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {
			String iconName = null;

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof String) {
				iconName = (String) value;
				renderer.setBorder(new EmptyBorder(1, 1, 1, 1));
				renderer.setText(iconName);
				renderer.setBackground(Color.black);
				renderer.setForeground(Color.white);
				renderer.setBorder(new EmptyBorder(1, 1, 1, 1));
				if (activCreator != null)
					renderer.setIcon(activCreator.getIcons().elementAt(index));
			}
			return renderer;
		}
	}

	/**
	 * Renderer für IconList
	 */
	private class CreatorListCellRenderer implements ListCellRenderer<Object> {
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof DefaultCreator) {
				renderer.setBackground(Color.darkGray.darker().darker());
				renderer.setForeground(Color.white);
				final DefaultCreator creator = creatorBox.getItemAt(index);
				if (creator != null && renderer.getIcon() == null) {
					final ImageIcon icon = creator.createImage(45, false);
					renderer.setIcon(icon);
				}
			}
			return renderer;
		}
	}

	private class BeendenAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public BeendenAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			System.exit(0);

		}
	}

	private class CreateAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public CreateAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			create();
		}
	}

	private class ZipAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ZipAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			final int n = JOptionPane.showConfirmDialog(IconCreatorFrame.this, "Would you like to create a flashable zip now ?", "Create Flashable-Zip...",
					JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				doZip();
			}
		}
	}

	private class LoadAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public LoadAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			if (activCreator != null) {
				final int n = JOptionPane.showConfirmDialog(IconCreatorFrame.this, "Would you like to load the last saved settings?", "Load Settings...",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					activCreator.loadSettings();
					configPane.setSettings(activCreator.getSettings());
					tracer.appendInfoText("Loading settings...");
				}
			}
		}
	}

	private class SaveAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public SaveAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			if (activCreator != null) {
				final int n = JOptionPane.showConfirmDialog(IconCreatorFrame.this, "Would you like to save the current settings?", "Save Settings...",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					activCreator.setSettings(configPane.getSettings());
					activCreator.persistSettings();
					tracer.appendInfoText("Saving settings...");
				}
			}
		}
	}

	private class AboutAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public AboutAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			final VersionDetails details = new VersionDetails();
			details.setApplicationname(APP_NAME);
			details.setCompany("Geith-Online.de");
			details.setVersion(VERSION_NR);
			details.setDate("30.09.2012");
			details.setLogo(logoIcon);
			details.setCopyright("by Oliver Geith");
			details.setDescription("This application can create icons for batteries...");
			final UniversalAboutDialog aboutDialog = new UniversalAboutDialog(frame, details);
			aboutDialog.setVisible(true);

		}
	}
}
