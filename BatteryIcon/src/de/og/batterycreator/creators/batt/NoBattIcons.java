package de.og.batterycreator.creators.batt;

import javax.swing.ImageIcon;

public class NoBattIcons extends AbstractIconCreator {
	public static String name = "Don't create Battery Icons";

	public NoBattIcons() {
	}

	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {
		return null;
	}

	@Override
	public String toString() {
		return name;
	}

}
