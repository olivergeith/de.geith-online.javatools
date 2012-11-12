package de.og.batterycreator.creators.signal;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class NoSignalIcons extends AbstractSignalCreator {

	public static String name = "No Signal Icons";

	public NoSignalIcons(final RomSettings romSettings) {
		super(romSettings);
	}

	@Override
	public ImageIcon createImage(final int level, final boolean fully) {
		return null;
	}

	@Override
	public ImageIcon createInOutImage(final boolean in, final boolean out) {
		return null;
	}

	@Override
	public String toString() {
		return name;
	}

}
