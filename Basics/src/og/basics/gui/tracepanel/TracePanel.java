/*
 * 
 *
 */
package og.basics.gui.tracepanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import og.basics.gui.fonttoolbar.FontToolBar;
import og.basics.gui.fonttoolbar.IFontToolBarListener;
import og.basics.gui.hinweistextfield.HinweisTextfield;

/**
 * Das TracePanelStyled dient an "Konsole" in Swing-Oberfläche. <br>
 * "Styled", weil "Farb-Text" ausgegeben kann je nach verwendeter Methode.<br>
 * 
 * Vorteil: es gibt eine eingebaute Suchfunktion und einen Button um den Inhalt
 * zu löschen.<br>
 * Es kann Text unten oder oben per appendText() angefügt werden. Default ist
 * unten anfügen. Der Text wird automatisch gescrollt!
 * 
 * @author geith
 * 
 */
public class TracePanel extends JPanel implements ITracer, IFontToolBarListener {
	private static final String STYLE_NORMAL = "Normal";
	private static final String STYLE_ERROR = "Error";
	private static final String STYLE_WARNING = "Warning";
	private static final String STYLE_INFO = "Info";
	private static final String STYLE_OK_SUCCESS = "Ok";
	public static final int APPEND_OBEN = 1;
	public static final int APPEND_UNTEN = 2;
	private static final String INITIAL_SUCHSTRING_HIER_EINGEBEN = "<Suchstring hier eingeben>";

	private boolean useColorText = true;
	private boolean useIconsInTextPane = true;
	private boolean allignTextWithIcons = true;

	private int appendMode = APPEND_UNTEN;
	private static final long serialVersionUID = 1L;
	private final JTextPane textArea = new JTextPane();
	private final HinweisTextfield searchPatternField = new HinweisTextfield(INITIAL_SUCHSTRING_HIER_EINGEBEN);

	private final ImageIcon clearIcon = new ImageIcon(this.getClass().getResource("clear.png"));
	private final ImageIcon searchIcon = new ImageIcon(this.getClass().getResource("search.png"));
	private final ImageIcon errorIcon = new ImageIcon(this.getClass().getResource("icon_error.png"));
	private final ImageIcon warningIcon = new ImageIcon(this.getClass().getResource("icon_warning.png"));
	private final ImageIcon infoIcon = new ImageIcon(this.getClass().getResource("icon_info.png"));
	private final ImageIcon emptyIcon = new ImageIcon(this.getClass().getResource("icon_empty.png"));
	private final ImageIcon okIcon = new ImageIcon(this.getClass().getResource("icon_ok.png"));
	private final ImageIcon saveIcon = new ImageIcon(this.getClass().getResource("save.png"));

	// private final JToggleButton slockButton = new JToggleButton();
	private final JCheckBox slockButton = new JCheckBox("Scroll Lock");

	/**
	 * aktuelle suchposition
	 */
	private int searchPosition = 0;
	private ISaveHandler saveHandler = null;

	/**
	 * constructor
	 */
	public TracePanel() {
		super();
		initForm();
	}

	/**
	 * @param saveHandler
	 *            If you submit a SaveHandler, there will be a saveButton in the
	 *            toolbar to save the Content
	 */
	public TracePanel(ISaveHandler saveHandler) {
		super();
		this.saveHandler = saveHandler;
		initForm();
	}

	/**
	 * initForm method
	 * 
	 * @throws Exception
	 */
	private void initForm() {

		setLayout(new BorderLayout());

		this.add(createTopPanel(), BorderLayout.NORTH);
		this.add(createTextArea(), BorderLayout.CENTER);
		this.add(createBottomPanel(), BorderLayout.SOUTH);
		initStyles();

	}

	/**
	 * Erzeugt die Fusszeile mit dem Scroll-Lock Button
	 * 
	 * @return
	 */
	private Component createBottomPanel() {
		slockButton.setToolTipText("Scroll Lock");
		// Panel zusammenbauen
		final JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.add(slockButton);
		return toolbar;
	}

	/**
	 * erzeugt das topPanel mit dem Clear-Button und dem Suchfeld
	 */
	private Component createTopPanel() {

		final JButton saveButton = new JButton();
		saveButton.setToolTipText("Save TextArea");
		saveButton.setIcon(saveIcon);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				save();
			}
		});

		final JButton clearButton = new JButton();
		clearButton.setToolTipText("Clear TextArea");
		clearButton.setIcon(clearIcon);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				clear();
			}
		});

		final JButton searchButton = new JButton();
		searchButton.setToolTipText("Search TextArea");
		searchButton.setIcon(searchIcon);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				search();
			}
		});

		searchPatternField.setToolTipText("Such-Pattern hier eingeben");
		searchPatternField.addFocusListener(new FocusListener() {

			public void focusLost(final FocusEvent arg0) {
				// Hinweis anzeigen
				if (searchPatternField.getText().length() == 0) {
					searchPosition = 0;
				}
			}

			public void focusGained(final FocusEvent arg0) {
			}
		});
		// Keylistener, damit das Eingabefeld auf Enter reagiert
		searchPatternField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					search();
				} else
					searchPosition = 0;
			}
		});

		final FontToolBar fontbar = new FontToolBar();
		fontbar.addFontListener(this);
		fontbar.setSelectedFontSize(11);
		// folgende Zeile ist unnötig, weil beim setSelectedSize automatisch die
		// Listener informiert werden
		// textArea.setFont(fontbar.getSelectedFont());

		// TopPanel zusammenbauen
		final JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		// topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		toolbar.add(searchPatternField);
		toolbar.addSeparator();
		toolbar.add(searchButton);
		toolbar.add(clearButton);
		if (saveHandler != null)
			toolbar.add(saveButton);
		toolbar.add(fontbar);
		return toolbar;
	}

	/**
	 * Speichert den Inhalt des Panes in eine Textdatei
	 */
	private void save() {
		if (saveHandler != null) {
			try {
				Document doc = textArea.getStyledDocument();
				String allText = doc.getText(0, doc.getLength());
				boolean result = saveHandler.save(allText);
				if (!result)
					onSaveException();
			} catch (BadLocationException e) {
				onSaveException();
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gibt die Meldung aus, dass nicht gespeichert werden konnte!
	 */
	private void onSaveException() {
		JOptionPane.showMessageDialog(this, "Error writing the Traceoutput to file", "Save Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * druchsucht den angezeigten Text im KTextArea
	 */
	private void search() {
		Document doc = textArea.getStyledDocument();

		final String pattern = searchPatternField.getText();
		if (!pattern.equals(INITIAL_SUCHSTRING_HIER_EINGEBEN) && pattern.length() > 0) {
			String allText;
			try {
				allText = doc.getText(0, doc.getLength());
				final int newpos = allText.toLowerCase().indexOf(pattern.toLowerCase(), searchPosition + 1);
				if (newpos > 0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							textArea.getCaret().setSelectionVisible(true);
							textArea.setCaretPosition(newpos);
							textArea.moveCaretPosition(newpos + pattern.length());
						}
					});
				}
				searchPosition = newpos;
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see og.basics.gui.tracepanel.ITracer#clear()
	 */
	public void clear() {
		textArea.setText("");
	}

	/**
	 * Erzeugt das eigentliche TracePanel
	 */
	private JScrollPane createTextArea() {
		final JScrollPane scroller = new JScrollPane();
		textArea.setEditable(false);
		scroller.add(textArea);
		scroller.getViewport().setView(textArea);
		return scroller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see og.basics.gui.tracepanel.ITracer#setText(java.lang.String)
	 */
	public void setText(final String text) {
		textArea.setText(text);
	}

	/**
	 * Liefert die Textarea
	 * 
	 * @return
	 */
	public JTextPane getTextArea() {
		return textArea;
	}

	/**
	 * Setzt den Appenmodus. Möglich sind: <br>
	 * TracePanel.APPEND_OBEN <br>
	 * TracePanel.APPEND_UNTEN <br>
	 * 
	 * Wenn Nicht erfolgreich übernommen, dann wird APPEND_UNTEN angenommen
	 * 
	 * @param appendMode
	 * @return ob erfolgreich übernommen
	 */
	public boolean setAppendMode(final int appendMode) {
		if (appendMode == 1 || appendMode == 2) {
			this.appendMode = appendMode;
			return true;
		} else {
			this.appendMode = APPEND_UNTEN;
			return false;
		}

	}

	/**
	 * Gibt den aktuellen appendmode zurück
	 * 
	 * @return
	 */
	public int getAppendMode() {
		return appendMode;
	}

	public void setScrollLock(boolean scrolllock) {
		slockButton.setSelected(scrolllock);
	}

	public boolean isScrollLock() {
		return slockButton.isSelected();
	}

	private void initStyles() {
		Style s;

		Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		s = textArea.addStyle(STYLE_INFO, defaultStyle);
		StyleConstants.setForeground(s, Color.BLUE.darker());
		s = textArea.addStyle(STYLE_WARNING, defaultStyle);
		StyleConstants.setForeground(s, new Color(255, 137, 0));
		s = textArea.addStyle(STYLE_ERROR, defaultStyle);
		StyleConstants.setForeground(s, Color.RED.darker());
		s = textArea.addStyle(STYLE_NORMAL, defaultStyle);
		StyleConstants.setForeground(s, Color.BLACK);
		s = textArea.addStyle(STYLE_OK_SUCCESS, defaultStyle);
		StyleConstants.setForeground(s, Color.GREEN.darker());
		// StyleConstants.setBackground(s, new Color(255, 252, 211));

	}

	private void appendText(String myText, String style) {
		if (!isUseColorText())
			style = STYLE_NORMAL;

		Document doc = textArea.getStyledDocument();
		// System.out.println(myText);
		if (appendMode == APPEND_OBEN) {
			try {
				if (isUseIconsInTextPane()) {
					if (style.equals(STYLE_INFO))
						insertIcon(0, doc, infoIcon);
					else if (style.equals(STYLE_WARNING))
						insertIcon(0, doc, warningIcon);
					else if (style.equals(STYLE_ERROR))
						insertIcon(0, doc, errorIcon);
					else if (style.equals(STYLE_OK_SUCCESS))
						insertIcon(0, doc, okIcon);
					else {
						if (isAllignText())
							insertIcon(0, doc, emptyIcon);
					}
				}
				doc.insertString(0, myText + System.getProperty("line.separator"), textArea.getStyle(style));
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			if (!slockButton.isSelected())
				textArea.setCaretPosition(0);
		} else {
			try {
				if (isUseIconsInTextPane()) {
					if (style.equals(STYLE_INFO))
						insertIcon(doc.getLength(), doc, infoIcon);
					else if (style.equals(STYLE_WARNING))
						insertIcon(doc.getLength(), doc, warningIcon);
					else if (style.equals(STYLE_ERROR))
						insertIcon(doc.getLength(), doc, errorIcon);
					else if (style.equals(STYLE_OK_SUCCESS))
						insertIcon(doc.getLength(), doc, okIcon);
					else {
						if (isAllignText())
							insertIcon(doc.getLength(), doc, emptyIcon);
					}
				}
				doc.insertString(doc.getLength(), myText + System.getProperty("line.separator"), textArea.getStyle(style));
			} catch (BadLocationException e) {
				e.printStackTrace();
			}

			if (!slockButton.isSelected())
				textArea.setCaretPosition(doc.getLength() - myText.length());
		}
	}

	/**
	 * Inserts an icon in the Textpane
	 * 
	 * @param position
	 * @param doc
	 * @param icon
	 * @throws BadLocationException
	 */
	private void insertIcon(final int position, Document doc, ImageIcon icon) throws BadLocationException {
		StyleConstants.setIcon(textArea.getInputAttributes(), icon);
		((AbstractDocument) doc).replace(position, 0, " ", textArea.getInputAttributes());
	}

	@Override
	public void appendErrorText(String myText) {
		appendText(myText, STYLE_ERROR);

	}

	@Override
	public void appendWarningText(String myText) {
		appendText(myText, STYLE_WARNING);
	}

	@Override
	public void appendText(String myText) {
		appendText(myText, STYLE_NORMAL);
	}

	@Override
	public void appendInfoText(String myText) {
		appendText(myText, STYLE_INFO);
	}

	@Override
	public void appendSuccessText(String myText) {
		appendText(myText, STYLE_OK_SUCCESS);
	}

	/**
	 * @return
	 */
	public boolean isUseIconsInTextPane() {
		return useIconsInTextPane;
	}

	/**
	 * @param useIconsInTextPane
	 */
	public void setUseIconsInTextPane(boolean useIconsInTextPane) {
		this.useIconsInTextPane = useIconsInTextPane;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * og.basics.gui.fonttoolbar.IFontToolBarListener#setMyFont(java.awt.Font)
	 */
	public void setMyFont(Font font) {
		textArea.setFont(font);
	}

	/**
	 * @return Den Font der TextArea
	 */
	public Font getMyFont() {
		return textArea.getFont();
	}

	/**
	 * Defieniert, ob der Normale Text mit den anderen Texten (Warning Error
	 * usw.) alligned werden soll<br>
	 * In dem Fall wird ein Emptyicon vor dem normalen Text eingefügt
	 * 
	 * @param allignText
	 */
	public void setAllignText(boolean allignText) {
		allignTextWithIcons = allignText;
	}

	/**
	 * @return ob der Normale Text mit den anderen Texten (Warning Error usw.)
	 *         alligned wird
	 */
	public boolean isAllignText() {
		return allignTextWithIcons;
	}

	/**
	 * Schaltet die Benutzung von Styles (farbieger Text) an...<br>
	 * default ist on=true
	 * 
	 * @param useColorText
	 */
	public void setUseColorText(boolean useColorText) {
		this.useColorText = useColorText;
	}

	/**
	 * @return of Styles(Colored Text) benutzt werden
	 */
	public boolean isUseColorText() {
		return useColorText;
	}
}
