package de.og.batterycreator.creatorswifi;

import java.awt.Color;
import java.io.Serializable;

public class WifiSettings implements Serializable {
	private static final long serialVersionUID = -5214326495470879931L;
	public static final String ICON_NAME = "stat_sys_wifi_signal_";
	public static final String ICON_EXTENSION_FULLY = "_fully";

	public static final String ICON_NAME_IN = "stat_sys_wifi_in.png";
	public static final String ICON_NAME_OUT = "stat_sys_wifi_out.png";
	public static final String ICON_NAME_INOUT = "stat_sys_wifi_inout.png";

	private String filePattern = new String(ICON_NAME);
	private String fileEXtensionFully = new String(ICON_EXTENSION_FULLY);

	private String fileIn = new String(ICON_NAME_IN);
	private String fileOut = new String(ICON_NAME_OUT);
	private String fileInOut = new String(ICON_NAME_INOUT);

	private Color inColor = Color.red;
	private Color outColor = Color.green.darker();

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
	 * @return the fileEXtensionFully
	 */
	public String getFileEXtensionFully() {
		return fileEXtensionFully;
	}

	/**
	 * @param fileEXtensionFully
	 *            the fileEXtensionFully to set
	 */
	public void setFileEXtensionFully(final String fileEXtensionFully) {
		this.fileEXtensionFully = fileEXtensionFully;
	}

	/**
	 * @return the fileIn
	 */
	public String getFileIn() {
		return fileIn;
	}

	/**
	 * @param fileIn
	 *            the fileIn to set
	 */
	public void setFileIn(final String fileIn) {
		this.fileIn = fileIn;
	}

	/**
	 * @return the fileOut
	 */
	public String getFileOut() {
		return fileOut;
	}

	/**
	 * @param fileOut
	 *            the fileOut to set
	 */
	public void setFileOut(final String fileOut) {
		this.fileOut = fileOut;
	}

	/**
	 * @return the fileInOut
	 */
	public String getFileInOut() {
		return fileInOut;
	}

	/**
	 * @param fileInOut
	 *            the fileInOut to set
	 */
	public void setFileInOut(final String fileInOut) {
		this.fileInOut = fileInOut;
	}

	/**
	 * @return the inColor
	 */
	public Color getInColor() {
		return inColor;
	}

	/**
	 * @param inColor
	 *            the inColor to set
	 */
	public void setInColor(final Color inColor) {
		this.inColor = inColor;
	}

	/**
	 * @return the outColor
	 */
	public Color getOutColor() {
		return outColor;
	}

	/**
	 * @param outColor
	 *            the outColor to set
	 */
	public void setOutColor(final Color outColor) {
		this.outColor = outColor;
	}

}
