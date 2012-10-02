package de.og.batterycreator.creators;

import java.awt.Color;

public class StyleSettings {
	// Konstanten
	public static final Color COLOR_FONT = Color.white;
	public static final Color COLOR_CHARGE = Color.green.darker();
	public static final Color COLOR_Med_BATT = Color.orange;
	public static final Color COLOR_LOW_BATT = Color.red;
	public static final Color AOKP_BLUE = new Color(15, 174, 234);
	// Member
	private Color fontColor = COLOR_FONT;
	private Color fontColorLowBatt = COLOR_LOW_BATT;
	private Color fontColorMedBatt = COLOR_Med_BATT;
	private Color iconColorInActiv = Color.darkGray;
	private Color iconColor = AOKP_BLUE;
	private Color iconColorLowBatt = COLOR_LOW_BATT;
	private Color iconColorMedBatt = COLOR_Med_BATT;
	private Color chargeColor = COLOR_CHARGE;

	private boolean showFont = true;
	private boolean coloredFont = false;
	private boolean coloredIcon = true;
	private boolean showChargeSymbol = false;

	private int lowBattTheshold = 10;
	private int MedBattTheshold = 30;

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontColor(final Color fontColor) {
		this.fontColor = fontColor;
	}

	public Color getFontColorLowBatt() {
		return fontColorLowBatt;
	}

	public void setFontColorLowBatt(final Color fontColorLowBatt) {
		this.fontColorLowBatt = fontColorLowBatt;
	}

	public Color getFontColorMedBatt() {
		return fontColorMedBatt;
	}

	public void setFontColorMedBatt(final Color fontColorMedBatt) {
		this.fontColorMedBatt = fontColorMedBatt;
	}

	public Color getIconColor() {
		return iconColor;
	}

	public void setIconColor(final Color iconColor) {
		this.iconColor = iconColor;
	}

	public Color getIconColorLowBatt() {
		return iconColorLowBatt;
	}

	public void setIconColorLowBatt(final Color iconColorLowBatt) {
		this.iconColorLowBatt = iconColorLowBatt;
	}

	public Color getIconColorMedBatt() {
		return iconColorMedBatt;
	}

	public void setIconColorMedBatt(final Color iconColorMedBatt) {
		this.iconColorMedBatt = iconColorMedBatt;
	}

	public Color getChargeColor() {
		return chargeColor;
	}

	public void setChargeColor(final Color chargeColor) {
		this.chargeColor = chargeColor;
	}

	public int getLowBattTheshold() {
		return lowBattTheshold;
	}

	public void setLowBattTheshold(final int lowBattTheshold) {
		this.lowBattTheshold = lowBattTheshold;
	}

	public int getMedBattTheshold() {
		return MedBattTheshold;
	}

	public void setMedBattTheshold(final int MedBattTheshold) {
		this.MedBattTheshold = MedBattTheshold;
	}

	public Color getIconColorInActiv() {
		return iconColorInActiv;
	}

	public void setIconColorInActiv(final Color iconColorInActiv) {
		this.iconColorInActiv = iconColorInActiv;
	}

	/**
	 * Returns the Color for a Percentage
	 * 
	 * @param percentage
	 * @return
	 */
	protected Color getActivIconColor(final int percentage) {
		Color col;
		// Wenn oberhalb der Schwelle oder immer einfarbig gezeichnet werden
		// soll, dann...
		if (percentage >= getMedBattTheshold() || isColoredIcon() == false)
			col = getIconColor();
		else if (percentage < getMedBattTheshold() && percentage >= getLowBattTheshold())
			col = getIconColorMedBatt();
		else
			col = getIconColorLowBatt();
		return col;
	}

	/**
	 * Returns the Color for a Percentage
	 * 
	 * @param percentage
	 * @return
	 */
	protected Color getActivFontColor(final int percentage) {
		Color col;
		// Wenn oberhalb der Schwelle oder immer einfarbig gezeichnet werden
		// soll, dann...
		if (percentage >= getMedBattTheshold() || isColoredFont() == false)
			col = getFontColor();
		else if (percentage < getMedBattTheshold() && percentage >= getLowBattTheshold())
			col = getFontColorMedBatt();
		else
			col = getFontColorLowBatt();
		return col;
	}

	/**
	 * Returns the Activ Color for percentage, or Chargecolor
	 * 
	 * @param percentage
	 * @param charge
	 * @return
	 */
	protected Color getActivIconColor(final int percentage, final boolean charge) {
		Color col;
		if (charge)
			col = getChargeColor();
		else
			col = getActivIconColor(percentage);
		return col;
	}

	/**
	 * Returns the Activ Color for percentage, or Chargecolor
	 * 
	 * @param percentage
	 * @param charge
	 * @return
	 */
	protected Color getActivFontColor(final int percentage, final boolean charge) {
		Color col;
		if (charge)
			col = getChargeColor();
		else
			col = getActivFontColor(percentage);
		return col;
	}

	/**
	 * @return the useColoredFont
	 */
	public boolean isColoredFont() {
		return coloredFont;
	}

	/**
	 * @param useColoredFont
	 *            the useColoredFont to set
	 */
	public void setColoredFont(final boolean useColoredFont) {
		coloredFont = useColoredFont;
	}

	/**
	 * @return the coloredIcon
	 */
	public boolean isColoredIcon() {
		return coloredIcon;
	}

	/**
	 * @param coloredIcon
	 *            the coloredIcon to set
	 */
	public void setColoredIcon(final boolean coloredIcon) {
		this.coloredIcon = coloredIcon;
	}

	/**
	 * @return the showFont
	 */
	public boolean isShowFont() {
		return showFont;
	}

	/**
	 * @param showFont
	 *            the showFont to set
	 */
	public void setShowFont(final boolean showFont) {
		this.showFont = showFont;
	}

	/**
	 * @return the showChargeSymbol
	 */
	public boolean isShowChargeSymbol() {
		return showChargeSymbol;
	}

	/**
	 * @param showChargeSymbol
	 *            the showChargeSymbol to set
	 */
	public void setShowChargeSymbol(final boolean showChargeSymbol) {
		this.showChargeSymbol = showChargeSymbol;
	}

}
