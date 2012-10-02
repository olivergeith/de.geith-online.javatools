/*
 * modul : KPropertyReader.java
 *
 * date : 10.04.2001 10:53
 *
 * Copyright (c) 2000 by
 *
 * Vossloh System-Technik GmbH
 * Edisonstr. 3, D-24145 Kiel, Germany
 *
 * Diese Software ist urheberrechtlich geschützt.
 * All rights reserved.
 *
 * @author  : Oliver Geith
 * @version : 
 *
 */

package og.basics.util;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Diese Klasse bietet ein bißchen Service beim Einlesen von Konfigurationsdaten
 * aus Property-Dateien Typischer Anwendungsfall: Lesen von Key-Value-Daten aus
 * der Datei.
 * <p>
 * Außerdem bietet sie ein gewisses default-handling, indem man als zweiten
 * Paramter einen default-Wert übergibt, der im Fall irgendeines Fehler zurück
 * kommt. Also wenn beispielsweise der Eintrag nicht vorhanden ist, oder eine
 * NumberFormatException beim Lesen auftritt usw...
 * <p>
 * Dadurch reduziert sich das Lesen von irgendwelchen Basistypen auf einen
 * ein/zwei-Zeiler in der eigenen Anwendung
 * <p>
 * Beispiel:
 * <p>
 * {@code KPropertyReader reader = new KPropertyReader("config.properties");}
 * <p>
 * {@code int port = reader.readIntProperty("Port", 12345);}
 * <p>
 * {@code boolean useTCP = reader.readBooleanProperty("useTCP", true);}
 * 
 * @author O.Geith
 * 
 */
public class KPropertyReader {
	// Members
	private String myFile = new String("");
	private final Properties prop = new Properties();

	/**
	 * Konstruktor Falls File nicht existiert, wird es auf wunsch erzeugt, falls
	 * nicht schon vorhanden
	 * 
	 * @param FileName
	 */
	public KPropertyReader(final String FileName, final boolean create) {
		myFile = new String(System.getProperty("user.dir") + File.separator + FileName);
		createFileIfNotExist(create);
	}

	/**
	 * Setzt einen neuen Filename und erzeugt die Datei auf wunsch, falls nicht
	 * vorhanden
	 * 
	 * @param FileName
	 * @param create
	 */
	public void setFileName(final String FileName, final boolean create) {
		myFile = new String(System.getProperty("user.dir") + File.separator + FileName);
		createFileIfNotExist(create);
	}

	/**
	 * Erzeugt die Datei falls nicht vorhanden und erünscht
	 */
	private void createFileIfNotExist(final boolean create) {
		final File file = new File(myFile);
		if (!file.exists() && create)
			try {
				file.createNewFile();
			} catch (final IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Schreibt ein String Property
	 * 
	 * @param pKey
	 * @param pValue
	 */
	public void writeProperty(final String pKey, // Keywert
			final String pValue) // Value des Keys
	{
		// Das alles darf nur gehen, wenn auch ein File angegeben wurde
		if (myFile != null) {
			FileOutputStream outFile;
			FileInputStream inFile;
			try {
				// ....erstmal die Datei einlesen
				inFile = new FileInputStream(myFile);
				prop.load(inFile);
				// ...Propertie setzen // Diese Zeile überschreibt bzw. setzt
				// einen NEUEN Key
				prop.setProperty(pKey, pValue);
				if (inFile != null)
					inFile.close();
				// ...und dann mit geaenderten/neuen Daten speichern
				outFile = new FileOutputStream(myFile);
				prop.store(outFile, "Paramterdaten");
				if (outFile != null)
					outFile.close();
			} catch (final Exception exc) {
				exc.printStackTrace();
			}
		}
	}

	/**
	 * Liest ein String Property
	 * 
	 * @param pKey
	 * @param pDefault
	 * @return
	 */
	public String readProperty(final String pKey, // Keywert
			final String pDefault) // Default, falls er nicht gefunden wird
	{
		// Das alles darf nur gehen, wenn auch ein File angegeben wurde
		if (myFile != null) {
			FileInputStream inFile = null;
			try {
				// ....erstmal die Datei einlesen
				inFile = new FileInputStream(myFile);
				prop.load(inFile);
				if (inFile != null)
					inFile.close();
				// ...Propertie setzen
				return prop.getProperty(pKey, pDefault);
			} catch (final Exception exc) {
				exc.printStackTrace();
				return pDefault;
			}
		} else {
			// Kein File ??? ...dann gibt es default zurück
			return pDefault;
		}
	}

	/**
	 * Schreibt ein boolean Property
	 * 
	 * @param pKey
	 * @param pValue
	 */
	public void writeBooleanProperty(final String pKey, // Keywert
			final boolean pValue) // Value des Keys
	{
		writeProperty(pKey, "" + pValue);
	}

	/**
	 * Liest ein boolean property
	 * 
	 * @param pKey
	 * @param pDefault
	 * @return
	 */
	public boolean readBooleanProperty(final String pKey, // Keywert
			final boolean pDefault) // Default, falls er nicht gefunden wird
	{
		final String boolString = readProperty(pKey, "" + pDefault);
		// Damit es tru ist, muss einer der folgenden Values in der Datei
		// stehen,
		// alles andere ist fals
		if (boolString.trim().compareTo("true") == 0 || boolString.trim().compareTo("1") == 0 || boolString.trim().compareTo("on") == 0)
			return true;
		else
			return false;
	}

	/**
	 * Schreibt ein Doulbe
	 * 
	 * @param pKey
	 * @param pValue
	 */
	public void writeDoubleProperty(final String pKey, // Keywert
			final double pValue) // Value des Keys
	{
		writeProperty(pKey, "" + pValue);
	}

	/**
	 * Liest ein Doulble
	 * 
	 * @param pKey
	 * @param pDefault
	 * @return
	 */
	public double readDoubleProperty(final String pKey, // Keywert
			final double pDefault) // Default, falls er nicht gefunden wird
	{
		final String temp = readProperty(pKey, "" + pDefault);
		double d = pDefault;
		try {
			d = Double.parseDouble(temp.trim());
		} catch (final NumberFormatException e) {
			System.err.println("KPropertyReader " + pKey + "=" + temp + " ist kein gültiger Double");
		}
		return d;
	}

	/**
	 * Schreibt ein Float
	 * 
	 * @param pKey
	 * @param pValue
	 */
	public void writeFloatProperty(final String pKey, // Keywert
			final float pValue) // Value des Keys
	{
		writeProperty(pKey, "" + pValue);
	}

	/**
	 * Liest ein Float
	 * 
	 * @param pKey
	 * @param pDefault
	 * @return
	 */
	public float readFloatProperty(final String pKey, // Keywert
			final float pDefault) // Default, falls er nicht gefunden wird
	{
		final String temp = readProperty(pKey, "" + pDefault);
		float d = pDefault;
		try {
			d = Float.parseFloat(temp.trim());
		} catch (final NumberFormatException e) {
			System.err.println("KPropertyReader " + pKey + "=" + temp + " ist kein gültiger Float");
		}
		return d;
	}

	/**
	 * Schreibt ein int
	 * 
	 * @param pKey
	 * @param pValue
	 */
	public void writeIntProperty(final String pKey, // Keywert
			final int pValue) // Value des Keys
	{
		writeProperty(pKey, "" + pValue);
	}

	/**
	 * Liest ein Int
	 * 
	 * @param pKey
	 * @param pDefault
	 * @return
	 */
	public int readIntProperty(final String pKey, // Keywert
			final int pDefault) // Default, falls er nicht gefunden wird
	{
		final String temp = readProperty(pKey, "" + pDefault);
		int i = pDefault;
		try {
			i = Integer.parseInt(temp.trim());
		} catch (final NumberFormatException e) {
			System.err.println("KPropertyReader " + pKey + "=" + temp + " ist kein gültiger Int");
		}
		return i;
	}

	/**
	 * Schreibt ein Long
	 * 
	 * @param pKey
	 * @param pValue
	 */
	public void writeLongProperty(final String pKey, // Keywert
			final long pValue) // Value des Keys
	{
		writeProperty(pKey, "" + pValue);
	}

	/**
	 * Liest ein Long
	 * 
	 * @param pKey
	 * @param pDefault
	 * @return long
	 */
	public long readLongProperty(final String pKey, // Keywert
			final long pDefault) // Default, falls er nicht gefunden wird
	{
		final String temp = readProperty(pKey, "" + pDefault);
		long l = pDefault;
		try {
			l = Long.parseLong(temp.trim());
		} catch (final NumberFormatException e) {
			System.err.println("KPropertyReader " + pKey + "=" + temp + " ist kein gültiger Long");
		}
		return l;
	}

	/**
	 * Schreibt ein Color
	 * 
	 * @param pKey
	 * @param pValue
	 */
	public void writeColorProperty(final String pKey, // Keywert
			final Color pValue) // Value des Keys
	{
		writeProperty(pKey, pValue.getRed() + "," + pValue.getGreen() + "," + pValue.getBlue());
	}

	/**
	 * Liest ein e Color
	 * 
	 * @param pKey
	 * @param pDefault
	 * @return
	 */
	public Color readColorProperty(final String pKey, // Keywert
			final Color pDefault) // Default, falls er nicht gefunden wird
	{
		int r = 0, g = 0, b = 0;
		final String temp = readProperty(pKey, pDefault.getRed() + "," + pDefault.getGreen() + "," + pDefault.getBlue());
		// Tokenizer initialisieren
		final StringTokenizer tokenizer = new StringTokenizer(temp, ",", false);
		// Daten lesen in einen Vector einlesen
		if (tokenizer.countTokens() != 3)
			return pDefault;
		Color c = pDefault;
		try {
			r = Integer.parseInt(tokenizer.nextToken());
			g = Integer.parseInt(tokenizer.nextToken());
			b = Integer.parseInt(tokenizer.nextToken());
			c = new Color(r, g, b);
		} catch (final NumberFormatException e) {
			System.err.println("KPropertyReader " + pKey + "=" + temp + " ist keine gültige Color");
			return pDefault;
		}
		return c;
	}

	/**
	 * Gibt den Ort des verwendeten Property-Files zurück
	 * 
	 * @return
	 */
	public String getMyPropertyFile() {
		return myFile;
	}

}