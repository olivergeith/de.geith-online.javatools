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

import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.creators.wifi.AbstractWifiCreator;
import de.og.batterycreator.creators.wifi.BrickWifi2Creator;
import de.og.batterycreator.creators.wifi.BrickWifiCreator;
import de.og.batterycreator.creators.wifi.CircleWifiCreator;
import de.og.batterycreator.creators.wifi.ForkWifiCreator;
import de.og.batterycreator.creators.wifi.NoWifiIcons;
import de.og.batterycreator.creators.wifi.StarGateWifiCreator;
import de.og.batterycreator.creators.wifi.TextWifiCreator;
import de.og.batterycreator.creators.wifi.TopCornerWifiCreator;
import de.og.batterycreator.creators.wifi.TowerWifiCreator;
import de.og.batterycreator.gui.ConfigPanel;
import de.og.batterycreator.gui.iconstore.IconStore;

public class WifiCreatorSelector extends JComboBox<AbstractWifiCreator> {
	private static final long serialVersionUID = -4657987890334428414L;

	private final OverviewPanel wifiOverviewPanel = new OverviewPanel();
	private final ConfigPanel configPane;

	public WifiCreatorSelector(final ConfigPanel configPane) {
		super();
		this.configPane = configPane;
		fillFillCreatorList();
		initUI();
	}

	private void fillFillCreatorList() {
		addItem(new NoWifiIcons());
		addItem(new BrickWifiCreator());
		addItem(new BrickWifi2Creator());
		addItem(new TowerWifiCreator());
		addItem(new TopCornerWifiCreator());
		addItem(new ForkWifiCreator());
		addItem(new StarGateWifiCreator());
		addItem(new CircleWifiCreator());
		addItem(new TextWifiCreator());
	}

	private void initUI() {

		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final AbstractWifiCreator cre = (AbstractWifiCreator) getSelectedItem();

				if (cre != null && !cre.toString().equals(NoWifiIcons.name)) {
					cre.setStylSettings(configPane.getSettings());
					cre.createAllImages();
					wifiOverviewPanel.setOverview(cre.getOverviewIcon());
					wifiOverviewPanel.setText("");
				} else {
					wifiOverviewPanel.setOverview(IconStore.nothingIcon);
					wifiOverviewPanel.setText("    No Wifi Icons selected...choose Wifi icon style in Toolbar");
				}
			}
		});
		setRenderer(new WifiCreatorListCellRenderer());
		if (getItemCount() > 0)
			setSelectedIndex(0);
		setToolTipText("Choose your WifiCreator...then press play-button");
		setMaximumRowCount(10);
		// activWifiCreator = (AbstractWifiCreator) getSelectedItem();
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
				final AbstractWifiCreator creator = value;
				if (creator != null && renderer.getIcon() == null) {
					ImageIcon icon = creator.createImage(3, true);
					if (icon == null) {
						icon = IconStore.nothingIcon;
						renderer.setIcon(icon);
					} else {
						final BufferedImage bimg = StaticImageHelper.resize(StaticImageHelper.convertImageIcon(icon), 32);
						renderer.setIcon(new ImageIcon(bimg));
					}
				}
			}
			return renderer;
		}

	}

	public OverviewPanel getWifiOverviewPanel() {
		return wifiOverviewPanel;
	}

}
