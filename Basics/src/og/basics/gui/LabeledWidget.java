package og.basics.gui;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LabeledWidget extends JPanel {
	public static final int COMP_HEIGHT = 25;
	private static final long serialVersionUID = 1558335358181288522L;
	private int widgetLocation;
	private Component comp;

	private final JLabel label = new JLabel();

	public String getLabelText() {
		return label.getText();
	}

	public void setLabelText(final String labeltext) {
		label.setText(labeltext);
	}

	public int getWidgetLocation() {
		return widgetLocation;
	}

	public void setWidgetLocation(final int widgetLocation) {
		this.widgetLocation = widgetLocation;
	}

	public Component getComp() {
		return comp;
	}

	private void setComp(final Component comp) {
		this.comp = comp;
		this.comp.setBounds(widgetLocation, 1, 200, getHeight() - 2);
		this.add(comp);
	}

	public LabeledWidget(final String labelText, Component comp) {
		myInit(labelText);
		setComp(comp);
	}

	private void myInit(final String labelText) {
		setLabelText(labelText);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(label);
	}
}
