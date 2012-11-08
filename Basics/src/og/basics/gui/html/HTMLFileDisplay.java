package og.basics.gui.html;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HTMLFileDisplay extends JPanel {
	private static final long serialVersionUID = -3742307121183911588L;

	private final JEditorPane edit = new JEditorPane();

	private File file;

	public HTMLFileDisplay(final File file) {
		initUI();
		displayFile(file);

	}

	public HTMLFileDisplay() {
		initUI();
	}

	private void initUI() {
		setLayout(new BorderLayout());
		edit.addHyperlinkListener(new VerySimpleLinkListener(edit));

		edit.setEditable(false);
		final JScrollPane editorScrollPane = new JScrollPane(edit);
		this.add(editorScrollPane, BorderLayout.CENTER);
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void displayFile(final File file) {
		this.file = file;
		if (this.file != null) {
			try {
				final URL url = new URL("file", "localhost", this.file.getPath());
				// getResource(file.getPath());
				System.out.println(url.toString());
				edit.setPage(url);
			} catch (final MalformedURLException e) {
				e.printStackTrace();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * For testing purposes !!!
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {

		final JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Hallo Emmy!!!!!!!");
		f.setBounds(200, 200, 640, 600);
		f.setLayout(new BorderLayout());
		final File file = new File("./help/Help.htm");
		f.add(new HTMLFileDisplay(file), BorderLayout.CENTER);

		f.setVisible(true);
	}

}
