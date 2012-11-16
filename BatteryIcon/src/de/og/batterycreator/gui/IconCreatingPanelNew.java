package de.og.batterycreator.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;

import og.basics.gui.LToolBar;
import og.basics.gui.icon.CommonIconProvider;
import de.og.batterycreator.cfg.gui.RomSettingsPanel;
import de.og.batterycreator.creators.batt.AbstractIconCreator;
import de.og.batterycreator.creators.signal.AbstractSignalCreator;
import de.og.batterycreator.creators.signal.NoSignalIcons;
import de.og.batterycreator.creators.wifi.AbstractWifiCreator;
import de.og.batterycreator.creators.wifi.NoWifiIcons;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.panels.BatteryPanel;
import de.og.batterycreator.gui.panels.LockHandlePanel;
import de.og.batterycreator.gui.panels.SignalPanel;
import de.og.batterycreator.gui.panels.WifiPanel;
import de.og.batterycreator.gui.panels.fileset.RecurseFileSetSelector;
import de.og.batterycreator.gui.panels.iconset.IconSetDeployer;
import de.og.batterycreator.gui.panels.iconset.IconSetSelector;
import de.og.batterycreator.gui.panels.iconset.RawIconSetSelector;
import de.og.batterycreator.gui.panels.notification.NotificationAreaBG;
import de.og.batterycreator.gui.panels.recurseiconset.RecurseIconSetSelector;
import de.og.batterycreator.zipcreator.ZipElementCollection;
import de.og.batterycreator.zipcreator.ZipMaker;

public class IconCreatingPanelNew extends JPanel {
	private static final long serialVersionUID = -2956273745014471932L;

	private final JButton zipButton = new JButton(IconStore.zipIcon);
	private final JButton createButton = new JButton(CommonIconProvider.getInstance().BUTTON_ICON_START);
	private final JToggleButton advancedToggle = new JToggleButton(IconStore.moreIcon, false);
	private final JTabbedPane tabPane = new JTabbedPane();
	private final LToolBar toolBar = new LToolBar();
	private final RomSettingsPanel romSettingsPanel = new RomSettingsPanel();
	private final BatteryPanel battPanel = new BatteryPanel(romSettingsPanel.getSettings());
	private final WifiPanel wifiPanel = new WifiPanel(romSettingsPanel.getSettings());
	private final SignalPanel signalPanel = new SignalPanel(romSettingsPanel.getSettings());

	private final LockHandlePanel lockHandleSelector = new LockHandlePanel();
	private final IconSetSelector toggleBox = new IconSetSelector("Toggle", "./custom/toggles/");
	private final IconSetSelector weatherBox = new IconSetSelector("Weather", "./custom/weather/");
	private final NotificationAreaBG notificationBG = new NotificationAreaBG();
	private final RawIconSetSelector systemUIBox = new RawIconSetSelector("SystemUIMod", "./custom/systemui-mods/");
	private final RawIconSetSelector frameworkresBox = new RawIconSetSelector("FrameworkResMod", "./custom/frameworkres-mods/");

	private final RecurseFileSetSelector filesetBox = new RecurseFileSetSelector();
	private final RecurseIconSetSelector iconsetBox = new RecurseIconSetSelector();

	// Treadstuff
	private final JProgressBar progressBar = new JProgressBar();
	private Thread t = null;
	private boolean isrunning = false;
	// private boolean stopnow = false;
	private final int maxsteps = 13;
	private int step = 0;

	private final JFrame parentFrame;

	private JTabbedPane advancedTabPane;

	public IconCreatingPanelNew(final JFrame parentFrame) {
		this.parentFrame = parentFrame;
		initUI();
	}

	private void initUI() {
		setLayout(new BorderLayout());
		zipButton.setToolTipText("Create flashble Zip!");
		zipButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				startZipThread();
			}
		});
		createButton.setToolTipText("Create/Refresh icons");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				create();
			}
		});

		// Tooglebutton
		advancedToggle.setSelected(false);
		advancedToggle.setToolTipText("Show Advanced Features...Noobs stay away!!!!");
		advancedToggle.addActionListener(new ActionListener() {
			boolean firstTime = true;

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (firstTime == true) {
					JOptionPane
							.showMessageDialog(
									IconCreatingPanelNew.this,
									"You are entering dangerous terrain!!!\n\nNoobs keep away from those advanced features!!!\n\nYou need to know what you are doing!!!\n- You know how your Rom looks from the inside?\n- You know how your apk's look from the inside ?\n No? then stay away from this feature!",
									"Attention", JOptionPane.WARNING_MESSAGE);
					firstTime = false;
				}
				validatePanel(advancedToggle.isSelected());
			}
		});

		// Prograssbar int
		progressBar.setIndeterminate(false);
		progressBar.setMinimum(0);
		progressBar.setMaximum(maxsteps);
		progressBar.setStringPainted(true);
		resetProgressBar();

		advancedTabPane = new JTabbedPane();
		// Main Tabbed Pane
		tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabPane.addTab("RomSettings", IconStore.cfgIcon, romSettingsPanel, "RomSettings");
		tabPane.addTab("Battery", IconStore.batteryIcon, battPanel, "Create your batteries here");
		tabPane.addTab("Wifi", IconStore.wifiIcon, wifiPanel, "Create your Wifi Icons here");
		tabPane.addTab("Signal", IconStore.signalIcon, signalPanel, "Create your Signal Icons here");
		tabPane.addTab("NotificationPanel", IconStore.notificationIcon, notificationBG, "Transparent Notification Panel");
		tabPane.addTab("Toggle", IconStore.toggleIcon, toggleBox, "Get an Overview of your toggles");
		tabPane.addTab("Weather", IconStore.weatherIcon, weatherBox, "Get an Overview of your weather icons");
		tabPane.addTab("Lockring", IconStore.lockringIcon, lockHandleSelector, "See your choosen Lockring!");
		// tabPane.addTab("Advanced Theming", IconStore.additionalIcon,
		// advancedTabPane, "Advanced Stuff!!");

		advancedTabPane.addTab("SystemUI Mods", IconStore.androidredIcon, systemUIBox, "Get an Overview of your icons");
		advancedTabPane.addTab("FrameWorkRes Mods", IconStore.androidblueIcon, frameworkresBox, "Get an Overview of your icons");
		advancedTabPane.addTab("Themes/Morphs", IconStore.folderIcon, iconsetBox, "Add any Themes/Morphs you want...");
		advancedTabPane.addTab("Filesets", IconStore.folder2Icon, filesetBox, "Add Filesets like apk's, lib's, media...");

		tabPane.setSelectedIndex(1);
		// Panel zusammensetzen
		add(tabPane, BorderLayout.CENTER);
		// add(configPane, BorderLayout.WEST);
		add(progressBar, BorderLayout.SOUTH);

		makeButtonBar();
	}

	private void validatePanel(final boolean advancedMode) {
		if (advancedMode == false)
			tabPane.remove(advancedTabPane);
		else
			tabPane.addTab("Advanced Theming", IconStore.additionalIcon, advancedTabPane, "Advanced Stuff!!");

	}

	/**
	 * Creating buttonbar
	 */
	private void makeButtonBar() {
		toolBar.setFloatable(false);
		toolBar.add(advancedToggle);
		toolBar.add(new JPanel());
		toolBar.add(createButton);
		toolBar.add(zipButton);
		// add(toolBar, BorderLayout.NORTH);
	}

	/**
	 * Zip the flashable Zip!
	 */
	private void doZip() {
		// first create everything again, to fill the deploy area with latest
		// settings
		updateProgressBar(step++, "Creating and deploying Icons to ./pngs");
		create();

		final ZipMaker zipper = new ZipMaker();
		final Vector<String> files2add2Lidroid = new Vector<String>();
		final Vector<String> files2add2SystemUI = new Vector<String>();
		final Vector<String> files2add2Framework = new Vector<String>();
		// adding Battery Icons
		updateProgressBar(step++, "Adding Battery Icons (if configured)");
		final AbstractIconCreator activBattCreator = battPanel.getActivBattCreator();
		if (activBattCreator != null) {
			files2add2SystemUI.addAll(activBattCreator.getAllFilenamesAndPath());
		}
		// Add Wifi Icons
		updateProgressBar(step++, "Adding Wifi Icons (if configured)");
		final AbstractWifiCreator activWifiCreator = wifiPanel.getActivWifiCreator();
		if (activWifiCreator != null && !activWifiCreator.toString().equals(NoWifiIcons.name)) {
			files2add2SystemUI.addAll(activWifiCreator.getAllFilenamesAndPath());
		}
		// // Add Signal Icons
		updateProgressBar(step++, "Adding Signal Icons (if configured)");
		final AbstractSignalCreator activSignalCreator = signalPanel.getActivSignalCreator();
		if (activSignalCreator != null && !activSignalCreator.toString().equals(NoSignalIcons.name)) {
			files2add2SystemUI.addAll(activSignalCreator.getAllFilenamesAndPath());
		}

		// Add Toggles
		updateProgressBar(step++, "Adding Toggle Icons (if configured)");
		if (romSettingsPanel.getSettings().isUseLidroid() == true) {
			files2add2Lidroid.addAll(toggleBox.getAllFilenamesAndPath());
		} else { // default
			files2add2SystemUI.addAll(toggleBox.getAllFilenamesAndPath());
		}
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

		// ZipElementCollection anlegen und alle Zipelemente einf�gen
		updateProgressBar(step++, "Creating ZipCollection");
		final ZipElementCollection zipCollection = new ZipElementCollection();
		zipCollection.addElements(files2add2SystemUI, romSettingsPanel.getSettings().getFolderSystemUIInZip());
		zipCollection.addElements(files2add2Framework, romSettingsPanel.getSettings().getFolderFrameworkInZip());
		zipCollection.addElements(files2add2Lidroid, romSettingsPanel.getSettings().getFolderLidroidInZip());

		// Adding Them MORPH
		updateProgressBar(step++, "Adding Theme/Morph to ZipCollection");
		if (iconsetBox.getSelectedSet() != null) {
			zipCollection.addElements(iconsetBox.getSelectedSet().getFilenamesAndPath(), iconsetBox.getSelectedSet().getAllPathInZip());
		}
		// Adding XTRAS
		updateProgressBar(step++, "Adding XTRAS to ZipCollection");
		if (filesetBox.getSelectedSet() != null) {
			zipCollection.addElements(filesetBox.getSelectedSet().getFilenamesAndPath(), filesetBox.getSelectedSet().getAllPathInZip());
		}

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
		step = 0;
		// Creating Battery Icons
		updateProgressBar(step++, "Creating Battery Icons (if configured)");
		battPanel.createAllImages(romSettingsPanel.getSettings());

		// Creating Wifi Icons
		updateProgressBar(step++, "Creating Wifi Icons (if configured)");
		wifiPanel.createAllImages(romSettingsPanel.getSettings());

		// Creating Signal Icons
		updateProgressBar(step++, "Creating Signal Icons (if configured)");
		signalPanel.createAllImages(romSettingsPanel.getSettings());

		// creating notification
		updateProgressBar(step++, "Deploying Notification Background (if configured)");
		notificationBG.createAllImages(romSettingsPanel.getSettings());

		// creating lockHandle
		updateProgressBar(step++, "Deploying Lockring (if configured)");
		lockHandleSelector.createAllImages(romSettingsPanel.getSettings());

		// creating toggles
		updateProgressBar(step++, "Deploying Toggles (if configured)");
		toggleBox.createAllImages(romSettingsPanel.getSettings().getToggleSize());
		// creating weather
		updateProgressBar(step++, "Deploying Weather Icons (if configured)");
		weatherBox.createAllImages(romSettingsPanel.getSettings().getWeatherSize());
		updateProgressBar(step++, "Deploying Weather Icons ...done");
		// Systemui Mods
		updateProgressBar(step++, "Deploying SystemUI Icons (if configured)");
		systemUIBox.createAllImages(IconSetDeployer.NO_RESIZING);
		// frame Mods
		updateProgressBar(step++, "Deploying Framework-res Icons (if configured)");
		frameworkresBox.createAllImages(IconSetDeployer.NO_RESIZING);
		resetProgressBar();
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
	 * @return true, wenn Thread noch l�uft
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

	/**
	 * @return the toolBar
	 */
	public LToolBar getToolBar() {
		return toolBar;
	}

}
