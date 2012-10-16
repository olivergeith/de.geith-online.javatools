package de.og.batterycreator.gui.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

public class DrawableComboBox extends JComboBox<String> {
	private static final long serialVersionUID = 1L;

	public static final String FOLDER_XHDPI = "drawable-xhdpi";
	public static final String FOLDER_HDPI = "drawable-hdpi";
	public static final String FOLDER_720DP = "drawable-sw720dp-xhdpi";
	public static final String FOLDER_600DP = "drawable-sw600dp-xhdpi";

	public static final int ICON_HEIGHT_XHDPI = 38;
	public static final int ICON_HEIGHT_HDPI = 27;
	public static final int ICON_HEIGHT_720DP = 48;
	public static final int ICON_HEIGHT_600DP = 43;

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
					sliderResize.setValue(ICON_HEIGHT_XHDPI);
				} else if (getSelectedItem().equals(FOLDER_HDPI)) {
					sliderResize.setValue(ICON_HEIGHT_HDPI);
				} else if (getSelectedItem().equals(FOLDER_720DP)) {
					sliderResize.setValue(ICON_HEIGHT_720DP);
				} else if (getSelectedItem().equals(FOLDER_600DP)) {
					sliderResize.setValue(ICON_HEIGHT_600DP);
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
