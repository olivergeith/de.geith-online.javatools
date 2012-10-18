package de.og.batterycreator.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import og.basics.gui.icon.CommonIconProvider;
import de.og.batterycreator.creators.batt.AbstractIconCreator;
import de.og.batterycreator.creators.wifi.AbstractWifiCreator;
import de.og.batterycreator.creators.wifi.NoWifiIcons;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.BattCreatorSelector;
import de.og.batterycreator.gui.widgets.LockHandleSelector;
import de.og.batterycreator.gui.widgets.OverviewPanel;
import de.og.batterycreator.gui.widgets.ToggleSelector;
import de.og.batterycreator.gui.widgets.WifiCreatorSelector;
import de.og.batterycreator.zipcreator.ZipMaker;

public class IconCreatingPanelNew extends JPanel {
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

	private final LockHandleSelector lockHandleSelector = new LockHandleSelector(configPane);
	private final ToggleSelector toggleBox = new ToggleSelector();

	public IconCreatingPanelNew() {
		initUI();
	}

	private void initUI() {
		setLayout(new BorderLayout());

		//
		lockHandleSelector.setPreferredSize(new Dimension(200, 30));

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
		// Tabbed Pane
		tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabPane.addTab("Battery Icon", IconStore.batteryIcon, battTabPane, "Get an Overview of your icons");
		tabPane.addTab("Optional Wifi Icons", IconStore.wifiIcon, wifiOverviewPanel, "Get an Overview of your icons");
		tabPane.addTab("Optional Toggle Icons", IconStore.toggleIcon, toggleBox.getOverviewPanel(), "Get an Overview of your toggles");

		// Panel zusammensetzen
		add(tabPane, BorderLayout.CENTER);
		add(configPane, BorderLayout.WEST);

		// Comobox abfragen
		activBattCreator = (AbstractIconCreator) battCreatorBox.getSelectedItem();
		activWifiCreator = (AbstractWifiCreator) wifiCreatorBox.getSelectedItem();

		makeButtonBar();
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
		toolBar.add(toggleBox);
		toolBar.addSeparator();
		toolBar.add(lockHandleSelector);
		toolBar.addSeparator();
		toolBar.add(createAktion);
		toolBar.add(zipAktion);
		add(toolBar, BorderLayout.NORTH);
	}

	/**
	 * Zip the flashable Zip!
	 */
	private void doZip() {
		final ZipMaker zipper = new ZipMaker();
		final Vector<String> files2add2SystemUI = new Vector<String>();
		final Vector<String> files2add2Framework = new Vector<String>();
		// adding Battery Icons
		activBattCreator = (AbstractIconCreator) battCreatorBox.getSelectedItem();
		if (activBattCreator != null) {
			files2add2SystemUI.addAll(activBattCreator.getAllFilenamesAndPath());
		}
		// Add Wifi Icons
		activWifiCreator = (AbstractWifiCreator) wifiCreatorBox.getSelectedItem();
		if (activWifiCreator != null && !activWifiCreator.toString().equals(NoWifiIcons.name)) {
			files2add2SystemUI.addAll(activWifiCreator.getAllFilenamesAndPath());
		}
		// Add Toggles
		files2add2SystemUI.addAll(toggleBox.getFilenamesAndPath());

		// Lockhandle
		files2add2Framework.addAll(lockHandleSelector.getFilenamesAndPath());

		// TODO Hier fehlt noch was im Zipper!!! um auch framework ins zip zu
		// bekommen

		// now thew actual zipping...
		try {
			final boolean saved = zipper.addFilesToArchive(files2add2SystemUI, activBattCreator.getStylSettings().getFolderSystemUIInZip(),
					activBattCreator.getName());
			// all ok ? Then Messagebox
			if (saved == true) {
				JOptionPane.showMessageDialog(IconCreatingPanelNew.this, "Zip was created successfully", "Zip creating", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (final Exception e) {
			// Error during zip...
			JOptionPane.showMessageDialog(IconCreatingPanelNew.this,
					"ERROR: Zip was not created successfully!!!\nDid you create icons already ? (Play-Button?!)", "Zip creating ERROR",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Creates the desired Icons ;-)
	 */
	private void create() {
		// Creating Battery Icons
		activBattCreator = (AbstractIconCreator) battCreatorBox.getSelectedItem();
		activBattCreator.setStylSettings(configPane.getSettings());
		activBattCreator.createAllImages();
		battIconList.removeAll();
		battIconList.setListData(activBattCreator.getFilenames());
		battIconList.repaint();
		battOverviewPanel.setOverview(activBattCreator.getOverviewIcon());

		// Creating Wifi Icons
		activWifiCreator = (AbstractWifiCreator) wifiCreatorBox.getSelectedItem();
		if (activWifiCreator != null && !activWifiCreator.toString().equals(NoWifiIcons.name)) {
			activWifiCreator.setStylSettings(configPane.getSettings());
			activWifiCreator.createAllImages();
			wifiOverviewPanel.setOverview(activWifiCreator.getOverviewIcon());
		}

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
			doZip();
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
				configPane.setSettings(activBattCreator.getStylSettings());
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
				activBattCreator.setStylSettings(configPane.getSettings());
				activBattCreator.persistSettings();
			}
		}
	}

}
