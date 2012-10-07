package de.og.batterycreator.widgets;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class SliderLabel extends JPanel {

	private static final long serialVersionUID = -5143738798997636241L;
	private JSlider slider = null;
	private final JLabel valueLabel = new JLabel();

	Dimension prefsize = new Dimension(130, 25);

	public SliderLabel(final int min, final int max) {
		slider = new JSlider(min, max);
		initUI();
	}

	private void initUI() {
		setLayout(new BorderLayout());
		setPreferredSize(prefsize);
		setSize(prefsize);
		slider.setPreferredSize(new Dimension(120, 20));
		slider.setSize(new Dimension(120, 20));
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent arg0) {
				valueLabel.setText("" + slider.getValue());
			}
		});
		valueLabel.setBorder(new BevelBorder(1));
		final FormLayout layout = new FormLayout("pref, 2dlu, pref", "2dlu, pref, 2dlu");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		final int row = 2;
		builder.add(slider, cc.xyw(1, row, 1));
		builder.add(valueLabel, cc.xyw(3, row, 1));
		final JPanel cfp = builder.getPanel();
		// cfp.setBackground(Color.black);
		this.add(cfp, BorderLayout.CENTER);

	}

	public int getValue() {
		return slider.getValue();
	}

	public void setValue(final int value) {
		slider.setValue(value);
		valueLabel.setText("" + value);

	}

	public JSlider getSlider() {
		return slider;
	}
}
