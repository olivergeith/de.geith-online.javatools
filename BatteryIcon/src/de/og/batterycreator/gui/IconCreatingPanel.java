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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import og.basics.gui.icon.CommonIconProvider;
import de.og.batterycreator.creators.batt.AOKPCircleModCreator;
import de.og.batterycreator.creators.batt.AbstractIconCreator;
import de.og.batterycreator.creators.batt.AppleBatteryCreator;
import de.og.batterycreator.creators.batt.ArcCreator;
import de.og.batterycreator.creators.batt.ArcDecimalCreator;
import de.og.batterycreator.creators.batt.ArcQuaterCreator2;
import de.og.batterycreator.creators.batt.ArcSunCreator;
import de.og.batterycreator.creators.batt.BatterySymbolCreator;
import de.og.batterycreator.creators.batt.BatteryVerticalSymbolCreator;
import de.og.batterycreator.creators.batt.BinaryBarsCreator;
import de.og.batterycreator.creators.batt.BinarySquaresCreator;
import de.og.batterycreator.creators.batt.BrickBattCreator;
import de.og.batterycreator.creators.batt.BrickBattNoGapCreator;
import de.og.batterycreator.creators.batt.BrickDecimal2Creator;
import de.og.batterycreator.creators.batt.BrickDecimalCreator;
import de.og.batterycreator.creators.batt.ClockCreator;
import de.og.batterycreator.creators.batt.ClockPointerCreator;
import de.og.batterycreator.creators.batt.DecimalBar2Creator;
import de.og.batterycreator.creators.batt.DecimalBarCreator;
import de.og.batterycreator.creators.batt.ScalaBatteryCreator;
import de.og.batterycreator.creators.wifi.AbstractWifiCreator;
import de.og.batterycreator.creators.wifi.BrickWifi2Creator;
import de.og.batterycreator.creators.wifi.BrickWifiCreator;
import de.og.batterycreator.creators.wifi.NoWifiIcons;
import de.og.batterycreator.creators.wifi.TopCornerWifiCreator;
import de.og.batterycreator.creators.wifi.TowerWifiCreator;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.OverviewPanel;
import de.og.batterycreator.gui.widgets.ToggleSelector;
import de.og.batterycreator.zipcreator.ZipMaker;

public class IconCreatingPanel extends JPanel {
	private static final long serialVersionUID = -2956273745014471932L;

	private final JTabbedPane tabPane = new JTabbedPane();
	private final ConfigPanel configPane = new ConfigPanel();
	private final OverviewPanel battOverviewPanel = new OverviewPanel();
	private final OverviewPanel wifiOverviewPanel = new OverviewPanel();

	private final JList<String> battIconList = new JList<String>();
	private JComboBox<AbstractIconCreator> battCreatorBox;
	private AbstractIconCreator activBattCreator = null;
	private final Vector<AbstractIconCreator> battCreators = new Vector<AbstractIconCreator>();

	private AbstractWifiCreator activWifiCreator = null;
	private JComboBox<AbstractWifiCreator> wifiCreatorBox;
	private final Vector<AbstractWifiCreator> wifiCreators = new Vector<AbstractWifiCreator>();

	private final ToggleSelector toggleBox = new ToggleSelector();

	public IconCreatingPanel() {
		initUI();
	}

	private void fillFillCreatorList() {
		wifiCreators.add(new NoWifiIcons());
		wifiCreators.add(new BrickWifiCreator());
		wifiCreators.add(new BrickWifi2Creator());
		wifiCreators.add(new TowerWifiCreator());
		wifiCreators.add(new TopCornerWifiCreator());
	}

	public void fillCreatorList() {
		battCreators.add(new ArcCreator());
		battCreators.add(new ArcSunCreator());
		battCreators.add(new ArcQuaterCreator2());
		battCreators.add(new ArcDecimalCreator());
		battCreators.add(new AOKPCircleModCreator());
		battCreators.add(new BrickBattCreator());
		battCreators.add(new BrickBattNoGapCreator());
		battCreators.add(new BrickDecimalCreator());
		battCreators.add(new BrickDecimal2Creator());
		battCreators.add(new DecimalBarCreator());
		battCreators.add(new DecimalBar2Creator());
		battCreators.add(new BinaryBarsCreator());
		battCreators.add(new BinarySquaresCreator());
		battCreators.add(new BatterySymbolCreator());
		battCreators.add(new BatteryVerticalSymbolCreator());
		battCreators.add(new AppleBatteryCreator());
		battCreators.add(new ClockCreator());
		battCreators.add(new ClockPointerCreator());
		battCreators.add(new ScalaBatteryCreator());
	}

	private void initUI() {
		setLayout(new BorderLayout());
		fillCreatorList();
		fillFillCreatorList();

		// Icon Liste
		battIconList.setCellRenderer(new IconListCellRenderer());
		battIconList.setBackground(Color.black);
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

		// Comobox mit creatoren anzeigen
		battCreatorBox = new JComboBox<AbstractIconCreator>(battCreators);
		battCreatorBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final AbstractIconCreator cre = (AbstractIconCreator) battCreatorBox.getSelectedItem();

				if (cre != null) {
					configPane.setSettings(cre.getStylSettings());
					configPane.enableSupportedFeatures(cre.supportsFlip(), cre.supportsStrokeWidth());
				}
			}
		});
		battCreatorBox.setSelectedIndex(0);
		battCreatorBox.setToolTipText("Choose your IconCreator...then press play-button");
		battCreatorBox.setRenderer(new BattCreatorListCellRenderer());
		battCreatorBox.setMaximumRowCount(10);
		activBattCreator = (AbstractIconCreator) battCreatorBox.getSelectedItem();

		// Wifi Comobox mit creatoren anzeigen
		wifiCreatorBox = new JComboBox<AbstractWifiCreator>(wifiCreators);
		wifiCreatorBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final AbstractWifiCreator cre = (AbstractWifiCreator) wifiCreatorBox.getSelectedItem();

				if (cre != null && !cre.toString().equals(NoWifiIcons.name)) {
					wifiOverviewPanel.setOverview(null);
					wifiOverviewPanel.setText("");
				} else {
					wifiOverviewPanel.setOverview(null);
					wifiOverviewPanel.setText("    No Wifi Icons selected...choose Wifi icon style in Toolbar");
				}
			}
		});
		wifiCreatorBox.setRenderer(new WifiCreatorListCellRenderer());
		if (wifiCreatorBox.getItemCount() > 0)
			wifiCreatorBox.setSelectedIndex(0);
		wifiCreatorBox.setToolTipText("Choose your WifiCreator...then press play-button");
		wifiCreatorBox.setMaximumRowCount(10);
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
		toolBar.add(createAktion);
		toolBar.add(zipAktion);
		add(toolBar, BorderLayout.NORTH);
	}

	/**
	 * Zip the flashable Zip!
	 */
	private void doZip() {
		final ZipMaker zipper = new ZipMaker();
		final Vector<String> files2add = new Vector<String>();
		// adding Battery Icons
		if (activWifiCreator != null) {
			files2add.addAll(activBattCreator.getAllFilenamesAndPath());
		}
		// Add Wifi Icons
		if (activWifiCreator != null && !activWifiCreator.toString().equals(NoWifiIcons.name)) {
			files2add.addAll(activWifiCreator.getAllFilenamesAndPath());
		}
		// Add Toggles
		files2add.addAll(toggleBox.getFilenamesAndPath());

		// now thew actual zipping...
		try {
			final boolean saved = zipper.addFilesToArchive(files2add, activBattCreator.getStylSettings().getFolderWithinZip(), activBattCreator.getName());
			// all ok ? Then Messagebox
			if (saved == true) {
				JOptionPane.showMessageDialog(IconCreatingPanel.this, "Zip was created successfully", "Zip creating", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (final Exception e) {
			// Error during zip...
			JOptionPane.showMessageDialog(IconCreatingPanel.this, "ERROR: Zip was not created successfully!!!\nDid you create icons already ? (Play-Button?!)",
					"Zip creating ERROR", JOptionPane.ERROR_MESSAGE);
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
				if (activBattCreator != null)
					renderer.setIcon(activBattCreator.getIcons().elementAt(index));
			}
			return renderer;
		}
	}

	/**
	 * Renderer for BattCreator-Combo
	 */
	private class BattCreatorListCellRenderer implements ListCellRenderer<AbstractIconCreator> {
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends AbstractIconCreator> list, final AbstractIconCreator value, final int index,
				final boolean isSelected, final boolean cellHasFocus) {
			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof AbstractIconCreator) {
				if (isSelected)
					renderer.setBackground(Color.darkGray.darker());
				else
					renderer.setBackground(Color.black);
				renderer.setForeground(Color.white);
				// wenn auch in der Combo selbst ein Icon sein soll:
				// final AbstractIconCreator creator = value;
				final AbstractIconCreator creator = battCreatorBox.getItemAt(index);
				if (creator != null && renderer.getIcon() == null) {
					final ImageIcon icon = creator.createImage(45, false);
					renderer.setIcon(icon);
				}
			}
			return renderer;
		}
	}

	/**
	 * Renderer for WifiCreator-Combo
	 */
	private class WifiCreatorListCellRenderer implements ListCellRenderer<AbstractWifiCreator> {
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends AbstractWifiCreator> list, final AbstractWifiCreator value, final int index,
				final boolean isSelected, final boolean cellHasFocus) {

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof AbstractWifiCreator) {
				if (isSelected)
					renderer.setBackground(Color.darkGray.darker());
				else
					renderer.setBackground(Color.black);
				renderer.setForeground(Color.white);
				// wenn auch in der Combo selbst ein Icon sein soll:
				// final AbstractWifiCreator creator = value;
				final AbstractWifiCreator creator = wifiCreatorBox.getItemAt(index);
				// renderer.setBorder(new EmptyBorder(1, 1, 1, 1));
				if (creator != null && renderer.getIcon() == null) {
					ImageIcon icon = creator.createImage(3, true);
					if (icon == null)
						icon = IconStore.nothingIcon;
					renderer.setIcon(icon);
				}
			}
			return renderer;
		}

	}
}
