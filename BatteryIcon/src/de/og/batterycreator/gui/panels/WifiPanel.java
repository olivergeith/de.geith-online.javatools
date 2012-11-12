package de.og.batterycreator.gui.panels;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.cfg.gui.WifiSignaleSettingsPanel;
import de.og.batterycreator.creators.wifi.AbstractWifiCreator;
import de.og.batterycreator.creators.wifi.BrickWifi2Creator;
import de.og.batterycreator.creators.wifi.CircleWifiCreator;
import de.og.batterycreator.creators.wifi.ForkWifiCreator;
import de.og.batterycreator.creators.wifi.NoWifiIcons;
import de.og.batterycreator.creators.wifi.StarGateWifiCreator;
import de.og.batterycreator.creators.wifi.TextWifiCreator;
import de.og.batterycreator.creators.wifi.TopCornerWifiCreator;
import de.og.batterycreator.creators.wifi.TowerWifiCreator;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.OverviewPanel;

public class WifiPanel extends JPanel {
	private static final long serialVersionUID = -4657987890334428414L;
	private final JComboBox<AbstractWifiCreator> combo = new JComboBox<AbstractWifiCreator>();
	private AbstractWifiCreator activWifiCreator;
	private final WifiSignaleSettingsPanel settingsPanel = new WifiSignaleSettingsPanel();
	private final OverviewPanel wifiOverviewPanel = new OverviewPanel();
	private final RomSettings romSettings;

	public WifiPanel(final RomSettings romSettings) {
		super();
		this.romSettings = romSettings;
		fillFillCreatorList();
		initUI();
	}

	private void fillFillCreatorList() {
		combo.addItem(new NoWifiIcons(romSettings));
		combo.addItem(new BrickWifi2Creator(romSettings));
		combo.addItem(new TowerWifiCreator(romSettings));
		combo.addItem(new TopCornerWifiCreator(romSettings));
		combo.addItem(new ForkWifiCreator(romSettings));
		combo.addItem(new StarGateWifiCreator(romSettings));
		combo.addItem(new CircleWifiCreator(romSettings));
		combo.addItem(new TextWifiCreator(romSettings));
	}

	private void initUI() {

		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final AbstractWifiCreator cre = (AbstractWifiCreator) combo.getSelectedItem();
				setActivWifiCreator((AbstractWifiCreator) combo.getSelectedItem());

				if (cre != null && !cre.toString().equals(NoWifiIcons.name)) {
					cre.setRomSettings(romSettings);
					cre.createAllImages();
					wifiOverviewPanel.setOverview(cre.getOverviewIcon());
					wifiOverviewPanel.setText("");
				} else {
					wifiOverviewPanel.setOverview(IconStore.nothingIcon);
					wifiOverviewPanel.setText("    No Wifi Icons selected...choose Wifi icon style in Toolbar");
				}
			}
		});
		combo.setRenderer(new WifiCreatorListCellRenderer());
		if (combo.getItemCount() > 0)
			combo.setSelectedIndex(0);
		setToolTipText("Choose your WifiCreator...then press play-button");
		combo.setMaximumRowCount(10);
		setActivWifiCreator((AbstractWifiCreator) combo.getSelectedItem());

		setLayout(new BorderLayout());
		this.add(combo, BorderLayout.NORTH);
		this.add(wifiOverviewPanel, BorderLayout.CENTER);
		this.add(settingsPanel, BorderLayout.WEST);
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
						final BufferedImage bimg = StaticImageHelper.resize2Height(StaticImageHelper.convertImageIcon(icon), 32);
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

	/**
	 * @return the activWifiCreator
	 */
	public AbstractWifiCreator getActivWifiCreator() {
		return activWifiCreator;
	}

	/**
	 * @param activWifiCreator
	 *            the activWifiCreator to set
	 */
	private void setActivWifiCreator(final AbstractWifiCreator activWifiCreator) {
		this.activWifiCreator = activWifiCreator;
	}

}
