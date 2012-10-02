package de.og.batterycreator.demos;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import de.og.batterycreator.gui.ConfigPanel;

public class EmmyHallo {
	private static final ImageIcon logoIcon = new ImageIcon(ConfigPanel.class.getResource("logo.png"));

	public static void main(final String[] args) {

		final JFrame f = new JFrame();
		f.setTitle("Hallo Emmy!!!!!!!");
		f.setBounds(200, 200, 200, 200);
		f.setIconImage(logoIcon.getImage());
		f.setLayout(new BorderLayout());
		final JLabel la = new JLabel(logoIcon);
		f.add(la, BorderLayout.CENTER);

		f.setVisible(true);
	}
}
