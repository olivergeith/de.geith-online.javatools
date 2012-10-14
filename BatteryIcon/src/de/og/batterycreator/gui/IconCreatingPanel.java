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
import og.basics.gui.tracepanel.ITracer;
import de.og.batterycreator.creators.AOKPCircleModCreator;
import de.og.batterycreator.creators.AbstractIconCreator;
import de.og.batterycreator.creators.AppleBatteryCreator;
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
import de.og.batterycreator.creators.DecimalBar2Creator;
import de.og.batterycreator.creators.DecimalBarCreator;
import de.og.batterycreator.creators.ScalaBatteryCreator;
import de.og.batterycreator.guiwifi.WifiCreatingPanel;
import de.og.batterycreator.zipcreator.ZipMaker;

public class IconCreatingPanel extends JPanel {
	private static final long serialVersionUID = -2956273745014471932L;
	private final ImageIcon zipIcon = new ImageIcon(this.getClass().getResource("zip.png"));
	private final ImageIcon overIcon = new ImageIcon(this.getClass().getResource("over.png"));
	private final ImageIcon listIcon = new ImageIcon(this.getClass().getResource("list.png"));
	private final ImageIcon wifiIcon = new ImageIcon(this.getClass().getResource("wifi.png"));

	private final JList<String> iconList = new JList<String>();
	private JComboBox<AbstractIconCreator> iconCreatorBox;
	private final ConfigPanel configPane = new ConfigPanel();
	private AbstractIconCreator activIconCreator = null;
	// private final WifiCreatingPanel wifiPanel = new WifiCreatingPanel();
	private final IconOverviewPanel iconOverviewPanel = new IconOverviewPanel();
	private final Vector<AbstractIconCreator> iconCreators = new Vector<AbstractIconCreator>();
	private final ITracer tracer;

	private final WifiCreatingPanel wifiCreatingPanel = new WifiCreatingPanel();

	public IconCreatingPanel(final ITracer tracer) {
		this.tracer = tracer;
		initUI();
	}

	public void fillCreatorList() {
		// creators.add(new ArcCreator());
		// creators.add(new Arc2Creator());
		iconCreators.add(new ArcCreator());
		iconCreators.add(new ArcSunCreator());
		iconCreators.add(new ArcQuaterCreator2());
		iconCreators.add(new ArcDecimalCreator());
		iconCreators.add(new AOKPCircleModCreator());
		iconCreators.add(new BrickBattCreator());
		iconCreators.add(new BrickBattNoGapCreator());
		iconCreators.add(new BrickDecimalCreator());
		iconCreators.add(new BrickDecimal2Creator());
		iconCreators.add(new DecimalBarCreator());
		iconCreators.add(new DecimalBar2Creator());
		iconCreators.add(new BinaryBarsCreator());
		iconCreators.add(new BinarySquaresCreator());
		iconCreators.add(new BatterySymbolCreator());
		iconCreators.add(new BatteryVerticalSymbolCreator());
		iconCreators.add(new AppleBatteryCreator());
		iconCreators.add(new ClockCreator());
		iconCreators.add(new ClockPointerCreator());
		iconCreators.add(new ScalaBatteryCreator());
	}

	private void initUI() {
		setLayout(new BorderLayout());
		fillCreatorList();

		// Icon Liste
		iconList.setCellRenderer(new IconListCellRenderer());
		iconList.setBackground(Color.black);
		final JScrollPane scroller = new JScrollPane();
		scroller.add(iconList);
		scroller.getViewport().setView(iconList);
		scroller.setPreferredSize(new Dimension(800, 600));

		final JTabbedPane tabPane = new JTabbedPane();
		tabPane.addTab("Battery Icon Overview", overIcon, iconOverviewPanel, "Get an Overview of your icons");
		tabPane.addTab("Battery Icon List", listIcon, scroller, "Get an Overview of your icons");
		tabPane.addTab("Wifi Icon Overview", wifiIcon, wifiCreatingPanel, "Get an Overview of your icons");

		add(tabPane, BorderLayout.CENTER);
		add(configPane, BorderLayout.WEST);
		// hier kommen die Wifif Icons ins Spiel
		// add(wifiPanel, BorderLayout.EAST);

		// Comobox mit creatoren anzeigen
		iconCreatorBox = new JComboBox<AbstractIconCreator>(iconCreators);
		iconCreatorBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final AbstractIconCreator cre = (AbstractIconCreator) iconCreatorBox.getSelectedItem();

				if (cre != null) {
					configPane.setSettings(cre.getSettings());
					configPane.enableSupportedFeatures(cre.supportsFlip(), cre.supportsStrokeWidth());
				}
			}
		});
		iconCreatorBox.setSelectedIndex(0);
		iconCreatorBox.setToolTipText("Choose your IconCreator...then press play-button");
		iconCreatorBox.setRenderer(new CreatorListCellRenderer());
		iconCreatorBox.setMaximumRowCount(10);
		activIconCreator = (AbstractIconCreator) iconCreatorBox.getSelectedItem();
		makeMenuAndButtonBar();
	}

	/**
	 * Menüleiste Zusammensetzen
	 */
	private void makeMenuAndButtonBar() {
		final LoadAktion loadAktion = new LoadAktion("Load Settings for selected Creator", CommonIconProvider.getInstance().BUTTON_ICON_OPEN);
		final SaveAktion saveAktion = new SaveAktion("Save Settings for selected Creator", CommonIconProvider.getInstance().BUTTON_ICON_SAVE);
		final CreateAktion createAktion = new CreateAktion("Create Icons", CommonIconProvider.getInstance().BUTTON_ICON_START);
		final ZipAktion zipAktion = new ZipAktion("Create flashable Zip", zipIcon);
		final JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.add(loadAktion);
		toolBar.add(saveAktion);
		toolBar.addSeparator();
		toolBar.add(iconCreatorBox);
		toolBar.add(wifiCreatingPanel.getCreatorBox());
		toolBar.add(createAktion);
		toolBar.add(zipAktion);
		add(toolBar, BorderLayout.NORTH);
	}

	/**
	 * Zip the flashable Zip!
	 */
	private void doZip() {
		final ZipMaker zipper = new ZipMaker(tracer);
		final Vector<String> files2add = new Vector<String>();

		for (int i = 0; i <= 100; i++) {
			files2add.add(activIconCreator.getFilenameAndPath(i, false));
			files2add.add(activIconCreator.getFilenameAndPath(i, true));
		}
		files2add.add(activIconCreator.getFilenameAndPathFull(false));
		files2add.add(activIconCreator.getFilenameAndPathFull(true));

		try {
			final boolean saved = zipper.addFilesToArchive(files2add, activIconCreator.getSettings().getFolderWithinZip(), activIconCreator.getName());
			tracer.appendSuccessText("Everything seems ok....:-)");
			if (saved == true) {
				tracer.appendSuccessText("Everything seems ok....:-)");
				JOptionPane.showMessageDialog(IconCreatingPanel.this, "Zip was created successfully", "Zip creating", JOptionPane.INFORMATION_MESSAGE);
			} else
				tracer.appendSuccessText("Zipping was aborted");
		} catch (final Exception e) {
			tracer.appendErrorText("There was a Problem creating the Zip: " + e.getMessage());
			tracer.appendErrorText("There was a Problem creating the Zip: " + e.getStackTrace());
			JOptionPane.showMessageDialog(IconCreatingPanel.this, "ERROR: Zip was not created successfully!!!\nDid you create icons already ? (Play-Button?!)",
					"Zip creating ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	/**
	 * Creates the desired Icons ;-)
	 */
	private void create() {
		activIconCreator = (AbstractIconCreator) iconCreatorBox.getSelectedItem();
		activIconCreator.setSettings(configPane.getSettings());
		activIconCreator.createAllImages();

		iconList.removeAll();
		iconList.setListData(activIconCreator.getFilenames());
		iconList.repaint();
		iconOverviewPanel.setOverview(activIconCreator.getOverviewIcon());

		// building Wifi Icons
		wifiCreatingPanel.create(configPane.getSettings());

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
			doZip();
		}
	}

	private class LoadAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public LoadAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			if (activIconCreator != null) {
				activIconCreator.loadSettings();
				configPane.setSettings(activIconCreator.getSettings());
			}
		}
	}

	private class SaveAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public SaveAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			if (activIconCreator != null) {
				activIconCreator.setSettings(configPane.getSettings());
				activIconCreator.persistSettings();
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
				if (activIconCreator != null)
					renderer.setIcon(activIconCreator.getIcons().elementAt(index));
			}
			return renderer;
		}
	}

	/**
	 * Renderer für Creator Combobox
	 */
	private class CreatorListCellRenderer implements ListCellRenderer<Object> {
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof AbstractIconCreator) {
				renderer.setBackground(Color.black);
				renderer.setForeground(Color.white);
				final AbstractIconCreator creator = iconCreatorBox.getItemAt(index);
				if (creator != null && renderer.getIcon() == null) {
					final ImageIcon icon = creator.createImage(45, false);
					renderer.setIcon(icon);
				}
			}
			return renderer;
		}
	}

	public AbstractIconCreator getActivCreator() {
		return activIconCreator;
	}

}
