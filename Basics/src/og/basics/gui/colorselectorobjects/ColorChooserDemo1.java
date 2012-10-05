package og.basics.gui.colorselectorobjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.colorchooser.AbstractColorChooserPanel;

public class ColorChooserDemo1 {

	public static void main(final String[] a) {

		final JColorChooser colorChooser = new JColorChooser();
		final JCLabel previewLabel = new JCLabel();
		previewLabel.setText("xxx");
		previewLabel.setSize(previewLabel.getPreferredSize());
		previewLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 48));
		previewLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
		colorChooser.setPreviewPanel(previewLabel);

		// Override the chooser panels with our own

		final AbstractColorChooserPanel[] panels = colorChooser.getChooserPanels();

		final AbstractColorChooserPanel[] panelsnew = new AbstractColorChooserPanel[panels.length + 1];
		panelsnew[0] = new CrayonPanel();

		for (int i = 0; i < panels.length; i++) {
			panelsnew[i + 1] = panels[i];
		}

		colorChooser.setChooserPanels(panelsnew);

		final ActionListener okActionListener = new ActionListener() {
			public void actionPerformed(final ActionEvent actionEvent) {
				System.out.println("OK Button");
				System.out.println(colorChooser.getColor());
			}
		};

		final ActionListener cancelActionListener = new ActionListener() {
			public void actionPerformed(final ActionEvent actionEvent) {
				System.out.println("Cancel Button");
			}
		};

		final JDialog dialog = JColorChooser.createDialog(null, "Change Button Background", true, colorChooser, okActionListener, cancelActionListener);

		dialog.setVisible(true);
	}
}

/**
 * @author geith
 * 
 */
class JCLabel extends JButton {
	private static final long serialVersionUID = 6585056704904920207L;

	@Override
	public void setForeground(final Color fg) {
		setBackground(fg);
		super.setForeground(fg);
	}

}
