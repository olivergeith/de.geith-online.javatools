package og.basics.util;

public class StringHelper {

	/**
	 * Füllt den text rechts mit Leerzeichen auf
	 * 
	 * @param text
	 * @param length
	 * @return
	 */
	public static String fillUpWithBlanksRight(final String text, final int length) {
		return fillRight(text, ' ', length);
	}

	/**
	 * Füllt den text links mit Leerzeichen auf
	 * 
	 * @param text
	 * @param length
	 * @return
	 */
	public static String fillUpWithBlanksLeft(final String text, final int length) {
		return fillLeft(text, ' ', length);
	}

	/**
	 * Füllt den text rechts mit "char c" auf
	 * 
	 * @param text
	 * @param length
	 * @return
	 */
	public static String fillRight(String text, final char c, final int length) {
		while (text.length() < length) {
			text = text + c;
		}
		return text;
	}

	/**
	 * Füllt den text links mit "char c" auf
	 * 
	 * @param text
	 * @param length
	 * @return
	 */
	public static String fillLeft(String text, final char c, final int length) {
		while (text.length() < length) {
			text = c + text;
		}
		return text;
	}

}
