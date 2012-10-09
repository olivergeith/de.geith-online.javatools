package de.og.batterycreator.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IconOverviewPanel extends JPanel {
	private static final long serialVersionUID = -8220432122495035757L;
	private final JLabel overviewLabel = new JLabel();

	public IconOverviewPanel() {
		setLayout(new BorderLayout());
		setBackground(Color.black);
		add(overviewLabel);
	}

	public void setOverview(final ImageIcon overview) {
		overviewLabel.setIcon(overview);
	}

}
