package de.og.batterycreator.creators.wifi;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class NoWifiIcons extends AbstractWifiCreator {

	public static String name = "No Wifi Icons";

	public NoWifiIcons(final RomSettings romSettings) {
		super(romSettings);
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
