package de.og.batterycreator.creators;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class StyleSettings implements Serializable {
	private static final long serialVersionUID = 4747296256398459127L;

	public static final String FOLDER_SYSTEMUI = "MORPH/system/app/SystemUI.apk/res/";
	public static final String FOLDER_XHDPI = "drawable-xhdpi";
	public static final String FOLDER_HDPI = "drawable-hdpi";
	public static final String FOLDER_720DP = "drawable-sw720dp-xhdpi";
	public static final String FOLDER_600DP = "drawable-sw600dp-xhdpi";

	public static final int ICON_HEIGHT_XHDPI = 38;
	public static final int ICON_HEIGHT_HDPI = 27;
	public static final int ICON_HEIGHT_720DP = 48;
	public static final int ICON_HEIGHT_600DP = 43;

	public static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 21);

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
	private Color fontChargeColor = COLOR_FONT;

	private Color iconColorInActiv = Color.darkGray;
	private Color iconColor = AOKP_BLUE;
	private Color iconColorLowBatt = COLOR_LOW_BATT;
	private Color iconColorMedBatt = COLOR_Med_BATT;
	private Color iconChargeColor = COLOR_CHARGE;

	private boolean showFont = true;
	private boolean coloredFont = false;
	private boolean coloredIcon = true;
	private boolean showChargeSymbol = true;
	private boolean useGradiantForMediumColor = false;
	private boolean useGradiantForNormalColor = false;

	private int lowBattTheshold = 10;
	private int MedBattTheshold = 30;

	private ImageIcon chargeIcon = null;

	private String filePattern = new String("stat_sys_battery_circle_");
	private String filePatternCharge = new String("stat_sys_battery_circle_charge_anim");

	private String zipResolutionFolder = FOLDER_XHDPI;
	private String folderWithinZip = FOLDER_SYSTEMUI + FOLDER_XHDPI + File.separator;

	private int targetIconSize = ICON_HEIGHT_XHDPI;
	private boolean useAdvancedResize = false;

	private Font font = DEFAULT_FONT;

	private int fontXOffset = 0;
	private int fontYOffset = 0;

	public String getFolderWithinZip() {
		return folderWithinZip;
	}

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

	public Color getIconChargeColor() {
		return iconChargeColor;
	}

	public void setIconChargeColor(final Color iconChargeColor) {
		this.iconChargeColor = iconChargeColor;
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
		if (!isColoredIcon()) {
			col = getIconColor();
		} else {
			// Wenn oberhalb der Schwelle oder immer einfarbig gezeichnet werden
			// soll, dann...
			if (percentage >= getMedBattTheshold()) {
				if (useGradiantForNormalColor)
					col = getRadiantColor(getIconColor(), getIconColorMedBatt(), percentage, 100, getMedBattTheshold());
				else
					col = getIconColor();
			} else if (percentage < getMedBattTheshold() && percentage >= getLowBattTheshold()) {
				if (useGradiantForMediumColor)
					col = getRadiantColor(getIconColorLowBatt(), getIconColorMedBatt(), percentage, getLowBattTheshold(), getMedBattTheshold());
				else
					col = getIconColorMedBatt();
			} else {
				col = getIconColorLowBatt();
			}
		}
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
		if (!isColoredFont()) {
			col = getFontColor();
		} else {
			if (percentage >= getMedBattTheshold()) {
				if (useGradiantForNormalColor)
					col = getRadiantColor(getFontColor(), getFontColorMedBatt(), percentage, 100, getMedBattTheshold());
				else
					col = getFontColor();
			} else if (percentage < getMedBattTheshold() && percentage >= getLowBattTheshold()) {
				if (useGradiantForMediumColor)
					col = getRadiantColor(getFontColorLowBatt(), getFontColorMedBatt(), percentage, getLowBattTheshold(), getMedBattTheshold());
				else
					col = getFontColorMedBatt();
			} else
				col = getFontColorLowBatt();
		}
		return col;
	}

	private Color getRadiantColor(final Color col1, final Color col2, final int percentage, final int min, final int max) {
		final int diff = min - max;
		final int diffpercent = min - percentage;
		final float factor = Math.abs((float) diffpercent / (float) diff);

		final int diffr = col1.getRed() - col2.getRed();
		final int diffg = col1.getGreen() - col2.getGreen();
		final int diffb = col1.getBlue() - col2.getBlue();

		final int r = Math.round(col1.getRed() - diffr * factor);
		final int g = Math.round(col1.getGreen() - diffg * factor);
		final int b = Math.round(col1.getBlue() - diffb * factor);

		final Color col = new Color(r, g, b);
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
			col = getIconChargeColor();
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
			col = getFontChargeColor();
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

	/**
	 * @return the fontChargeColor
	 */
	public Color getFontChargeColor() {
		return fontChargeColor;
	}

	/**
	 * @param fontChargeColor
	 *            the fontChargeColor to set
	 */
	public void setFontChargeColor(final Color fontChargeColor) {
		this.fontChargeColor = fontChargeColor;
	}

	/**
	 * @return the chargeIcon
	 */
	public ImageIcon getChargeIcon() {
		return chargeIcon;
	}

	/**
	 * @param chargeIcon
	 *            the chargeIcon to set
	 */
	public void setChargeIcon(final ImageIcon chargeIcon) {
		this.chargeIcon = chargeIcon;
	}

	/**
	 * @return the filePattern
	 */
	public String getFilePattern() {
		return filePattern;
	}

	/**
	 * @param filePattern
	 *            the filePattern to set
	 */
	public void setFilePattern(final String filePattern) {
		this.filePattern = filePattern;
	}

	/**
	 * @return the filePatternCharge
	 */
	public String getFilePatternCharge() {
		return filePatternCharge;
	}

	/**
	 * @param filePatternCharge
	 *            the filePatternCharge to set
	 */
	public void setFilePatternCharge(final String filePatternCharge) {
		this.filePatternCharge = filePatternCharge;
	}

	/**
	 * @return the zipOutFolder
	 */
	public String getZipResolutionFolder() {
		return zipResolutionFolder;
	}

	/**
	 * @param zipResolutionFolder
	 *            the zipOutFolder to set
	 */
	public void setZipResolutionFolder(final String zipResolutionFolder) {
		this.zipResolutionFolder = zipResolutionFolder;
		folderWithinZip = FOLDER_SYSTEMUI + zipResolutionFolder + "/";

	}

	/**
	 * @return the iconSize
	 */
	public int getTargetIconSize() {
		return targetIconSize;
	}

	/**
	 * @param targetIconSize
	 *            the iconSize to set
	 */
	public void setTargetIconSize(final int targetIconSize) {
		this.targetIconSize = targetIconSize;
	}

	/**
	 * @return the useAdvancedResize
	 */
	public boolean isUseAdvancedResize() {
		return useAdvancedResize;
	}

	/**
	 * @param useAdvancedResize
	 *            the useAdvancedResize to set
	 */
	public void setUseAdvancedResize(final boolean useAdvancedResize) {
		this.useAdvancedResize = useAdvancedResize;
	}

	/**
	 * @return the useGradiantForNormalColor
	 */
	public boolean isUseGradiantForNormalColor() {
		return useGradiantForNormalColor;
	}

	/**
	 * @param useGradiantForNormalColor
	 *            the useGradiantForNormalColor to set
	 */
	public void setUseGradiantForNormalColor(final boolean useGradiantForNormalColor) {
		this.useGradiantForNormalColor = useGradiantForNormalColor;
	}

	public boolean isUseGradiantForMediumColor() {
		return useGradiantForMediumColor;
	}

	public void setUseGradiantForMediumColor(final boolean useGradiantForMediumColor) {
		this.useGradiantForMediumColor = useGradiantForMediumColor;
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font
	 *            the font to set
	 */
	public void setFont(final Font font) {
		this.font = font;
	}

	/**
	 * @return the fontYOffset
	 */
	public int getFontYOffset() {
		return fontYOffset;
	}

	/**
	 * @param fontYOffset
	 *            the fontYOffset to set
	 */
	public void setFontYOffset(final int fontYOffset) {
		this.fontYOffset = fontYOffset;
	}

	/**
	 * @return the fontXOffset
	 */
	public int getFontXOffset() {
		return fontXOffset;
	}

	/**
	 * @param fontXOffset
	 *            the fontXOffset to set
	 */
	public void setFontXOffset(final int fontXOffset) {
		this.fontXOffset = fontXOffset;
	}

}
