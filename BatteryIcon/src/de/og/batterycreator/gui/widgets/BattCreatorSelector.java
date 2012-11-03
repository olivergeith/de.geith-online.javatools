package de.og.batterycreator.gui.widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.creators.batt.AOKPCircleModCreator;
import de.og.batterycreator.creators.batt.AbstractIconCreator;
import de.og.batterycreator.creators.batt.AppleBatteryCreator;
import de.og.batterycreator.creators.batt.ArcCreator;
import de.og.batterycreator.creators.batt.ArcCreator2;
import de.og.batterycreator.creators.batt.ArcCreator3;
import de.og.batterycreator.creators.batt.ArcDecimalCreator;
import de.og.batterycreator.creators.batt.ArcQuaterCreator2;
import de.og.batterycreator.creators.batt.ArcSunCreator;
import de.og.batterycreator.creators.batt.BatterySymbolCreator;
import de.og.batterycreator.creators.batt.BatteryVerticalSymbolCreator;
import de.og.batterycreator.creators.batt.BinaryBarsCreator;
import de.og.batterycreator.creators.batt.BinarySquaresCreator;
import de.og.batterycreator.creators.batt.BoxCreator;
import de.og.batterycreator.creators.batt.BrickBattCreator;
import de.og.batterycreator.creators.batt.BrickBattNoGapCreator;
import de.og.batterycreator.creators.batt.BrickDecimal2Creator;
import de.og.batterycreator.creators.batt.BrickDecimalCreator;
import de.og.batterycreator.creators.batt.BubbleCreator;
import de.og.batterycreator.creators.batt.ClockCreator;
import de.og.batterycreator.creators.batt.ClockPointerCreator;
import de.og.batterycreator.creators.batt.DecimalBar2Creator;
import de.og.batterycreator.creators.batt.DecimalBarCreator;
import de.og.batterycreator.creators.batt.HoneycombCreator;
import de.og.batterycreator.creators.batt.NoBattIcons;
import de.og.batterycreator.creators.batt.ScalaBatteryCreator;
import de.og.batterycreator.creators.batt.XORAndroidCreator;
import de.og.batterycreator.creators.batt.XORApfelCreator;
import de.og.batterycreator.creators.batt.XORSkullCreator;
import de.og.batterycreator.creators.batt.XORVnvCreator;
import de.og.batterycreator.gui.ConfigPanel;
import de.og.batterycreator.gui.iconstore.IconStore;

public class BattCreatorSelector extends JComboBox<AbstractIconCreator> {
	private static final long serialVersionUID = -5956664471952448919L;

	private final JList<String> battIconList = new JList<String>();
	private AbstractIconCreator activBattCreator = null;
	private final OverviewPanel battOverviewPanel = new OverviewPanel();

	private final ConfigPanel configPane;

	public BattCreatorSelector(final ConfigPanel configPane) {
		super();
		this.configPane = configPane;
		fillCreatorList();
		initIconList();
		initUI();
	}

	private void fillCreatorList() {
		addItem(new NoBattIcons());
		addItem(new ArcCreator());
		addItem(new ArcCreator2());
		addItem(new ArcCreator3());
		addItem(new ArcSunCreator());
		addItem(new ArcQuaterCreator2());
		addItem(new ArcDecimalCreator());
		addItem(new HoneycombCreator());
		addItem(new AOKPCircleModCreator());
		addItem(new BoxCreator());
		addItem(new BubbleCreator());
		addItem(new BrickBattCreator());
		addItem(new BrickBattNoGapCreator());
		addItem(new BrickDecimalCreator());
		addItem(new BrickDecimal2Creator());
		addItem(new DecimalBarCreator());
		addItem(new DecimalBar2Creator());
		addItem(new BinaryBarsCreator());
		addItem(new BinarySquaresCreator());
		addItem(new BatterySymbolCreator());
		addItem(new BatteryVerticalSymbolCreator());
		addItem(new AppleBatteryCreator());
		addItem(new ClockCreator());
		addItem(new ClockPointerCreator());
		addItem(new ScalaBatteryCreator());
		addItem(new XORAndroidCreator());
		addItem(new XORApfelCreator());
		addItem(new XORSkullCreator());
		addItem(new XORVnvCreator());
	}

	private void initIconList() {
		battIconList.setCellRenderer(new IconListCellRenderer());
		battIconList.setBackground(Color.black);
	}

	private void initUI() {
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final AbstractIconCreator cre = (AbstractIconCreator) getSelectedItem();

				if (cre != null) {
					activBattCreator = cre;
					configPane.setSettings(cre.getSettings());
					configPane.enableSupportedFeatures(cre.supportsFlip(), cre.supportsStrokeWidth());
					// remove next line, if you dont want to generate images
					// on startup!
					updateOverviews(cre);
				} else {
					battIconList.removeAll();
					battOverviewPanel.setOverview(IconStore.nothingIcon);
					battOverviewPanel.setText("    No Battery Icons selected...choose Wifi icon style in Toolbar");
				}
			}

			public void updateOverviews(final AbstractIconCreator cre) {
				cre.setSettings(configPane.getSettings());
				cre.createAllImages();
				if (cre.getOverviewIcon().equals(IconStore.nothingIcon))
					battOverviewPanel.setText("Choose your Battery Style in dropdown box");
				else
					battOverviewPanel.setText("");
				battOverviewPanel.setOverview(cre.getOverviewIcon());
				battIconList.removeAll();
				battIconList.setListData(cre.getFilenames());
				battIconList.repaint();
			}
		});
		if (getItemCount() > 0)
			setSelectedIndex(0);
		activBattCreator = (AbstractIconCreator) getSelectedItem();

		setToolTipText("Choose your IconCreator...then press play-button");
		setRenderer(new BattCreatorListCellRenderer());
		setMaximumRowCount(10);
	}

	/**
	 * @return the battIconList
	 */
	public JList<String> getBattIconList() {
		return battIconList;
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
				final AbstractIconCreator creator = value;
				if (creator != null && renderer.getIcon() == null) {
					final ImageIcon icon = creator.createImage(45, false);
					final BufferedImage bimg = StaticImageHelper.resize2Height(StaticImageHelper.convertImageIcon(icon), 32);
					renderer.setIcon(new ImageIcon(bimg));
				}
			}
			return renderer;
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

	public OverviewPanel getBattOverviewPanel() {
		return battOverviewPanel;
	}

}
