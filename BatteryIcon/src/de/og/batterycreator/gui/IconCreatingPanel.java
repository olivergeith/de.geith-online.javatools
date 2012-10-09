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
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import og.basics.gui.icon.CommonIconProvider;
import og.basics.gui.tracepanel.ITracer;
import de.og.batterycreator.creators.AppleBatteryCreator;
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

public class IconCreatingPanel extends JPanel {
	private static final long serialVersionUID = -2956273745014471932L;
	private final JList<String> iconList = new JList<String>();
	private JComboBox<DefaultCreator> creatorBox;
	private final ConfigPanel configPane = new ConfigPanel();
	private DefaultCreator activCreator = null;

	private final Vector<DefaultCreator> creators = new Vector<DefaultCreator>();
	private final ImageIcon zipIcon = new ImageIcon(this.getClass().getResource("zip.png"));
	private final ITracer tracer;
	private final IconOverviewPanel overviewPanel;

	public IconCreatingPanel(final ITracer tracer, final IconOverviewPanel overviewPanel) {
		this.tracer = tracer;
		this.overviewPanel = overviewPanel;
		initUI();
	}

	public void fillCreatorList() {
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
		creators.add(new AppleBatteryCreator());
		creators.add(new ClockCreator());
		creators.add(new ClockPointerCreator());
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
		scroller.setPreferredSize(new Dimension(400, 600));

		// final JPanel centerPanel = new JPanel(new BorderLayout());
		// centerPanel.add(scroller, BorderLayout.CENTER);
		// centerPanel.add(new JLabel(logoIcon), BorderLayout.SOUTH);
		add(scroller, BorderLayout.CENTER);
		add(configPane, BorderLayout.WEST);

		// Comobox mit creatoren anzeigen
		creatorBox = new JComboBox<DefaultCreator>(creators);
		creatorBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final DefaultCreator cre = (DefaultCreator) creatorBox.getSelectedItem();

				if (cre != null) {
					configPane.setSettings(cre.getSettings());
					configPane.enableSupportedFeatures(cre.supportsFlip(), cre.supportsStrokeWidth());
				}
			}
		});
		creatorBox.setSelectedIndex(0);
		creatorBox.setToolTipText("Choose your IconCreator...then press play-button");
		creatorBox.setRenderer(new CreatorListCellRenderer());
		creatorBox.setMaximumRowCount(15);
		activCreator = (DefaultCreator) creatorBox.getSelectedItem();
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
		toolBar.add(creatorBox);
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
			files2add.add(activCreator.getFilenameAndPath(i, false));
			files2add.add(activCreator.getFilenameAndPath(i, true));
		}
		files2add.add(activCreator.getFilenameAndPathFull(false));
		files2add.add(activCreator.getFilenameAndPathFull(true));

		try {
			zipper.addFilesToArchive(files2add, activCreator.getSettings().getFolderWithinZip(), activCreator.getName());
			tracer.appendSuccessText("Everything seems ok....:-)");
			JOptionPane.showMessageDialog(IconCreatingPanel.this, "Zip was created successfully", "Zip creating", JOptionPane.INFORMATION_MESSAGE);
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
		activCreator = (DefaultCreator) creatorBox.getSelectedItem();
		activCreator.setSettings(configPane.getSettings());
		activCreator.createAllImages();

		iconList.removeAll();
		iconList.setListData(activCreator.getFilenames());
		iconList.repaint();
		overviewPanel.setOverview(activCreator.getOverviewIcon());
		// pack();
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
			final int n = JOptionPane.showConfirmDialog(IconCreatingPanel.this, "Would you like to create a flashable zip now ?", "Create Flashable-Zip...",
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
				activCreator.loadSettings();
				configPane.setSettings(activCreator.getSettings());
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
				activCreator.setSettings(configPane.getSettings());
				activCreator.persistSettings();
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

	public DefaultCreator getActivCreator() {
		return activCreator;
	}

}
