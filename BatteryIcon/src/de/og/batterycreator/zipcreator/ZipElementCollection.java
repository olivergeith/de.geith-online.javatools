package de.og.batterycreator.zipcreator;

import java.util.Vector;

public class ZipElementCollection {

	final Vector<ZipElement> zipelEments = new Vector<ZipElement>();

	public ZipElementCollection() {
	}

	public void addElements(final Vector<String> elements, final String pathInArchiv) {

		if (elements != null && pathInArchiv != null && pathInArchiv.length() > 0) {
			for (final String ele : elements) {
				zipelEments.add(new ZipElement(ele, pathInArchiv));
			}
		}
	}

	public Vector<ZipElement> getZipelEments() {
		return zipelEments;
	}

}
