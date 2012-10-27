package de.og.batterycreator.creators;

import java.util.Vector;

public interface IconProviderInterface {

	public String getProviderName();

	public Vector<String> getAllFilenamesAndPath();

	// public void createAllImages();

	public void createAllImages(final int size);

	public boolean isActiv();

}
