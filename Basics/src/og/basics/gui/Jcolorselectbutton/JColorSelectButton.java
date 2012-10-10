/**
 * 
 */
package og.basics.gui.Jcolorselectbutton;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 * @author geith
 * 
 */
public class JColorSelectButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1405509065947842554L;

	/**
	 * 
	 */
	public JColorSelectButton() {
		initUI();
	}

	/**
	 * @param arg0
	 */
	public JColorSelectButton(final Icon arg0) {
		super(arg0);
		initUI();
	}

	/**
	 * @param arg0
	 */
	public JColorSelectButton(final String arg0) {
		super(arg0);
		initUI();
	}

	/**
	 * @param arg0
	 */
	public JColorSelectButton(final Action arg0) {
		super(arg0);
		initUI();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public JColorSelectButton(final String arg0, final Icon arg1) {
		super(arg0, arg1);
		initUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#setBackground(java.awt.Color)
	 */
	@Override
	public void setBackground(final Color bg) {
		super.setBackground(bg);
		final int howdark = bg.getRed() + bg.getGreen() + bg.getBlue();
		if (howdark < (3 * 255) / 2)
			setForeground(Color.white);
		else
			setForeground(Color.black);
	}

	private void initUI() {
		// setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				bringUpColorChooser2();
			}
		});

	}

	// private void bringUpColorChooser1() {
	// final Color newColor = JColorChooser.showDialog(JColorSelectButton.this,
	// "Choose Color", getBackground());
	// if (newColor != null) {
	// setBackground(newColor);
	// }
	// }

	private void bringUpColorChooser2() {
		final JColorChooser colorChooser = new JColorChooser();
		final JLabel previewLabel = new JLabel();
		previewLabel.setText("xxx");
		previewLabel.setSize(previewLabel.getPreferredSize());
		previewLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 48));
		previewLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
		colorChooser.setPreviewPanel(previewLabel);

		// Override the chooser panels with our own

		final AbstractColorChooserPanel[] panels = colorChooser.getChooserPanels();

		final AbstractColorChooserPanel[] panelsnew = new AbstractColorChooserPanel[panels.length + 1];

		for (int i = 0; i < panels.length; i++) {
			panelsnew[i] = panels[i];
		}
		panelsnew[panels.length] = new CrayonPanel();

		colorChooser.setChooserPanels(panelsnew);

		final ActionListener okActionListener = new ActionListener() {
			public void actionPerformed(final ActionEvent actionEvent) {
				System.out.println("OK Button");
				System.out.println(colorChooser.getColor());
				setBackground(colorChooser.getColor());
			}
		};

		final ActionListener cancelActionListener = new ActionListener() {
			public void actionPerformed(final ActionEvent actionEvent) {
				System.out.println("Cancel Button");
			}
		};

		final JDialog dialog = JColorChooser.createDialog(null, "Change Color", true, colorChooser, okActionListener, cancelActionListener);

		dialog.setVisible(true);
	}

}
