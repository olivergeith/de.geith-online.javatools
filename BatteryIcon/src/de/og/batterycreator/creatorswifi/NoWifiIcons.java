package de.og.batterycreator.creatorswifi;

import javax.swing.ImageIcon;

public class NoWifiIcons extends AbstractWifiCreator {

	public static String name = "Don't create Wifi Icons";

	public NoWifiIcons() {
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public ImageIcon createImage(final int level, final boolean fully) {
		return null;
	}

	@Override
	public ImageIcon createInOutImage(final boolean in, final boolean out) {
		return null;
	}

}
