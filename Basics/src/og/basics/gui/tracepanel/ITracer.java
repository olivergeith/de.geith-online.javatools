package og.basics.gui.tracepanel;

/**
 * Interface wird vom TracePanel implementiert und versteckt die sonstigen
 * Methoden des Tracepanels
 * 
 * @author geith
 * 
 */
public interface ITracer {
	/**
	 * Text anfügen
	 * 
	 * @param myText
	 */
	public void appendText(final String myText);

	public void appendWarningText(final String myText);

	public void appendInfoText(final String myText);

	public void appendErrorText(final String myText);

	public void appendSuccessText(final String myText);

	/**
	 * Text setzen und alles überschreiben, was vorher da war!
	 * 
	 * @param myText
	 */
	public void setText(final String myText);

	/**
	 * Löscht den Fensterinhalt
	 */
	public void clear();

}
