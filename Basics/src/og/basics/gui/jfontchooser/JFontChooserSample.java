package og.basics.gui.jfontchooser;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;


public class JFontChooserSample {
	public static void main(final String[] args) {
		try {
			UIManager.getInstalledLookAndFeels();
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			final JFontChooser fontChooser = new JFontChooser();

			final JFrame window = new JFrame("JFontChooser Sample");
			final JButton showButton = new JButton("Select Font");
			showButton.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					final int result = fontChooser.showDialog(window);
					if (result == JFontChooser.OK_OPTION) {
						final Font font = fontChooser.getSelectedFont();
						showButton.setFont(font);
						window.pack();
						System.out.println("Selected Font : " + font);
					}
				}
			});
			window.getContentPane().add(showButton, BorderLayout.CENTER);
			window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			window.setLocationRelativeTo(null);
			window.pack();
			window.setVisible(true);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
