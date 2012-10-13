package de.og.batterycreator.guiwifi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.og.batterycreator.creatorswifi.DefaultWifiCreator;

public class WifiCreatingPanel extends JPanel {
	private static final long serialVersionUID = -2956273745014471932L;
	private JComboBox<DefaultWifiCreator> creatorBox;
	private final ConfigWifiPanel configPane = new ConfigWifiPanel();
	private DefaultWifiCreator activCreator = null;
	private final Vector<DefaultWifiCreator> creators = new Vector<DefaultWifiCreator>();

	private final JLabel overviewLabel = new JLabel("Overview");

	public WifiCreatingPanel() {
		initUI();
	}

	public void fillCreatorList() {
		// creators.add(new ArcCreator());
	}

	private void initUI() {
		setLayout(new BorderLayout());
		fillCreatorList();
		overviewLabel.setBackground(Color.black);

		// Comobox mit creatoren anzeigen
		creatorBox = new JComboBox<DefaultWifiCreator>(creators);
		creatorBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final DefaultWifiCreator cre = (DefaultWifiCreator) creatorBox.getSelectedItem();

				if (cre != null) {
					configPane.setSettings(cre.getWifiSettings());
				}
			}
		});
		if (creatorBox.getItemCount() > 0)
			creatorBox.setSelectedIndex(0);
		creatorBox.setToolTipText("Choose your WifiCreator...then press play-button");
		creatorBox.setMaximumRowCount(10);
		activCreator = (DefaultWifiCreator) creatorBox.getSelectedItem();
		add(creatorBox, BorderLayout.NORTH);
		add(configPane, BorderLayout.CENTER);
		add(overviewLabel, BorderLayout.SOUTH);
	}

	/**
	 * Creates the desired Icons ;-)
	 */
	public void create() {
		activCreator = (DefaultWifiCreator) creatorBox.getSelectedItem();
		activCreator.setWifiSettings(configPane.getSettings());
		activCreator.createAllImages();
	}

	public DefaultWifiCreator getActivCreator() {
		return activCreator;
	}

}
