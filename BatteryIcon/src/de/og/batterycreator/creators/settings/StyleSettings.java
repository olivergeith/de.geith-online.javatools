package de.og.batterycreator.creators.settings;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

import javax.swing.ImageIcon;

import de.og.batterycreator.gui.widgets.DrawableComboBox;

public class StyleSettings implements Serializable {
	private static final String LOCKHANDLE_FILENAME_DEFAULT = "ic_lockscreen_handle_normal.png";

	private static final long serialVersionUID = 4747296256398459127L;

	public static final String BATT_ICON_CHARGE_NAME_AOKP = "stat_sys_battery_circle_charge_anim";
	public static final String BATT_ICON_NAME_AOKP = "stat_sys_battery_circle_";
	public static final String BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY = "stat_sys_battery_charge_anim";
	public static final String BATT_ICON_NAME_STOCK_ICS_JKAY = "stat_sys_battery_";

	public static final String FOLDER_SYSTEMUI = "MORPH/system/app/SystemUI.apk/res/";
	public static final String FOLDER_FRAMEWORK = "MORPH/system/framework/framework-res.apk/res/";

	public static final String WIFI_ICON_NAME = "stat_sys_wifi_signal_";
	public static final String WIFI_ICON_EXTENSION_FULLY = "_fully";
	public static final String WIFI_ICON_NAME_IN = "stat_sys_wifi_in.png";
	public static final String WIFI_ICON_NAME_OUT = "stat_sys_wifi_out.png";
	public static final String WIFI_ICON_NAME_INOUT = "stat_sys_wifi_inout.png";

	public static final String SIGNAL_ICON_NAME = "stat_sys_signal_";
	public static final String SIGNAL_ICON_EXTENSION_FULLY = "_fully";
	public static final String SIGNAL_ICON_NAME_IN = "stat_sys_signal_in.png";
	public static final String SIGNAL_ICON_NAME_OUT = "stat_sys_signal_out.png";
	public static final String SIGNAL_ICON_NAME_INOUT = "stat_sys_signal_inout.png";

	public static final String NOTIFICATION_BG_FILENME = "notification_panel_bg.9.png";

	// Konstanten
	public static final Color COLOR_INACTIV = Color.darkGray.brighter();
	public static final Color COLOR_BGRND = Color.black;
	public static final Color COLOR_FONT = Color.white;
	public static final Color COLOR_CHARGE = Color.green.darker();
	public static final Color COLOR_Med_BATT = Color.orange;
	public static final Color COLOR_LOW_BATT = Color.red;
	public static final Color COLOR_AOKP_BLUE = new Color(15, 174, 234);
	public static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 21);

	// Member
	private Color fontColor = COLOR_FONT;
	private Color fontColorLowBatt = COLOR_LOW_BATT;
	private Color fontColorMedBatt = COLOR_Med_BATT;
	private Color fontChargeColor = COLOR_FONT;

	private Color iconColorInActiv = COLOR_INACTIV;
	private Color iconColor = COLOR_AOKP_BLUE;
	private Color iconColorLowBatt = COLOR_LOW_BATT;
	private Color iconColorMedBatt = COLOR_Med_BATT;
	private Color iconChargeColor = COLOR_CHARGE;

	private boolean transparentBackground = true;
	private Color backgroundColor = COLOR_BGRND;

	private boolean showFont = true;
	private boolean coloredFont = false;
	private boolean coloredIcon = true;
	private boolean showChargeSymbol = true;
	private boolean resizeChargeSymbol = true;
	private int resizeChargeSymbolHeight = 24;
	private boolean useGradiantForMediumColor = false;
	private boolean useGradiantForNormalColor = false;
	private boolean flip = false;
	private int strokewidth = 3;

	private int lowBattTheshold = 10;
	private int MedBattTheshold = 30;

	private ImageIcon chargeIcon = null;

	private String filePattern = new String(BATT_ICON_NAME_AOKP);
	private String filePatternCharge = new String(BATT_ICON_CHARGE_NAME_AOKP);

	private String frameworkDrawableFolder = DrawableComboBox.FOLDER_HDPI;
	private String systemUIDrawableFolder = DrawableComboBox.FOLDER_XHDPI;
	private String folderSystemUIInZip = FOLDER_SYSTEMUI + DrawableComboBox.FOLDER_XHDPI + "/";
	private String folderFrameworkInZip = FOLDER_FRAMEWORK + DrawableComboBox.FOLDER_HDPI + "/";
	private int targetIconSize = RomPreset.BATT_ICON_HEIGHT_XHDPI;
	private boolean useAdvancedResize = true;

	// Lockhandle
	private String lockHandleFileName = LOCKHANDLE_FILENAME_DEFAULT;
	private int lockHandleSize = RomPreset.LOCK_HDPI;
	// toggle
	private int toggleSize = RomPreset.TOGGLE_XHDPI;
	// weather
	private int weatherSize = RomPreset.WEATHER_HDPI;

	// font
	private Font font = DEFAULT_FONT;
	private int reduceFontOn100 = -3;

	// font and charge icon offset
	private int iconXOffset = 0;
	private int iconYOffset = 0;
	private int fontXOffset = 0;
	private int fontYOffset = 0;

	// Notification
	private String notificationBGFilename = NOTIFICATION_BG_FILENME;
	private int notificationHeight = RomPreset.NOTIFICATION_XHDPI;
	// Signal Stuff
	private String fileSignalPattern = new String(SIGNAL_ICON_NAME);
	private String fileSignalEXtensionFully = new String(SIGNAL_ICON_EXTENSION_FULLY);
	private String fileSignalIn = new String(SIGNAL_ICON_NAME_IN);
	private String fileSignalOut = new String(SIGNAL_ICON_NAME_OUT);
	private String fileSignalInOut = new String(SIGNAL_ICON_NAME_INOUT);

	// Wifi Stuff
	private String fileWifiPattern = new String(WIFI_ICON_NAME);
	private String fileWifiEXtensionFully = new String(WIFI_ICON_EXTENSION_FULLY);
	private String fileWifiIn = new String(WIFI_ICON_NAME_IN);
	private String fileWifiOut = new String(WIFI_ICON_NAME_OUT);
	private String fileWifiInOut = new String(WIFI_ICON_NAME_INOUT);
	private Color inWifiColor = Color.red;
	private Color outWifiColor = Color.green.darker();
	private Color wifiColor = Color.white;
	private Color wifiColorFully = COLOR_AOKP_BLUE;

	public String getFolderSystemUIInZip() {
		return folderSystemUIInZip;
	}

	public String getFolderFrameworkInZip() {
		return folderFrameworkInZip;
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
	public Color getActivIconColor(final int percentage) {
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
	public Color getActivFontColor(final int percentage) {
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
	public Color getActivIconColor(final int percentage, final boolean charge) {
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
	public Color getActivFontColor(final int percentage, final boolean charge) {
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
	 * @return the systemUIDrawableFolder
	 */
	public String getSystemUIDrawableFolder() {
		return systemUIDrawableFolder;
	}

	/**
	 * @param systemUIDrawableFolder
	 *            the zipOutFolder to set
	 */
	public void setSystemUIDrawableFolder(final String systemUIDrawableFolder) {
		this.systemUIDrawableFolder = systemUIDrawableFolder;
		folderSystemUIInZip = FOLDER_SYSTEMUI + systemUIDrawableFolder + "/";
	}

	/**
	 * @return the frameworkDrawableFolder
	 */
	public String getFrameworkDrawableFolder() {
		return frameworkDrawableFolder;
	}

	/**
	 * @param frameworkDrawableFolder
	 *            the frameworkDrawableFolder to set
	 */
	public void setFrameworkDrawableFolder(final String frameworkDrawableFolder) {
		this.frameworkDrawableFolder = frameworkDrawableFolder;
		folderFrameworkInZip = FOLDER_FRAMEWORK + frameworkDrawableFolder + "/";
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

	/**
	 * @return the strokewidth
	 */
	public int getStrokewidth() {
		return strokewidth;
	}

	/**
	 * @param strokewidth
	 *            the strokewidth to set
	 */
	public void setStrokewidth(final int strokewidth) {
		this.strokewidth = strokewidth;
	}

	/**
	 * @return the flip
	 */
	public boolean isFlip() {
		return flip;
	}

	/**
	 * @param flip
	 *            the flip to set
	 */
	public void setFlip(final boolean flip) {
		this.flip = flip;
	}

	/**
	 * @return the reduceFontOn100
	 */
	public int getReduceFontOn100() {
		return reduceFontOn100;
	}

	/**
	 * @param reduceFontOn100
	 *            the reduceFontOn100 to set
	 */
	public void setReduceFontOn100(final int reduceFontOn100) {
		this.reduceFontOn100 = reduceFontOn100;
	}

	/**
	 * @return the iconXOffset
	 */
	public int getIconXOffset() {
		return iconXOffset;
	}

	/**
	 * @param iconXOffset
	 *            the iconXOffset to set
	 */
	public void setIconXOffset(final int iconXOffset) {
		this.iconXOffset = iconXOffset;
	}

	/**
	 * @return the iconYOffset
	 */
	public int getIconYOffset() {
		return iconYOffset;
	}

	/**
	 * @param iconYOffset
	 *            the iconYOffset to set
	 */
	public void setIconYOffset(final int iconYOffset) {
		this.iconYOffset = iconYOffset;
	}

	/**
	 * @return the resizeChargeSymbol
	 */
	public boolean isResizeChargeSymbol() {
		return resizeChargeSymbol;
	}

	/**
	 * @param resizeChargeSymbol
	 *            the resizeChargeSymbol to set
	 */
	public void setResizeChargeSymbol(final boolean resizeChargeSymbol) {
		this.resizeChargeSymbol = resizeChargeSymbol;
	}

	/**
	 * @return the resizeChargeSymbolHeight
	 */
	public int getResizeChargeSymbolHeight() {
		return resizeChargeSymbolHeight;
	}

	/**
	 * @param resizeChargeSymbolHeight
	 *            the resizeChargeSymbolHeight to set
	 */
	public void setResizeChargeSymbolHeight(final int resizeChargeSymbolHeight) {
		this.resizeChargeSymbolHeight = resizeChargeSymbolHeight;
	}

	/**
	 * @return the filePattern
	 */
	public String getFileWifiPattern() {
		return fileWifiPattern;
	}

	/**
	 * @param filePattern
	 *            the filePattern to set
	 */
	public void setFileWifiPattern(final String filePattern) {
		fileWifiPattern = filePattern;
	}

	/**
	 * @return the fileEXtensionFully
	 */
	public String getFileWifiEXtensionFully() {
		return fileWifiEXtensionFully;
	}

	/**
	 * @param fileEXtensionFully
	 *            the fileEXtensionFully to set
	 */
	public void setFileWifiEXtensionFully(final String fileEXtensionFully) {
		fileWifiEXtensionFully = fileEXtensionFully;
	}

	/**
	 * @return the fileIn
	 */
	public String getFileWifiIn() {
		return fileWifiIn;
	}

	/**
	 * @param fileIn
	 *            the fileIn to set
	 */
	public void setFileWifiIn(final String fileIn) {
		fileWifiIn = fileIn;
	}

	/**
	 * @return the fileOut
	 */
	public String getFileWifiOut() {
		return fileWifiOut;
	}

	/**
	 * @param fileOut
	 *            the fileOut to set
	 */
	public void setFileWifiOut(final String fileOut) {
		fileWifiOut = fileOut;
	}

	/**
	 * @return the fileInOut
	 */
	public String getFileWifiInOut() {
		return fileWifiInOut;
	}

	/**
	 * @param fileInOut
	 *            the fileInOut to set
	 */
	public void setFileWifiInOut(final String fileInOut) {
		fileWifiInOut = fileInOut;
	}

	/**
	 * @return the inColor
	 */
	public Color getInWifiColor() {
		return inWifiColor;
	}

	/**
	 * @param inColor
	 *            the inColor to set
	 */
	public void setInWifiColor(final Color inColor) {
		inWifiColor = inColor;
	}

	/**
	 * @return the outColor
	 */
	public Color getOutWifiColor() {
		return outWifiColor;
	}

	/**
	 * @param outColor
	 *            the outColor to set
	 */
	public void setOutWifiColor(final Color outColor) {
		outWifiColor = outColor;
	}

	/**
	 * @return the wifiColor
	 */
	public Color getWifiColor() {
		return wifiColor;
	}

	/**
	 * @param wifiColor
	 *            the wifiColor to set
	 */
	public void setWifiColor(final Color wifiColor) {
		this.wifiColor = wifiColor;
	}

	/**
	 * @return the wifiColorFully
	 */
	public Color getWifiColorFully() {
		return wifiColorFully;
	}

	/**
	 * @param wifiColorFully
	 *            the wifiColorFully to set
	 */
	public void setWifiColorFully(final Color wifiColorFully) {
		this.wifiColorFully = wifiColorFully;
	}

	/**
	 * @return the lockHandleFileName
	 */
	public String getLockHandleFileName() {
		return lockHandleFileName;
	}

	/**
	 * @param lockHandleFileName
	 *            the lockHandleFileName to set
	 */
	public void setLockHandleFileName(final String lockHandleFileName) {
		this.lockHandleFileName = lockHandleFileName;
	}

	/**
	 * @return the lockHandleSize
	 */
	public int getLockHandleSize() {
		return lockHandleSize;
	}

	/**
	 * @param lockHandleSize
	 *            the lockHandleSize to set
	 */
	public void setLockHandleSize(final int lockHandleSize) {
		this.lockHandleSize = lockHandleSize;
	}

	/**
	 * @return the transparentBackground
	 */
	public boolean isTransparentBackground() {
		return transparentBackground;
	}

	/**
	 * @param transparentBackground
	 *            the transparentBackground to set
	 */
	public void setTransparentBackground(final boolean transparentBackground) {
		this.transparentBackground = transparentBackground;
	}

	/**
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor() {
		// if (!isTransparentBackground())
		return backgroundColor;
		// else
		// return Color.black;
	}

	/**
	 * @param backgroundColor
	 *            the backgroundColor to set
	 */
	public void setBackgroundColor(final Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return the fileSignalInOut
	 */
	public String getFileSignalInOut() {
		return fileSignalInOut;
	}

	/**
	 * @param fileSignalInOut
	 *            the fileSignalInOut to set
	 */
	public void setFileSignalInOut(final String fileSignalInOut) {
		this.fileSignalInOut = fileSignalInOut;
	}

	/**
	 * @return the fileSignalOut
	 */
	public String getFileSignalOut() {
		return fileSignalOut;
	}

	/**
	 * @param fileSignalOut
	 *            the fileSignalOut to set
	 */
	public void setFileSignalOut(final String fileSignalOut) {
		this.fileSignalOut = fileSignalOut;
	}

	/**
	 * @return the fileSignalIn
	 */
	public String getFileSignalIn() {
		return fileSignalIn;
	}

	/**
	 * @param fileSignalIn
	 *            the fileSignalIn to set
	 */
	public void setFileSignalIn(final String fileSignalIn) {
		this.fileSignalIn = fileSignalIn;
	}

	/**
	 * @return the fileSignalEXtensionFully
	 */
	public String getFileSignalEXtensionFully() {
		return fileSignalEXtensionFully;
	}

	/**
	 * @param fileSignalEXtensionFully
	 *            the fileSignalEXtensionFully to set
	 */
	public void setFileSignalEXtensionFully(final String fileSignalEXtensionFully) {
		this.fileSignalEXtensionFully = fileSignalEXtensionFully;
	}

	/**
	 * @return the fileSignalPattern
	 */
	public String getFileSignalPattern() {
		return fileSignalPattern;
	}

	/**
	 * @param fileSignalPattern
	 *            the fileSignalPattern to set
	 */
	public void setFileSignalPattern(final String fileSignalPattern) {
		this.fileSignalPattern = fileSignalPattern;
	}

	/**
	 * @return the notificationBGFilename
	 */
	public String getNotificationBGFilename() {
		return notificationBGFilename;
	}

	/**
	 * @param notificationBGFilename
	 *            the notificationBGFilename to set
	 */
	public void setNotificationBGFilename(final String notificationBGFilename) {
		this.notificationBGFilename = notificationBGFilename;
	}

	/**
	 * @return the notificationHeight
	 */
	public int getNotificationHeight() {
		return notificationHeight;
	}

	/**
	 * @param notificationHeight
	 *            the notificationHeight to set
	 */
	public void setNotificationHeight(final int notificationHeight) {
		this.notificationHeight = notificationHeight;
	}

	/**
	 * @return the toggleSize
	 */
	public int getToggleSize() {
		return toggleSize;
	}

	/**
	 * @param toggleSize
	 *            the toggleSize to set
	 */
	public void setToggleSize(final int toggleSize) {
		this.toggleSize = toggleSize;
	}

	/**
	 * @return the weatherSize
	 */
	public int getWeatherSize() {
		return weatherSize;
	}

	/**
	 * @param weatherSize
	 *            the weatherSize to set
	 */
	public void setWeatherSize(final int weatherSize) {
		this.weatherSize = weatherSize;
	}

}
