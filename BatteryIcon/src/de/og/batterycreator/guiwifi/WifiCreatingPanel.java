package de.og.batterycreator.guiwifi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.og.batterycreator.creators.StyleSettings;
import de.og.batterycreator.creatorswifi.AbstractWifiCreator;
import de.og.batterycreator.creatorswifi.BrickWifiCreator;
import de.og.batterycreator.creatorswifi.NoWifiIcons;

public class WifiCreatingPanel extends JPanel {
	private static final long serialVersionUID = -2956273745014471932L;
	private JComboBox<AbstractWifiCreator> creatorBox;

	private final ConfigWifiPanel configPane = new ConfigWifiPanel();
	private AbstractWifiCreator activCreator = null;
	private final Vector<AbstractWifiCreator> creators = new Vector<AbstractWifiCreator>();

	private final JLabel overviewLabel = new JLabel("Overview");

	public WifiCreatingPanel() {
		initUI();
	}

	public void fillCreatorList() {
		creators.add(new NoWifiIcons());
		creators.add(new BrickWifiCreator());
	}

	private void initUI() {
		setLayout(new BorderLayout());
		fillCreatorList();
		overviewLabel.setBackground(Color.black);
		overviewLabel.setPreferredSize(new Dimension(400, 300));

		// Comobox mit creatoren anzeigen
		creatorBox = new JComboBox<AbstractWifiCreator>(creators);
		creatorBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final AbstractWifiCreator cre = (AbstractWifiCreator) creatorBox.getSelectedItem();

				if (cre != null && !cre.toString().equals(NoWifiIcons.name)) {
					configPane.setSettings(cre.getWifiSettings());
					configPane.setVisible(true);
					overviewLabel.setText("");
				} else {
					configPane.setVisible(false);
					overviewLabel.setIcon(null);
					overviewLabel.setText("    No Wifi Icons selected...choose Wifi icon style in Toolbar");
				}
			}
		});
		if (creatorBox.getItemCount() > 0)
			creatorBox.setSelectedIndex(0);
		creatorBox.setToolTipText("Choose your WifiCreator...then press play-button");
		creatorBox.setMaximumRowCount(10);
		activCreator = (AbstractWifiCreator) creatorBox.getSelectedItem();
		// add(creatorBox, BorderLayout.NORTH);
		add(configPane, BorderLayout.WEST);

		final JPanel overP = new JPanel(new BorderLayout());
		overP.setBackground(Color.black);
		overP.add(overviewLabel, BorderLayout.CENTER);
		overviewLabel.setForeground(Color.white);
		add(overP, BorderLayout.CENTER);
	}

	/**
	 * Creates the desired Icons ;-)
	 */
	public void create(final StyleSettings styleSettings) {
		activCreator = (AbstractWifiCreator) creatorBox.getSelectedItem();
		if (activCreator != null && !activCreator.toString().equals(NoWifiIcons.name)) {
			activCreator.setWifiSettings(configPane.getSettings());
			activCreator.setStylSettings(styleSettings);
			activCreator.createAllImages();
			overviewLabel.setIcon(activCreator.getOverviewIcon());
		}
	}

	public AbstractWifiCreator getActivCreator() {
		return activCreator;
	}

	public Vector<String> getIconNames() {
		if (activCreator != null && !activCreator.toString().equals(NoWifiIcons.name)) {
			return activCreator.getFilenames();
		} else {
			return new Vector<String>();
		}
	}

	public Vector<String> getIconNamesWithPath() {
		if (activCreator != null && !activCreator.toString().equals(NoWifiIcons.name)) {
			return activCreator.getAllFilenamesAndPath();
		} else {
			return new Vector<String>();
		}
	}

	public Vector<ImageIcon> getImageIcons() {
		if (activCreator != null && !activCreator.toString().equals(NoWifiIcons.name)) {
			return activCreator.getIcons();
		} else {
			return new Vector<ImageIcon>();
		}
	}

	public JComboBox<AbstractWifiCreator> getCreatorBox() {
		return creatorBox;
	}
}
