package de.og.batterycreator.gui.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import de.og.batterycreator.cfg.RomPreset;

public class DrawableComboBox extends JComboBox<String> {
	private static final long serialVersionUID = 1L;

	private final SliderAndLabel sliderBattSize = new SliderAndLabel(25, 50);

	public DrawableComboBox() {

		initUI();

	}

	private void initUI() {
		addItem(RomPreset.DRAWABLE_XHDPI);
		addItem(RomPreset.DRAWABLE_HDPI);
		addItem(RomPreset.DRAWABLE_600DP);
		addItem(RomPreset.DRAWABLE_720DP);
		setEditable(false);
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (getSelectedItem().equals(RomPreset.DRAWABLE_XHDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_XHDPI);
				} else if (getSelectedItem().equals(RomPreset.DRAWABLE_HDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_HDPI);
				} else if (getSelectedItem().equals(RomPreset.DRAWABLE_720DP)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_720DP);
				} else if (getSelectedItem().equals(RomPreset.DRAWABLE_600DP)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_600DP);
				}
			}
		});
	}

	/**
	 * @return the slider
	 */
	public SliderAndLabel getSliderBattSize() {
		return sliderBattSize;
	}
}
