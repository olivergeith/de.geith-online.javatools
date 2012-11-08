package og.basics.gui.html.demo1;

//MiniBrowser.java
//Another implementation of a minimal browser.  This one is tracks mouseover
//events on hyperlinks and shows the destination of the link in a status bar
//at the bottom of the window.
//
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MiniBrowser extends JFrame {

	private final JEditorPane jep;

	public MiniBrowser(final String startingUrl) {
		// Ok, first just get a screen up and visible, with an appropriate
		// handler in place for the kill window command.
		super("MiniBrowser");
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Now set up our basic screen components, the editor pane, the
		// text field for URLs, and the label for status and link information.
		final JPanel urlPanel = new JPanel();
		urlPanel.setLayout(new BorderLayout());
		final JTextField urlField = new JTextField(startingUrl);
		urlPanel.add(new JLabel("Site: "), BorderLayout.WEST);
		urlPanel.add(urlField, BorderLayout.CENTER);
		final JLabel statusBar = new JLabel(" ");

		// Here's the editor pane configuration. It's important to make
		// the "setEditable(false)" call; otherwise, our hyperlinks won't
		// work. (If the text is editable, then clicking on a hyperlink
		// simply means that you want to change the text, not follow the
		// link.)
		jep = new JEditorPane();
		jep.setEditable(false);

		try {
			jep.setPage(startingUrl);
		} catch (final Exception e) {
			statusBar.setText("Could not open starting page.  Using a blank.");
		}
		final JScrollPane jsp = new JScrollPane(jep);

		// Get the GUI components onto our content pane.
		getContentPane().add(jsp, BorderLayout.CENTER);
		getContentPane().add(urlPanel, BorderLayout.NORTH);
		getContentPane().add(statusBar, BorderLayout.SOUTH);

		// Last but not least, hook up our event handlers.
		urlField.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent ae) {
				try {
					jep.setPage(ae.getActionCommand());
				} catch (final Exception e) {
					statusBar.setText("Error: " + e.getMessage());
				}
			}
		});
		jep.addHyperlinkListener(new SimpleLinkListener(jep, urlField, statusBar));
	}

	public static void main(final String args[]) {
		String url = "";
		if (args.length == 1) {
			url = args[0];
			if (!(url.startsWith("http:") || url.startsWith("file:"))) {
				// If it's not a fully qualified URL, assume it's a file.
				if (url.startsWith("/")) {
					// Absolute path, so just prepend "file:"
					url = "file:" + url;
				} else {
					try {
						// Assume it's relative to the starting point.
						final File f = new File(url);
						url = f.toURL().toString();
					} catch (final Exception e) {
						url = "http://www.oreilly.com/";
					}
				}
			}
		} else {
			url = "http://www.oreilly.com/";
		}
		new MiniBrowser(url).setVisible(true);
	}
}