package de.og.batterycreator.gui.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import de.og.batterycreator.cfg.RomPreset;

public class DrawableComboBox extends JComboBox<String> {
	private static final long serialVersionUID = 1L;

	private final SliderAndLabel sliderResize = new SliderAndLabel(25, 50);

	public DrawableComboBox() {

		initUI();

	}

	private void initUI() {
		addItem(RomPreset.FOLDER_XHDPI);
		addItem(RomPreset.FOLDER_HDPI);
		addItem(RomPreset.FOLDER_600DP);
		addItem(RomPreset.FOLDER_720DP);
		setEditable(false);
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (getSelectedItem().equals(RomPreset.FOLDER_XHDPI)) {
					sliderResize.setValue(RomPreset.BATT_ICON_HEIGHT_XHDPI);
				} else if (getSelectedItem().equals(RomPreset.FOLDER_HDPI)) {
					sliderResize.setValue(RomPreset.BATT_ICON_HEIGHT_HDPI);
				} else if (getSelectedItem().equals(RomPreset.FOLDER_720DP)) {
					sliderResize.setValue(RomPreset.BATT_ICON_HEIGHT_720DP);
				} else if (getSelectedItem().equals(RomPreset.FOLDER_600DP)) {
					sliderResize.setValue(RomPreset.BATT_ICON_HEIGHT_600DP);
				}
			}
		});
	}

	/**
	 * @return the slider
	 */
	public SliderAndLabel getSizeSlider() {
		return sliderResize;
	}

}
