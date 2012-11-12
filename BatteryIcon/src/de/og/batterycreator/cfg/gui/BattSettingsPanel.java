package de.og.batterycreator.cfg.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import og.basics.gui.Jcolorselectbutton.JColorSelectButton;
import og.basics.gui.jfontchooser.JFontChooserButton;
import og.basics.jgoodies.JGoodiesHelper;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.og.batterycreator.cfg.BattSettings;
import de.og.batterycreator.gui.widgets.SliderAndLabel;
import de.og.batterycreator.gui.widgets.chargeiconselector.ChargeIconSelector;

public class BattSettingsPanel extends SettingsPanel {
	private static final long serialVersionUID = 1L;

	private BattSettings settings;
	private final String fontSizes[] = {
			"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32"
	};

	private final JColorSelectButton fontColor = new JColorSelectButton("Main Color", "Color when normal battery-level");
	private final JColorSelectButton fontColorLowBatt = new JColorSelectButton("LowBatt", "Color when low battery");
	private final JColorSelectButton fontColorMedBatt = new JColorSelectButton("MedBatt", "Color when Med battery");
	private final JColorSelectButton fontColorCharge = new JColorSelectButton("Charge Color", "Color when charging");
	private final ChargeIconSelector chargeIconSeletor = new ChargeIconSelector();

	private final JCheckBox cboxTransparentBgrnd = createCheckbox("Transparent Background (switchOff = experimental !)",
			"Use this, when your statusbar Background is not black!");
	private final JColorSelectButton backgroundColor = new JColorSelectButton("Background Color", "Color if not transparent");

	private final JColorSelectButton iconColor = new JColorSelectButton("Main Color", "Color when normal battery-level");
	private final JColorSelectButton iconColorLowBatt = new JColorSelectButton("LowBatt", "Color when low battery");
	private final JColorSelectButton iconColorMedBatt = new JColorSelectButton("MedBatt", "Color when Med battery");
	private final JColorSelectButton iconColorInactiv = new JColorSelectButton("Inactiv", "Color for inactiv Iconelements");
	private final JColorSelectButton iconColorCharge = new JColorSelectButton("Charge Color", "Color when charging");

	private final SliderAndLabel sliderStroke = new SliderAndLabel(1, 10);
	private final JCheckBox cboxFlip = createCheckbox("Flip Icon", "Mirror's the Icon...ony has effect on a few styls!");

	private final JCheckBox cboxColoredFont = createCheckbox("Low battery Colors", "...");
	private final JCheckBox cboxColoredIcon = createCheckbox("Low battery Colors", "...");
	private final JCheckBox cboxShowFont = createCheckbox("Show percentages", "...");
	private final JCheckBox cboxShowChargeSymbol = createCheckbox("Charge-Symbol", "Show Charge-Symbol when charging");
	private final JCheckBox cboxUseGradientMediumLevels = createCheckbox("Gradient for Medium levels", "Use Gradient Colors between Low and Med Batterylevels");
	private final JCheckBox cboxUseGradientNormalLevels = createCheckbox("Gradient for Normal levels", "Use Gradient Colors between Med and 100% Batterylevels");

	private final SliderAndLabel sliderLowBatt = new SliderAndLabel(0, 30);
	private final SliderAndLabel sliderMedBatt = new SliderAndLabel(0, 100);

	private final SliderAndLabel sliderFontXOffset = new SliderAndLabel(-12, 12);
	private final SliderAndLabel sliderFontYOffset = new SliderAndLabel(-12, 12);
	private final SliderAndLabel slidericonXOffset = new SliderAndLabel(-12, 12);
	private final SliderAndLabel slidericonYOffset = new SliderAndLabel(-12, 12);

	private final SliderAndLabel sliderReduceOn100 = new SliderAndLabel(-5, 0);

	private final SliderAndLabel sliderResizeChargeSymbol = new SliderAndLabel(15, 30);
	private final JCheckBox cboxResizeChargeSymbol = createCheckbox("resize Charge Symbol to: (pixel)", " Resize the Charge Symbol to make it fit better");

	private final JFontChooserButton fontButton = new JFontChooserButton("Choose Font", fontSizes);

	// Construktor
	public BattSettingsPanel() {
		initComponents();
		myInit();
	}

	private void initComponents() {
		sliderLowBatt.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent arg0) {
				final int low = sliderLowBatt.getValue();
				final int med = sliderMedBatt.getValue();
				if (low > med)
					sliderMedBatt.setValue(low);
			}
		});

		sliderMedBatt.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent arg0) {
				final int low = sliderLowBatt.getValue();
				final int med = sliderMedBatt.getValue();
				if (med < low)
					sliderLowBatt.setValue(med);
			}
		});
	}

	private void myInit() {
		setLayout(new BorderLayout());
		this.add(createTabPaneBattSettings(), BorderLayout.CENTER);
	}

	public JPanel createTabPaneBattSettings() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;
		builder.add(JGoodiesHelper.createGroupLabel("Percentages..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));

		builder.add(cboxShowChargeSymbol, cc.xyw(2, ++row, 3));

		builder.add(cboxShowFont, cc.xyw(2, ++row, 3));
		builder.add(cboxColoredFont, cc.xyw(6, row, 3));

		builder.add(fontColor, cc.xyw(2, ++row, 1));
		builder.add(fontColorCharge, cc.xyw(4, row, 1));
		builder.add(fontColorLowBatt, cc.xyw(6, row, 1));
		builder.add(fontColorMedBatt, cc.xyw(8, row, 1));

		builder.add(JGoodiesHelper.createBlackLabel("Reduce font on 100% by <x> pixel"), cc.xyw(6, ++row, 3));
		builder.add(fontButton, cc.xyw(2, ++row, 3));
		builder.add(sliderReduceOn100, cc.xyw(6, row, 1));
		builder.add(sliderReduceOn100.getValueLabel(), cc.xyw(8, row, 1));

		builder.add(JGoodiesHelper.createBlackLabel("Font Pixel Position Offsets X"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Font Pixel Position Offsets Y"), cc.xyw(6, row, 3));
		builder.add(sliderFontXOffset, cc.xyw(2, ++row, 1));
		builder.add(sliderFontXOffset.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(sliderFontYOffset, cc.xyw(6, row, 1));
		builder.add(sliderFontYOffset.getValueLabel(), cc.xyw(8, row, 1));

		builder.add(JGoodiesHelper.createGroupLabel("Charge Icon..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));

		builder.add(cboxShowChargeSymbol, cc.xyw(2, ++row, 1));
		builder.add(chargeIconSeletor, cc.xyw(4, row, 1));

		builder.add(JGoodiesHelper.createBlackLabel("ChargeIcon Pixel Position Offsets X"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("ChargeIcon Pixel Position Offsets Y"), cc.xyw(6, row, 3));
		builder.add(slidericonXOffset, cc.xyw(2, ++row, 1));
		builder.add(slidericonXOffset.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(slidericonYOffset, cc.xyw(6, row, 1));
		builder.add(slidericonYOffset.getValueLabel(), cc.xyw(8, row, 1));

		builder.add(cboxResizeChargeSymbol, cc.xyw(2, ++row, 3));
		builder.add(sliderResizeChargeSymbol, cc.xyw(6, row, 1));
		builder.add(sliderResizeChargeSymbol.getValueLabel(), cc.xyw(8, row, 1));

		builder.add(JGoodiesHelper.createGroupLabel("Battery Icon..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));

		builder.add(iconColorInactiv, cc.xyw(2, ++row, 1));
		builder.add(backgroundColor, cc.xyw(4, row, 1));
		builder.add(cboxColoredIcon, cc.xyw(6, row, 3));
		builder.add(iconColor, cc.xyw(2, ++row, 1));
		builder.add(iconColorCharge, cc.xyw(4, row, 1));
		builder.add(iconColorLowBatt, cc.xyw(6, row, 1));
		builder.add(iconColorMedBatt, cc.xyw(8, row, 1));
		builder.add(cboxTransparentBgrnd, cc.xyw(1, ++row, 6));

		builder.add(JGoodiesHelper.createGroupLabel("Thresholds..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("...for Low Battery-Levels"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("...for Med Battery-Levels"), cc.xyw(6, row, 3));
		builder.add(sliderLowBatt, cc.xyw(2, ++row, 1));
		builder.add(sliderLowBatt.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(sliderMedBatt, cc.xyw(6, row, 1));
		builder.add(sliderMedBatt.getValueLabel(), cc.xyw(8, row, 1));
		builder.add(cboxUseGradientMediumLevels, cc.xyw(2, ++row, 3));
		builder.add(cboxUseGradientNormalLevels, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Misc Options ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("These settings only work on some styls"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Stroke Width"), cc.xyw(6, row, 3));
		builder.add(cboxFlip, cc.xyw(2, ++row, 3));
		builder.add(sliderStroke, cc.xyw(6, row, 1));
		builder.add(sliderStroke.getValueLabel(), cc.xyw(8, row, 1));

		final JPanel cfp = builder.getPanel();
		return cfp;
	}

	public void setSettings(final BattSettings settings) {
		this.settings = settings;
		fontColor.setColor(settings.getFontColor());
		fontColorLowBatt.setColor(settings.getFontColorLowBatt());
		fontColorMedBatt.setColor(settings.getFontColorMedBatt());
		fontColorCharge.setColor(settings.getFontChargeColor());

		iconColor.setColor(settings.getIconColor());
		iconColorLowBatt.setColor(settings.getIconColorLowBatt());
		iconColorMedBatt.setColor(settings.getIconColorMedBatt());
		iconColorInactiv.setColor(settings.getIconColorInActiv());
		iconColorCharge.setColor(settings.getIconChargeColor());

		backgroundColor.setColor(settings.getBackgroundColor());
		cboxTransparentBgrnd.setSelected(settings.isTransparentBackground());

		cboxFlip.setSelected(settings.isFlip());
		sliderStroke.setValue(settings.getStrokewidth());

		cboxShowFont.setSelected(settings.isShowFont());
		cboxShowChargeSymbol.setSelected(settings.isShowChargeSymbol());
		cboxColoredFont.setSelected(settings.isColoredFont());
		cboxColoredIcon.setSelected(settings.isColoredIcon());

		sliderMedBatt.setValue(settings.getMedBattTheshold());
		sliderLowBatt.setValue(settings.getLowBattTheshold());

		if (settings.getChargeIcon() != null)
			chargeIconSeletor.setSelectedItem(settings.getChargeIcon());
		else
			chargeIconSeletor.setSelectedIndex(1);

		cboxUseGradientMediumLevels.setSelected(settings.isUseGradiantForMediumColor());
		cboxUseGradientNormalLevels.setSelected(settings.isUseGradiantForNormalColor());
		fontButton.setFont(settings.getFont());

		slidericonXOffset.setValue(settings.getIconXOffset());
		slidericonYOffset.setValue(settings.getIconYOffset());

		sliderFontXOffset.setValue(settings.getFontXOffset());
		sliderFontYOffset.setValue(settings.getFontYOffset());
		sliderReduceOn100.setValue(settings.getReduceFontOn100());

		sliderResizeChargeSymbol.setValue(settings.getResizeChargeSymbolHeight());
		cboxResizeChargeSymbol.setSelected(settings.isResizeChargeSymbol());

		validateControls();
		this.repaint();
	}

	public BattSettings getSettings() {
		settings.setFontColor(fontColor.getColor());
		settings.setFontColorLowBatt(fontColorLowBatt.getColor());
		settings.setFontColorMedBatt(fontColorMedBatt.getColor());
		settings.setFontChargeColor(fontColorCharge.getColor());

		settings.setIconColor(iconColor.getColor());
		settings.setIconColorLowBatt(iconColorLowBatt.getColor());
		settings.setIconColorMedBatt(iconColorMedBatt.getColor());
		settings.setIconColorInActiv(iconColorInactiv.getColor());
		settings.setIconChargeColor(iconColorCharge.getColor());

		settings.setBackgroundColor(backgroundColor.getColor());
		settings.setTransparentBackground(cboxTransparentBgrnd.isSelected());

		settings.setFlip(cboxFlip.isSelected());
		settings.setStrokewidth(sliderStroke.getValue());

		settings.setShowFont(cboxShowFont.isSelected());
		settings.setShowChargeSymbol(cboxShowChargeSymbol.isSelected());
		settings.setColoredFont(cboxColoredFont.isSelected());
		settings.setColoredIcon(cboxColoredIcon.isSelected());

		settings.setMedBattTheshold(sliderMedBatt.getValue());
		settings.setLowBattTheshold(sliderLowBatt.getValue());

		settings.setChargeIcon((ImageIcon) chargeIconSeletor.getSelectedItem());
		settings.setUseGradiantForMediumColor(cboxUseGradientMediumLevels.isSelected());
		settings.setUseGradiantForNormalColor(cboxUseGradientNormalLevels.isSelected());

		settings.setFont(fontButton.getFont());
		settings.setFontXOffset(sliderFontXOffset.getValue());
		settings.setFontYOffset(sliderFontYOffset.getValue());
		settings.setReduceFontOn100(sliderReduceOn100.getValue());
		settings.setIconXOffset(slidericonXOffset.getValue());
		settings.setIconYOffset(slidericonYOffset.getValue());

		settings.setResizeChargeSymbol(cboxResizeChargeSymbol.isSelected());
		settings.setResizeChargeSymbolHeight(sliderResizeChargeSymbol.getValue());

		return settings;
	}

	@Override
	protected void validateControls() {
		fontColor.setEnabled(cboxShowFont.isSelected());
		fontColorMedBatt.setEnabled(cboxShowFont.isSelected() && cboxColoredFont.isSelected());
		fontColorLowBatt.setEnabled(cboxShowFont.isSelected() && cboxColoredFont.isSelected());
		fontColorCharge.setEnabled(cboxShowFont.isSelected());
		cboxColoredFont.setEnabled(cboxShowFont.isSelected());
		iconColorMedBatt.setEnabled(cboxColoredIcon.isSelected());
		iconColorLowBatt.setEnabled(cboxColoredIcon.isSelected());
		chargeIconSeletor.setEnabled(cboxShowChargeSymbol.isSelected());
		fontButton.setEnabled(cboxShowFont.isSelected());
		slidericonXOffset.setEnabled(cboxShowChargeSymbol.isSelected());
		slidericonYOffset.setEnabled(cboxShowChargeSymbol.isSelected());
		sliderFontXOffset.setEnabled(cboxShowFont.isSelected());
		sliderFontYOffset.setEnabled(cboxShowFont.isSelected());
		sliderReduceOn100.setEnabled(cboxShowFont.isSelected());
		sliderResizeChargeSymbol.setEnabled(cboxShowChargeSymbol.isSelected() && cboxResizeChargeSymbol.isSelected());
		cboxResizeChargeSymbol.setEnabled(cboxShowChargeSymbol.isSelected());
		// transparent Backround special behaviour
		backgroundColor.setEnabled(!cboxTransparentBgrnd.isSelected());
		backgroundColor.setVisible(!cboxTransparentBgrnd.isSelected());
		if (!backgroundColor.isEnabled()) {
			backgroundColor.setBackground(Color.black);
		}
	}

	public void enableSupportedFeatures(final boolean supportsFlip, final boolean suppoertsStrokewidth) {
		cboxFlip.setEnabled(supportsFlip);
		sliderStroke.setEnabled(suppoertsStrokewidth);
	}
}
