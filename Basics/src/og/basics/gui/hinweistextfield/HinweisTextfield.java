package og.basics.gui.hinweistextfield;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Diese Klasse ist ein JTextField mit einem Hinweistext, wenn es leer ist.
 * Außerdem hat sie einen Clearbutton am rechten rand, mit dem der Inhalt
 * gelöscht werden kann.
 * 
 * @author Oliver
 * 
 */
public class HinweisTextfield extends JTextField {
	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_HINWEIS = "<Text eingeben>";
	/**
	 * Der Hinweistext, wenn das Feld leer ist
	 */
	private String hinweisTextWhenEmpty = DEFAULT_HINWEIS;

	private static final Color DEFAULT_COLOR_EMPTY = Color.GRAY;
	/**
	 * The Color of the Hinweis-String, when Textfield is empty
	 */
	private Color emptyTextColor = DEFAULT_COLOR_EMPTY;

	private final ImageIcon DEFAULT_CLEAR_ICON = new ImageIcon(this.getClass().getResource("edit-clear.png"));
	private final ImageIcon DEFAULT_CLEAR_ICON_ROLLOVER = new ImageIcon(this.getClass().getResource("edit-clear-activ.png"));
	private final ImageIcon DEFAULT_INAKTIV_ICON = new ImageIcon(this.getClass().getResource("inaktiv.png"));
	private ImageIcon clearIcon = DEFAULT_CLEAR_ICON;
	private ImageIcon clearIconRollOver = DEFAULT_CLEAR_ICON_ROLLOVER;
	private ImageIcon inactivIcon = DEFAULT_INAKTIV_ICON;

	/**
	 * Hier wird ein JLabel als Button missbraucht :-), weil ein Button u.U. zu
	 * gross ist.
	 */
	final JLabel clearButton = new JLabel(inactivIcon);

	/**
	 * Konstruktor
	 */
	public HinweisTextfield() {
		super();
		myInit(hinweisTextWhenEmpty);
	}

	/**
	 * Konstruktor
	 * 
	 * @param hinweis
	 */
	public HinweisTextfield(final String hinweis) {
		super();
		myInit(hinweis);
	}

	/**
	 * Konstruktor
	 * 
	 * @param text
	 * @param hinweis
	 */
	public HinweisTextfield(final String text, final String hinweis) {
		super(text);
		myInit(hinweis);
	}

	/**
	 * Gui Init
	 * 
	 * @param hinweis
	 */
	/**
	 * @param hinweis
	 */
	private void myInit(final String hinweis) {
		hinweisTextWhenEmpty = hinweis;
		if (this.getText().length() == 0)
			handleEmptyField();
		else
			activateField();

		initClearButton();

		this.add(clearButton);

		// managed das Resizen des Textfields, damit der clearbutton immer
		// rechts bleibt und nicht überschrieben wird
		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 29 + 5));
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(final ComponentEvent e) {
				final Rectangle r = getBounds();
				clearButton.setBounds(r.width - 24 - 5, 0, 24, r.height);
			}
		});

		// FocusListener, damit das Suchfeld bei leerem Inhalt mit Hinweisstring
		// gefüllt wird
		addFocusListener(new FocusListener() {
			public void focusLost(final FocusEvent arg0) {
				// Hinweis anzeigen
				if (getText().length() == 0) {
					handleEmptyField();
				}
			}

			public void focusGained(final FocusEvent arg0) {
				// Hinweis löschen bei erhalt vom Fokus
				if (isEmpty()) {
					handleStartingInput();
				} else {
					// oder selektieren (zum schnelleren überschreiben)
					getCaret().setSelectionVisible(true);
					selectAll();
				}
			}

		});
		// Keylistener, damit das Feld den Hinweis entfernt, wenn was eingetippt
		// wird
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				if (isEmpty()) {
					handleStartingInput();
				}
			}
		});
	}

	/**
	 * Initialisiert den Clearbutton mit Mouselistener
	 */
	private void initClearButton() {
		clearButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(final MouseEvent e) {
				if (clearButton.isEnabled())
					clearButton.setIcon(clearIcon);
			}

			@Override
			public void mouseEntered(final MouseEvent e) {
				if (clearButton.isEnabled())
					clearButton.setIcon(clearIconRollOver);
				// Cursor auf Pfeil setzen solange wir über button sind
				clearButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseClicked(final MouseEvent arg0) {
				setText("");
			}
		});
	}

	/**
	 * Was passiert bei Inputbegin
	 */
	private void handleStartingInput() {
		super.setText("");
		activateField();
	}

	private void activateField() {
		setForeground(Color.BLACK);
		clearButton.setIcon(clearIcon);
		clearButton.setEnabled(true);
	}

	/**
	 * 
	 */
	private void handleEmptyField() {
		setForeground(emptyTextColor);
		super.setText(hinweisTextWhenEmpty);
		clearButton.setIcon(inactivIcon);
		clearButton.setEnabled(false);
	}

	// Public Methoden

	/**
	 * Gibt den Hinweistext zurück
	 * 
	 * @return
	 */
	public String getHinweis() {
		return hinweisTextWhenEmpty;
	}

	/**
	 * Sagt, ob das Feld leer ist (es ist ja nicht wirklich leer, sondern es
	 * wird der Hinweistext angezeigt)
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		if (getText().equals(hinweisTextWhenEmpty))
			return true;
		return false;
	}

	/**
	 * holt die Hinweistextfarbe
	 * 
	 * @return
	 */
	public Color getEmptyTextColor() {
		return emptyTextColor;
	}

	/**
	 * Setzt die Farbe, in der der Hinweistext angezeigt werden soll, wenn das
	 * Feld leer ist
	 * 
	 * @param emptyColor
	 */
	public void setEmptyTextColor(final Color emptyTextColor) {
		this.emptyTextColor = emptyTextColor;
	}

	/**
	 * Setzt den Hinweistext der bei einem Leeren Feld erscheinen soll
	 * 
	 * @param hinweis
	 */
	public void setHinweis(final String hinweis) {
		if (hinweis == null)
			hinweisTextWhenEmpty = "";
		else
			hinweisTextWhenEmpty = hinweis;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.text.JTextComponent#setText(java.lang.String)
	 */
	@Override
	public void setText(final String t) {
		if (t == null || t.length() == 0)
			handleEmptyField();
		else
			super.setText(t);
	}

	/**
	 * @return the Clear-Button-Icon
	 */
	public ImageIcon getClearIcon() {
		return clearIcon;
	}

	/**
	 * Sets the Clear-Button-Icon. If Null sets the Default-Icon
	 * 
	 * @param clearIcon
	 */
	public void setClearIcon(ImageIcon clearIcon) {
		if (clearIcon != null)
			this.clearIcon = clearIcon;
		else
			this.clearIcon = DEFAULT_CLEAR_ICON;
	}

	/**
	 * @return the RollOver-Clear-Button-Icon
	 */
	public ImageIcon getClearIconRollOver() {
		return clearIconRollOver;
	}

	/**
	 * Sets the RollOver-Clear-Button-Icon. If Null sets the Default-Icon
	 * 
	 * @param clearIconRollOver
	 */
	public void setClearIconRollOver(ImageIcon clearIconRollOver) {
		if (clearIconRollOver != null)
			this.clearIconRollOver = clearIconRollOver;
		else
			this.clearIconRollOver = DEFAULT_CLEAR_ICON_ROLLOVER;
	}

	/**
	 * @return the inactiv Clear-Button-Icon
	 */
	public ImageIcon getInactivIcon() {
		return inactivIcon;
	}

	/**
	 * Sets the inactiv Clear-Button-Icon. If Null sets the Default-Icon
	 * 
	 * @param inactivIcon
	 */
	public void setInactivIcon(ImageIcon inactivIcon) {
		if (inactivIcon != null)
			this.inactivIcon = inactivIcon;
		else
			this.inactivIcon = DEFAULT_INAKTIV_ICON;
	}

}
