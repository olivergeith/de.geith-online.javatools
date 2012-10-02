/**
 * 
 */
package og.basics.gui.colorselectorobjects;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;

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
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final Color newColor = JColorChooser.showDialog(JColorSelectButton.this, "Choose Color", getBackground());
				if (newColor != null) {
					setBackground(newColor);
				}
			}
		});

	}

}
