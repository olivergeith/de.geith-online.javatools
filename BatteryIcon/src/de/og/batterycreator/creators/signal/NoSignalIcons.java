package de.og.batterycreator.creators.signal;

import javax.swing.ImageIcon;

public class NoSignalIcons extends AbstractSignalCreator {

	public static String name = "No Signal Icons";

	public NoSignalIcons() {
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
