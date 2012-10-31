package de.og.batterycreator.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import og.basics.gui.icon.CommonIconProvider;
import de.og.batterycreator.creators.batt.AbstractIconCreator;
import de.og.batterycreator.creators.signal.AbstractSignalCreator;
import de.og.batterycreator.creators.signal.NoSignalIcons;
import de.og.batterycreator.creators.wifi.AbstractWifiCreator;
import de.og.batterycreator.creators.wifi.NoWifiIcons;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.BattCreatorSelector;
import de.og.batterycreator.gui.widgets.IconSetDeployer;
import de.og.batterycreator.gui.widgets.IconSetSelector;
import de.og.batterycreator.gui.widgets.OverviewPanel;
import de.og.batterycreator.gui.widgets.RawIconSetSelector;
import de.og.batterycreator.gui.widgets.SignalCreatorSelector;
import de.og.batterycreator.gui.widgets.WifiCreatorSelector;
import de.og.batterycreator.gui.widgets.lockhandleselector.LockHandleSelector;
import de.og.batterycreator.gui.widgets.transparent.NotificationAreaBG;
import de.og.batterycreator.zipcreator.ZipElementCollection;
import de.og.batterycreator.zipcreator.ZipMaker;

public class IconCreatingPanelNew extends JPanel implements ActionListener {
	private static final long serialVersionUID = -2956273745014471932L;

	private final JTabbedPane tabPane = new JTabbedPane();
	private final ConfigPanel configPane = new ConfigPanel();

	private final BattCreatorSelector battCreatorBox = new BattCreatorSelector(configPane);
	private final JList<String> battIconList = battCreatorBox.getBattIconList();
	private final OverviewPanel battOverviewPanel = battCreatorBox.getBattOverviewPanel();
	private AbstractIconCreator activBattCreator = null;

	private final WifiCreatorSelector wifiCreatorBox = new WifiCreatorSelector(configPane);;
	private final OverviewPanel wifiOverviewPanel = wifiCreatorBox.getWifiOverviewPanel();
	private AbstractWifiCreator activWifiCreator = null;

	private final SignalCreatorSelector signalCreatorBox = new SignalCreatorSelector(configPane);;
	private final OverviewPanel signalOverviewPanel = signalCreatorBox.getSignalOverviewPanel();
	private AbstractSignalCreator activSignalCreator = null;

	private final LockHandleSelector lockHandleSelector = new LockHandleSelector(configPane);
	private final IconSetSelector toggleBox = new IconSetSelector("Toggle", "./custom/toggles/");
	private final IconSetSelector weatherBox = new IconSetSelector("Weather", "./custom/weather/");
	private final NotificationAreaBG notificationBG = new NotificationAreaBG(configPane);
	private final RawIconSetSelector systemUIBox = new RawIconSetSelector("SystemUIMod", "./custom/systemui-mods/");
	private final RawIconSetSelector frameworkresBox = new RawIconSetSelector("FrameworkResMod", "./custom/frameworkres-mods/");

	// Treadstuff
	private final JProgressBar progressBar = new JProgressBar();
	private Thread t = null;
	private boolean isrunning = false;
	// private boolean stopnow = false;
	private final int maxsteps = 22;
	private int step = 0;

	private final JFrame parentFrame;

	public IconCreatingPanelNew(final JFrame parentFrame) {
		this.parentFrame = parentFrame;
		initUI();
	}

	private void initUI() {
		setLayout(new BorderLayout());
		// Prograssbar int

		progressBar.setIndeterminate(false);
		progressBar.setMinimum(0);
		progressBar.setMaximum(maxsteps);
		progressBar.setStringPainted(true);
		resetProgressBar();

		// Icon Liste
		final JScrollPane scroller = new JScrollPane();
		scroller.add(battIconList);
		scroller.getViewport().setView(battIconList);
		scroller.setPreferredSize(new Dimension(800, 600));

		// Tabbed Pane
		final JTabbedPane battTabPane = new JTabbedPane();
		battTabPane.setTabPlacement(JTabbedPane.LEFT);
		battTabPane.addTab("", IconStore.overIcon, battOverviewPanel, "Get an Overview of your icons");
		battTabPane.addTab("", IconStore.listIcon, scroller, "Get an Overview of your icons");
		final JTabbedPane additionalIconsTabPane = new JTabbedPane();
		additionalIconsTabPane.addTab("Toggle", IconStore.toggleIcon, toggleBox.getOverviewPanel(), "Get an Overview of your toggles");
		additionalIconsTabPane.addTab("Weather", IconStore.weatherIcon, weatherBox.getOverviewPanel(), "Get an Overview of your weather icons");
		additionalIconsTabPane.addTab("Lockring", IconStore.lockringIcon, lockHandleSelector.getOverviewPanel(), "See your choosen Lockring!");
		// additionalIconsTabPane.addTab("NotificationPanel",
		// IconStore.notificationIcon, notificationBG,
		// "Transparent Notification Panel");
		additionalIconsTabPane.addTab("SystemUI Mods", IconStore.androidredIcon, systemUIBox.getOverviewPanel(), "Get an Overview of your icons");
		additionalIconsTabPane.addTab("FrameWorkRes Mods", IconStore.androidblueIcon, frameworkresBox.getOverviewPanel(), "Get an Overview of your icons");

		// Tabbed Pane
		tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabPane.addTab("Battery", IconStore.batteryIcon, battTabPane, "Get an Overview of your icons");
		tabPane.addTab("Wifi", IconStore.wifiIcon, wifiOverviewPanel, "Get an Overview of your icons");
		tabPane.addTab("Signal", IconStore.signalIcon, signalOverviewPanel, "Get an Overview of your icons");
		tabPane.addTab("NotificationPanel", IconStore.notificationIcon, notificationBG, "Transparent Notification Panel");
		tabPane.addTab("Additional Icons", IconStore.additionalIcon, additionalIconsTabPane, "Add additional icons to your zip");

		// Actionlistener für die dropdownboxen, damit die Tabs aktiv werden
		wifiCreatorBox.addActionListener(this);
		signalCreatorBox.addActionListener(this);

		// Panel zusammensetzen
		add(tabPane, BorderLayout.CENTER);
		add(configPane, BorderLayout.WEST);
		add(progressBar, BorderLayout.SOUTH);

		// Comobox abfragen
		activBattCreator = (AbstractIconCreator) battCreatorBox.getSelectedItem();
		activWifiCreator = (AbstractWifiCreator) wifiCreatorBox.getSelectedItem();
		activSignalCreator = (AbstractSignalCreator) signalCreatorBox.getSelectedItem();

		makeButtonBar();
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource().equals(wifiCreatorBox) && wifiCreatorBox.getSelectedIndex() != 0) {
			tabPane.setSelectedIndex(1);
		}
		if (e.getSource().equals(signalCreatorBox) && signalCreatorBox.getSelectedIndex() != 0) {
			tabPane.setSelectedIndex(2);
		}
	}

	/**
	 * Creating buttonbar
	 */
	private void makeButtonBar() {
		final LoadSettingsAktion loadAktion = new LoadSettingsAktion("Load Settings for selected Creator", CommonIconProvider.getInstance().BUTTON_ICON_OPEN);
		final SaveSettingsAktion saveAktion = new SaveSettingsAktion("Save Settings for selected Creator", CommonIconProvider.getInstance().BUTTON_ICON_SAVE);
		final CreateAktion createAktion = new CreateAktion("Create Icons", CommonIconProvider.getInstance().BUTTON_ICON_START);
		final ZipAktion zipAktion = new ZipAktion("Create flashable Zip", IconStore.zipIcon);
		final JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.add(loadAktion);
		toolBar.add(saveAktion);
		toolBar.addSeparator();
		toolBar.add(battCreatorBox);
		toolBar.addSeparator();
		toolBar.add(wifiCreatorBox);
		toolBar.addSeparator();
		toolBar.add(signalCreatorBox);
		toolBar.addSeparator();
		toolBar.add(createAktion);
		toolBar.add(zipAktion);
		add(toolBar, BorderLayout.NORTH);
	}

	/**
	 * Zip the flashable Zip!
	 */
	private void doZip() {
		step = 0;
		// first create everything again, to fill the deploy area with latest
		// settings
		updateProgressBar(step++, "Creating and deploying Icons to ./pngs");
		create();

		final ZipMaker zipper = new ZipMaker();
		final Vector<String> files2add2SystemUI = new Vector<String>();
		final Vector<String> files2add2Framework = new Vector<String>();
		// adding Battery Icons
		updateProgressBar(step++, "Adding Battery Icons (if configured)");
		activBattCreator = (AbstractIconCreator) battCreatorBox.getSelectedItem();
		if (activBattCreator != null) {
			files2add2SystemUI.addAll(activBattCreator.getAllFilenamesAndPath());
		}
		// Add Wifi Icons
		updateProgressBar(step++, "Adding Wifi Icons (if configured)");
		activWifiCreator = (AbstractWifiCreator) wifiCreatorBox.getSelectedItem();
		if (activWifiCreator != null && !activWifiCreator.toString().equals(NoWifiIcons.name)) {
			files2add2SystemUI.addAll(activWifiCreator.getAllFilenamesAndPath());
		}

		// Add Signal Icons
		updateProgressBar(step++, "Adding Signal Icons (if configured)");
		activSignalCreator = (AbstractSignalCreator) signalCreatorBox.getSelectedItem();
		if (activSignalCreator != null && !activSignalCreator.toString().equals(NoSignalIcons.name)) {
			files2add2SystemUI.addAll(activSignalCreator.getAllFilenamesAndPath());
		}

		// Add Toggles
		updateProgressBar(step++, "Adding Toggle Icons (if configured)");
		files2add2SystemUI.addAll(toggleBox.getAllFilenamesAndPath());
		// Add Weather
		updateProgressBar(step++, "Adding Weather Icons (if configured)");
		files2add2Framework.addAll(weatherBox.getAllFilenamesAndPath());
		// Lockhandle
		updateProgressBar(step++, "Adding Lock Icons (if configured)");
		files2add2Framework.addAll(lockHandleSelector.getAllFilenamesAndPath());
		// notification BG
		updateProgressBar(step++, "Adding Notification Background (if configured)");
		files2add2SystemUI.addAll(notificationBG.getAllFilenamesAndPath());

		// SystemUI
		updateProgressBar(step++, "Adding SystemUI Mods (if configured)");
		files2add2SystemUI.addAll(systemUIBox.getAllFilenamesAndPath());
		// FrameWorkRES
		updateProgressBar(step++, "Adding SystemUI Mods (if configured)");
		files2add2Framework.addAll(frameworkresBox.getAllFilenamesAndPath());

		// ZipElementCollection anlegen und alle Zipelemente einfügen
		updateProgressBar(step++, "Creating ZipCollection");
		final ZipElementCollection zipCollection = new ZipElementCollection();
		zipCollection.addElements(files2add2SystemUI, activBattCreator.getSettings().getFolderSystemUIInZip());
		zipCollection.addElements(files2add2Framework, activBattCreator.getSettings().getFolderFrameworkInZip());
		// now the actual zipping...
		try {
			updateProgressBar(step++, "Choose ZipFilename....");
			final boolean saved = zipper.addFilesToArchive(zipCollection.getZipelEments(), activBattCreator.getCreatorName());
			// all ok ? Then Messagebox
			if (saved == true) {
				updateProgressBar(step++, "Done Successfully!");
				JOptionPane.showMessageDialog(IconCreatingPanelNew.this, "Zip was created successfully", "Zip creating", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (final Exception e) {
			// Error during zip...
			updateProgressBar(step++, "Done With Error!");
			JOptionPane.showMessageDialog(IconCreatingPanelNew.this,
					"ERROR: Zip was not created successfully!!!\nDid you create icons already ? (Play-Button?!)", "Zip creating ERROR",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		resetProgressBar();
	}

	/**
	 * Creates the desired Icons ;-)
	 */
	private void create() {
		// Creating Battery Icons
		updateProgressBar(step++, "Creating Battery Icons (if configured)");
		activBattCreator = (AbstractIconCreator) battCreatorBox.getSelectedItem();
		activBattCreator.setSettings(configPane.getSettings());
		activBattCreator.createAllImages();
		battIconList.removeAll();
		battIconList.setListData(activBattCreator.getFilenames());
		battIconList.repaint();
		battOverviewPanel.setOverview(activBattCreator.getOverviewIcon());

		// Creating Wifi Icons
		updateProgressBar(step++, "Creating Wifi Icons (if configured)");
		activWifiCreator = (AbstractWifiCreator) wifiCreatorBox.getSelectedItem();
		if (activWifiCreator != null && !activWifiCreator.toString().equals(NoWifiIcons.name)) {
			activWifiCreator.setSettings(configPane.getSettings());
			activWifiCreator.createAllImages();
			wifiOverviewPanel.setOverview(activWifiCreator.getOverviewIcon());
		}

		// Creating Signal Icons
		updateProgressBar(step++, "Creating Signal Icons (if configured)");
		activSignalCreator = (AbstractSignalCreator) signalCreatorBox.getSelectedItem();
		if (activSignalCreator != null && !activSignalCreator.toString().equals(NoSignalIcons.name)) {
			activSignalCreator.setSettings(configPane.getSettings());
			activSignalCreator.createAllImages();
			signalOverviewPanel.setOverview(activSignalCreator.getOverviewIcon());
		}

		// creating notification
		updateProgressBar(step++, "Deploying Notification Background (if configured)");
		notificationBG.createAllImages(configPane.getSettings().getNotificationHeight());
		// creating lockHandle
		updateProgressBar(step++, "Deploying Lockring (if configured)");
		lockHandleSelector.createAllImages(configPane.getSettings().getLockHandleSize());
		// creating toggles
		updateProgressBar(step++, "Deploying Toggles (if configured)");
		toggleBox.createAllImages(configPane.getSettings().getToggleSize());
		// creating weather
		updateProgressBar(step++, "Deploying Weather Icons (if configured)");
		weatherBox.createAllImages(configPane.getSettings().getWeatherSize());
		updateProgressBar(step++, "Deploying Weather Icons ...done");
		// Systemui Mods
		updateProgressBar(step++, "Deploying Weather Icons (if configured)");
		systemUIBox.createAllImages(IconSetDeployer.NO_RESIZING);
		// frame Mods
		updateProgressBar(step++, "Deploying Weather Icons (if configured)");
		frameworkresBox.createAllImages(IconSetDeployer.NO_RESIZING);
	}

	/**
	 * Create Action for creating off all icons
	 * 
	 */
	private class CreateAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public CreateAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			create();
		}
	}

	/**
	 * Zipping Action
	 * 
	 */
	private class ZipAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public ZipAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			startZipThread();
			// doZip();
		}
	}

	/**
	 * Load Settings Action
	 * 
	 */
	private class LoadSettingsAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public LoadSettingsAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			if (activBattCreator != null) {
				activBattCreator.loadSettings();
				configPane.setSettings(activBattCreator.getSettings());
			}
		}
	}

	/**
	 * Save Settings Action
	 * 
	 */
	private class SaveSettingsAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public SaveSettingsAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			if (activBattCreator != null) {
				activBattCreator.setSettings(configPane.getSettings());
				activBattCreator.persistSettings();
			}
		}
	}

	/**
	 * Startet den Thread
	 * 
	 * @param startDir
	 */
	private void startZipThread() {
		if (t != null)
			stopThread();
		if (t == null) {
			t = new Thread(new Runnable() {
				public void run() {
					parentFrame.setEnabled(false);
					// stopnow = false;
					isrunning = true;
					doZip();
					parentFrame.toFront();
					parentFrame.setEnabled(true);
					parentFrame.toFront();
					isrunning = false;
				}
			});

			t.start();
		}
	}

	public synchronized void stopThread() {
		if (t != null) {
			// stopnow = true;
			t = null;
		}
	}

	/**
	 * @return true, wenn Thread noch läuft
	 */
	public synchronized boolean isTreadRunning() {
		return isrunning;
	}

	private void updateProgressBar(final int value, final String text) {
		System.out.println("Progress: " + value + " " + text);
		progressBar.setValue(value);
		progressBar.setString(text);
	}

	private void resetProgressBar() {
		progressBar.setValue(0);
		progressBar.setString("Create your Icons");
	}

}
