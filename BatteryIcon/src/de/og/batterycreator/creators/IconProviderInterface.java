package de.og.batterycreator.creators;

import java.util.Vector;

public interface IconProviderInterface {

	public String getProviderName();

	public Vector<String> getAllFilenamesAndPath();

	public boolean isActiv();

}
