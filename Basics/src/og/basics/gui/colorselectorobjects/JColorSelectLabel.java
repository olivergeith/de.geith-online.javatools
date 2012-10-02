/**
 * 
 */
package og.basics.gui.colorselectorobjects;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JColorChooser;
import javax.swing.JLabel;

/**
 * Klasse öffnet beim Click auf Label einen ColorChooserDialog
 * 
 * @author geith
 * 
 */
public class JColorSelectLabel extends JLabel {
	private static final long serialVersionUID = 4308132056037904534L;

	/**
	 * 
	 */
	public JColorSelectLabel() {
		initUI();
	}

	/**
	 * @param arg0
	 */
	public JColorSelectLabel(final String arg0) {
		super(arg0);
		initUI();
	}

	/**
	 * @param arg0
	 */
	public JColorSelectLabel(final Icon arg0) {
		super(arg0);
		initUI();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public JColorSelectLabel(final String arg0, final int arg1) {
		super(arg0, arg1);
		initUI();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public JColorSelectLabel(final Icon arg0, final int arg1) {
		super(arg0, arg1);
		initUI();
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public JColorSelectLabel(final String arg0, final Icon arg1, final int arg2) {
		super(arg0, arg1, arg2);
		initUI();
	}

	private void initUI() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent arg0) {
				final Color newColor = JColorChooser.showDialog(JColorSelectLabel.this, "Choose Color", getForeground());
				if (newColor != null) {
					setForeground(newColor);
				}
			}
		});
	}

}
