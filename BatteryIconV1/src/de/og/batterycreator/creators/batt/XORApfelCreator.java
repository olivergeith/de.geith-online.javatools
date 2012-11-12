package de.og.batterycreator.creators.batt;

import javax.swing.ImageIcon;

public class XORApfelCreator extends AbstractIconXORCreator {

	protected static String name = "XApfelBattery";
	public static final ImageIcon myIcon = new ImageIcon(AbstractIconXORCreator.class.getResource("apple.png"));

	public XORApfelCreator() {
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	protected ImageIcon getXORIcon() {
		return myIcon;
	}

}
