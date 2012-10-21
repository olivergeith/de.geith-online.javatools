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
import de.og.batterycreator.creators.signal.AbstractSignalCreator;
import de.og.batterycreator.creators.signal.ArcSignalCreator;
import de.og.batterycreator.creators.signal.ForkSignalCreator;
import de.og.batterycreator.creators.signal.NoSignalIcons;
import de.og.batterycreator.creators.signal.TowerSignalCreator;
import de.og.batterycreator.gui.ConfigPanel;
import de.og.batterycreator.gui.iconstore.IconStore;

public class SignalCreatorSelector extends JComboBox<AbstractSignalCreator> {
	private static final long serialVersionUID = -4657987890334428414L;

	private final OverviewPanel signalOverviewPanel = new OverviewPanel();
	private final ConfigPanel configPane;

	public SignalCreatorSelector(final ConfigPanel configPane) {
		super();
		this.configPane = configPane;
		fillFillCreatorList();
		initUI();
	}

	private void fillFillCreatorList() {
		addItem(new NoSignalIcons());
		addItem(new ForkSignalCreator());
		addItem(new ArcSignalCreator());
		addItem(new TowerSignalCreator());
	}

	private void initUI() {

		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final AbstractSignalCreator cre = (AbstractSignalCreator) getSelectedItem();

				if (cre != null && !cre.toString().equals(NoSignalIcons.name)) {
					cre.setStylSettings(configPane.getSettings());
					cre.createAllImages();
					signalOverviewPanel.setOverview(cre.getOverviewIcon());
					signalOverviewPanel.setText("");
				} else {
					signalOverviewPanel.setOverview(null);
					signalOverviewPanel.setText("    No Signal Icons selected...choose Signal icon style in Toolbar");
				}
			}
		});
		setRenderer(new SignalCreatorListCellRenderer());
		if (getItemCount() > 0)
			setSelectedIndex(0);
		setToolTipText("Choose your SignalCreator...then press play-button");
		setMaximumRowCount(10);
		// activSignalCreator = (AbstractSignalCreator) getSelectedItem();
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
						final BufferedImage bimg = StaticImageHelper.resize(StaticImageHelper.convertImageIcon(icon), 32);
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

}
