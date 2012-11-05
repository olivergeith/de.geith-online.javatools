package de.og.batterycreator.gui.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import de.og.batterycreator.creators.settings.RomPreset;

public class DrawableComboBox extends JComboBox<String> {
	private static final long serialVersionUID = 1L;

	public static final String FOLDER_XHDPI = "drawable-xhdpi";
	public static final String FOLDER_HDPI = "drawable-hdpi";
	public static final String FOLDER_720DP = "drawable-sw720dp-xhdpi";
	public static final String FOLDER_600DP = "drawable-sw600dp-xhdpi";

	private final SliderAndLabel sliderResize = new SliderAndLabel(25, 50);

	public DrawableComboBox() {

		initUI();

	}

	private void initUI() {
		addItem(FOLDER_XHDPI);
		addItem(FOLDER_HDPI);
		addItem(FOLDER_600DP);
		addItem(FOLDER_720DP);
		setEditable(false);
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (getSelectedItem().equals(FOLDER_XHDPI)) {
					sliderResize.setValue(RomPreset.BATT_ICON_HEIGHT_XHDPI);
				} else if (getSelectedItem().equals(FOLDER_HDPI)) {
					sliderResize.setValue(RomPreset.BATT_ICON_HEIGHT_HDPI);
				} else if (getSelectedItem().equals(FOLDER_720DP)) {
					sliderResize.setValue(RomPreset.BATT_ICON_HEIGHT_720DP);
				} else if (getSelectedItem().equals(FOLDER_600DP)) {
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
