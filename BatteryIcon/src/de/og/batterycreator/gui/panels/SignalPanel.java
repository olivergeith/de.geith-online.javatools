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
import de.og.batterycreator.creators.signal.AbstractSignalCreator;
import de.og.batterycreator.creators.signal.ArcSignalCreator;
import de.og.batterycreator.creators.signal.ForkSignalCreator;
import de.og.batterycreator.creators.signal.NoSignalIcons;
import de.og.batterycreator.creators.signal.TowerSignalCreator;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.OverviewPanel;

public class SignalPanel extends JPanel {
	private static final long serialVersionUID = -4657987890334428414L;

	JComboBox<AbstractSignalCreator> combo = new JComboBox<AbstractSignalCreator>();
	private final OverviewPanel signalOverviewPanel = new OverviewPanel();
	private final WifiSignaleSettingsPanel settingsPanel = new WifiSignaleSettingsPanel();
	private AbstractSignalCreator activSignalCreator;

	private RomSettings romSettings;

	public SignalPanel(final RomSettings romSettings) {
		super();
		setRomSettings(romSettings);
		fillFillCreatorList();
		initUI();
	}

	private void fillFillCreatorList() {
		combo.addItem(new NoSignalIcons(romSettings));
		combo.addItem(new ForkSignalCreator(romSettings));
		combo.addItem(new ArcSignalCreator(romSettings));
		combo.addItem(new TowerSignalCreator(romSettings));
	}

	private void initUI() {

		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				setActivSignalCreator((AbstractSignalCreator) combo.getSelectedItem());
				final AbstractSignalCreator cre = (AbstractSignalCreator) combo.getSelectedItem();

				if (cre != null && !cre.toString().equals(NoSignalIcons.name)) {
					cre.setRomSettings(romSettings);
					cre.createAllImages();
					signalOverviewPanel.setOverview(cre.getOverviewIcon());
					signalOverviewPanel.setText("");
				} else {
					signalOverviewPanel.setOverview(IconStore.nothingIcon);
					signalOverviewPanel.setText("    No Signal Icons selected...choose Signal icon style in Toolbar");
				}
			}
		});
		combo.setRenderer(new SignalCreatorListCellRenderer());
		if (combo.getItemCount() > 0)
			combo.setSelectedIndex(0);
		setToolTipText("Choose your SignalCreator...then press play-button");
		combo.setMaximumRowCount(10);
		setActivSignalCreator((AbstractSignalCreator) combo.getSelectedItem());
		setLayout(new BorderLayout());
		this.add(combo, BorderLayout.NORTH);
		this.add(signalOverviewPanel, BorderLayout.CENTER);
		this.add(settingsPanel, BorderLayout.WEST);

	}

	/**
	 * Renderer for SignalCreator-Combo
	 */
	private class SignalCreatorListCellRenderer implements ListCellRenderer<AbstractSignalCreator> {
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends AbstractSignalCreator> list, final AbstractSignalCreator value, final int index,
				final boolean isSelected, final boolean cellHasFocus) {

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof AbstractSignalCreator) {
				if (isSelected)
					renderer.setBackground(Color.darkGray.darker());
				else
					renderer.setBackground(Color.black);
				renderer.setForeground(Color.white);
				// wenn auch in der Combo selbst ein Icon sein soll:
				final AbstractSignalCreator creator = value;
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

	public OverviewPanel getSignalOverviewPanel() {
		return signalOverviewPanel;
	}

	/**
	 * @return the romSettings
	 */
	public RomSettings getRomSettings() {
		return romSettings;
	}

	/**
	 * @param romSettings
	 *            the romSettings to set
	 */
	public void setRomSettings(final RomSettings romSettings) {
		this.romSettings = romSettings;
	}

	/**
	 * @return the activSignalCreator
	 */
	public AbstractSignalCreator getActivSignalCreator() {
		return activSignalCreator;
	}

	/**
	 * @param activSignalCreator
	 *            the activSignalCreator to set
	 */
	private void setActivSignalCreator(final AbstractSignalCreator activSignalCreator) {
		this.activSignalCreator = activSignalCreator;
	}

}
