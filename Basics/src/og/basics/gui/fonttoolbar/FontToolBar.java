package og.basics.gui.fonttoolbar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

public class FontToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	private final ImageIcon fontIcon = new ImageIcon(this.getClass().getResource("font.png"));
	private final ImageIcon fontsizeIcon = new ImageIcon(this.getClass().getResource("fontsize.png"));
	private final ImageIcon okIcon = new ImageIcon(this.getClass().getResource("ok.png"));
	private static final String DEFAULT_FONT_NAME = "Monospaced";
	private static final String DEFAULT_FONT_SIZE = "12";
	private static final String DEFAULT_FONT_NAMES[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	private static final String DEFAULT_FONT_SIZES[] = {
			"6", "7", "8", "9", "10", "11", "12", "14", "16", "18", "20", "24", "28", "32", "36", "48", "72", "100"
	};

	private String fontNames[] = DEFAULT_FONT_NAMES;
	private String fontSizes[] = DEFAULT_FONT_SIZES;

	private final JComboBox fontNamesCombo = new JComboBox(DEFAULT_FONT_NAMES);
	private final JComboBox fontSizeCombo = new JComboBox(DEFAULT_FONT_SIZES);
	private final JButton showButton = new JButton();
	private final JButton hideButton = new JButton();

	private final List<IFontToolBarListener> listeners = new ArrayList<IFontToolBarListener>();

	public FontToolBar() {
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setFloatable(false);

		// diese Beiden fontButtons toggeln sich hin und her (ausprobieren ;-))
		fontNamesCombo.setSelectedItem(DEFAULT_FONT_NAME);
		fontNamesCombo.setBorder(new EmptyBorder(0, 1, 1, 1));
		fontNamesCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				informListeners();
				switchState(true);
			}

		});
		fontNamesCombo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent e) {
				if (e.getOppositeComponent() != null && !e.getOppositeComponent().equals(fontSizeCombo)) {
					switchState(true);
				}
			}
		});
		fontNamesCombo.setRenderer(new FontNameComboCellRenderer());
		fontNamesCombo.setMaximumRowCount(15);
		fontNamesCombo.setToolTipText("Select Font");
		fontNamesCombo.setVisible(false);

		fontSizeCombo.setSelectedItem(DEFAULT_FONT_SIZE);
		fontSizeCombo.setBorder(new EmptyBorder(0, 1, 1, 1));
		fontSizeCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				informListeners();
				switchState(true);
			}

		});
		fontSizeCombo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent e) {
				if (e.getOppositeComponent() != null && !e.getOppositeComponent().equals(fontNamesCombo)) {
					switchState(true);
				}
			}
		});
		fontSizeCombo.setRenderer(new FontSizeComboCellRenderer());
		fontSizeCombo.setMaximumRowCount(15);
		fontSizeCombo.setToolTipText("Select Font-Size");
		fontSizeCombo.setVisible(false);

		showButton.setToolTipText("Show Fontselection");
		showButton.setIcon(fontIcon);
		showButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				switchState(false);
			}
		});
		hideButton.setToolTipText("Hide Fontselection");
		hideButton.setIcon(okIcon);
		hideButton.setVisible(false);
		hideButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				switchState(true);
			}
		});
		addSeparator();
		add(fontNamesCombo);
		add(fontSizeCombo);
		add(hideButton);
		add(showButton);
	}

	private void switchState(final boolean showsSingelFontButtonOnly) {
		// fontNamesCombo.setFont(new Font((String)
		// fontNamesCombo.getSelectedItem(), Font.PLAIN, 14));
		fontNamesCombo.setVisible(!showsSingelFontButtonOnly);
		fontSizeCombo.setVisible(!showsSingelFontButtonOnly);
		hideButton.setVisible(!showsSingelFontButtonOnly);
		showButton.setVisible(showsSingelFontButtonOnly);

	}

	/**
	 * Informiert die Listener über den neu eingestellen Font
	 */
	private void informListeners() {
		for (final IFontToolBarListener listener : listeners) {
			listener.setMyFont(getSelectedFont());
		}
	}

	/**
	 * Gibt den eingestellten Font zurück
	 * 
	 * @return
	 */
	public Font getSelectedFont() {
		final String name = (String) fontNamesCombo.getSelectedItem();
		final int size = Integer.parseInt((String) fontSizeCombo.getSelectedItem());
		return new Font(name, Font.PLAIN, size);
	}

	/**
	 * Add-Methode für den Listener
	 * 
	 * @param listener
	 */
	public void addFontListener(final IFontToolBarListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * Remove-Methode für den Listener
	 * 
	 * @param listener
	 */
	public void removeFontListener(final IFontToolBarListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Setzt die Liste der Fontnames
	 * 
	 * @param fontNames
	 */
	public void setFontNames(final String fontNames[]) {
		if (fontNames != null && fontNames.length != 0)
			this.fontNames = fontNames;
		fontNamesCombo.removeAll();
		for (final String name : fontNames)
			fontNamesCombo.addItem(name);
	}

	/**
	 * Holt die Liste der Fontnames
	 * 
	 * @return
	 */
	public String[] getFontNames() {
		return fontNames;
	}

	/**
	 * @param fontSizes
	 */
	public void setFontSizes(final String fontSizes[]) {
		if (fontSizes != null && fontSizes.length != 0)
			this.fontSizes = fontSizes;
		fontSizeCombo.removeAll();
		for (final String size : fontSizes)
			fontSizeCombo.addItem(size);
	}

	/**
	 * Holt die Liste der Fontgrößen
	 * 
	 * @return
	 */
	public String[] getFontSizes() {
		return fontSizes;
	}

	/**
	 * Setzt die Fontgröße
	 * 
	 * @param size
	 *            must be between 6 and 200
	 */
	public void setSelectedFontSize(final int size) {
		if (size >= 6 && size <= 200)
			fontSizeCombo.setSelectedItem("" + size);
	}

	/**
	 * Setzt den Fontnamen
	 * 
	 * @param name
	 *            must be a valid Fontname (aus den systemfontnames)
	 * @return true, when name valid
	 */
	public boolean setSelectedFontName(final String name) {
		final String f = getValidFontName(name);
		if (f != null) {
			fontNamesCombo.setSelectedItem(f);
			return true;
		}
		return false;
	}

	/**
	 * Prüft ob der angegebene name in den System-Fonts ist (ignoreCase)
	 * 
	 * @param name
	 * @return the Name from the System-FontList or null if name not found
	 */
	private String getValidFontName(final String name) {
		if (name != null && name.length() != 0) {
			for (final String fname : fontNames) {
				if (fname.equalsIgnoreCase(name)) {
					return fname;
				}
			}
		}
		return null;
	}

	/**
	 * Renderer für FontauswahlCombo
	 * 
	 * @author geith
	 * 
	 */
	private class FontNameComboCellRenderer implements ListCellRenderer {
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {
			String fontName = null;

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof String) {
				fontName = (String) value;
				renderer.setBorder(new EmptyBorder(1, 1, 1, 1));
				renderer.setText(fontName);
				renderer.setFont(new Font(fontName, Font.PLAIN, 14));
				renderer.setIcon(fontIcon);
			}
			return renderer;
		}
	}

	/**
	 * Renderer für FontSizeauswahlCombo
	 * 
	 * @author geith
	 * 
	 */
	private class FontSizeComboCellRenderer implements ListCellRenderer {
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {
			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof String) {
				renderer.setBorder(new EmptyBorder(1, 1, 1, 1));
				renderer.setIcon(fontsizeIcon);
			}
			return renderer;
		}
	}

	/**
	 * Zum Ausprobieren
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		final JFrame f = new JFrame("FontToolBar");
		final FontToolBar bar = new FontToolBar();
		final Container cp = f.getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(bar, BorderLayout.NORTH);
		// Now that theButton and theLabel are ready, make the action listener
		f.setSize(350, 100);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
