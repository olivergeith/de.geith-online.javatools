package og.basics.gui.font;

/*
 * modul : FensterListPanel.java
 *
 * date : 19.06.2007 14:59
 *
 * @author  : 
 * @version : 
 *
 */

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * MyPanel
 * 
 * @author Geith
 */
public class FontChooserPanel extends JPanel {
	private static final int FONT_SIZE = 19;
	private static final long serialVersionUID = 1L;

	/**
	 * constructor
	 */
	public FontChooserPanel() {
		super();
		try {
			initForm();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	private final JScrollPane scrollPane = new JScrollPane();
	private final JList list = new JList();
	private final JTextArea textArea = new JTextArea("The quick brown fox...");
	private final JTextField fontsizeField = new JTextField("19");

	/**
	 * initForm method
	 * 
	 * @throws Exception
	 */
	private void initForm() throws Exception {
		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		list.setListData(fonts);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				drawTextArea();
			}
		});

		fontsizeField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawTextArea();
			}
		});

		scrollPane.add(list);
		scrollPane.getViewport().setView(list);
		scrollPane.setSize(500, 600);

		setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.WEST);
		this.add(textArea, BorderLayout.CENTER);
		this.add(fontsizeField, BorderLayout.NORTH);
	}

	private void drawTextArea() {
		String name = (String) list.getSelectedValue();
		int fontsize = FONT_SIZE;
		try {
			fontsize = Integer.parseInt(fontsizeField.getText());
		} catch (NumberFormatException exc) {
			fontsize = FONT_SIZE;
			fontsizeField.setText("" + fontsize);
		}
		Font f = new Font(name, Font.PLAIN, fontsize);
		textArea.setFont(f);
	}
}
