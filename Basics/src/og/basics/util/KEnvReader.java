package og.basics.util;

/**
 * @author Geith Klasse zum lesen von Umgebungsvariablen ...:-) alles static,
 *         aber als Service braucht man sich nicht um das default-handling und
 *         exception-handling kümmern ;-)
 */
public class KEnvReader {

	/**
	 * Liest einen String aus dem Environment
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String readStringEnvVariable(final String key, final String defaultValue) {
		String val = null;
		try {
			// hier kommt null zurück, wenn die Variable nicht gesetzt ist
			// oder es gibt eine Security-Exception
			val = System.getenv(key);
		} catch (final Exception e) {
			System.out.println("Exception beim Lesen der Umgebungsvariable " + key);
		}
		// Wenn val jetzt einen Vert ungleich null hat, dann ist alles prima
		if (val != null)
			return val;
		// wenn nicht, default zurück!
		return defaultValue;
	}

	/**
	 * Liest einen Int aus dem Environment
	 * 
	 * @param key
	 * @param pDefault
	 * @return
	 */
	public static int readIntEnvVariable(final String key, final int pDefault) {
		final String val = readStringEnvVariable(key, "" + pDefault);
		int value = pDefault;
		try {
			value = Integer.parseInt(val);
		} catch (final Exception e) {
			System.out.println("Exception beim Int-Parsen der Umgebungsvariable " + key + " = " + val);
		}
		return value;
	}

	/**
	 * Liest ein Boolean aus dem Environment
	 * 
	 * @param key
	 * @param pDefault
	 * @return
	 */
	public boolean readBooleanEnvVariable(final String key, final boolean pDefault) {
		final String boolString = readStringEnvVariable(key, "" + pDefault);
		// Damit es true ist, muss einer der folgenden Values in der Umgebung
		// stehen,
		// alles andere ist false
		if (boolString.trim().compareTo("true") == 0 || boolString.trim().compareTo("1") == 0 || boolString.trim().compareTo("on") == 0)
			return true;
		else
			return false;
	}

}
