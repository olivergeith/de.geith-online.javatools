package og.basics.gui.html.demo2;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

// The Simple Web Browser.
public class MiniBrowser extends JFrame

implements HyperlinkListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4034851075283222285L;

	// These are the buttons for iterating through the page list.
	private final JButton backButton, forwardButton;

	// Page location text field.
	private final JTextField locationTextField;

	// Editor pane for displaying pages.
	private final JEditorPane displayEditorPane;

	// Browser's list of pages that have been visited.
	private final List<String> pageList = new ArrayList<String>();

	// Constructor for Mini Web Browser.
	public MiniBrowser() {
		// Set application title.
		super("Mini Browser");

		// Set window size.
		setSize(640, 480);

		// Handle closing events.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				actionExit();
			}
		});

		// Set up file menu.
		final JMenuBar menuBar = new JMenuBar();
		final JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		final JMenuItem fileExitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		fileExitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				actionExit();
			}
		});
		fileMenu.add(fileExitMenuItem);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);

		// Set up button panel.
		final JPanel buttonPanel = new JPanel();
		backButton = new JButton("< Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				actionBack();
			}
		});
		backButton.setEnabled(false);
		buttonPanel.add(backButton);
		forwardButton = new JButton("Forward >");
		forwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				actionForward();
			}
		});
		forwardButton.setEnabled(false);
		buttonPanel.add(forwardButton);
		locationTextField = new JTextField(35);
		locationTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					actionGo();
				}
			}
		});
		buttonPanel.add(locationTextField);
		final JButton goButton = new JButton("GO");
		goButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				actionGo();
			}
		});
		buttonPanel.add(goButton);

		// Set up page display.
		displayEditorPane = new JEditorPane();
		displayEditorPane.setContentType("text/html");
		displayEditorPane.setEditable(false);
		displayEditorPane.addHyperlinkListener(this);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(buttonPanel, BorderLayout.NORTH);
		getContentPane().add(new JScrollPane(displayEditorPane), BorderLayout.CENTER);
	}

	// Exit this program.
	private void actionExit() {
		System.exit(0);
	}

	// Go back to the page viewed before the current page.
	private void actionBack() {
		final URL currentUrl = displayEditorPane.getPage();
		final int pageIndex = pageList.indexOf(currentUrl.toString());
		try {
			showPage(new URL(pageList.get(pageIndex - 1)), false);
		} catch (final Exception e) {
		}
	}

	// Go forward to the page viewed after the current page.
	private void actionForward() {
		final URL currentUrl = displayEditorPane.getPage();
		final int pageIndex = pageList.indexOf(currentUrl.toString());
		try {
			showPage(new URL(pageList.get(pageIndex + 1)), false);
		} catch (final Exception e) {
		}
	}

	// Load and show the page specified in the location text field.
	private void actionGo() {
		final URL verifiedUrl = verifyUrl(locationTextField.getText());
		if (verifiedUrl != null) {
			showPage(verifiedUrl, true);
		} else {
			showError("Invalid URL");
		}
	}

	// Show dialog box with error message.
	private void showError(final String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}

	// Verify URL format.
	private URL verifyUrl(final String url) {
		// Only allow HTTP URLs.
		if (!url.toLowerCase().startsWith("http://"))
			return null;

		// Verify format of URL.
		URL verifiedUrl = null;
		try {
			verifiedUrl = new URL(url);
		} catch (final Exception e) {
			return null;
		}

		return verifiedUrl;
	}

	/*
	 * Show the specified page and add it to the page list if specified.
	 */
	private void showPage(final URL pageUrl, final boolean addToList) {
		// Show hour glass cursor while crawling is under way.
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		try {
			// Get URL of page currently being displayed.
			final URL currentUrl = displayEditorPane.getPage();

			// Load and display specified page.
			displayEditorPane.setPage(pageUrl);

			// Get URL of new page being displayed.
			final URL newUrl = displayEditorPane.getPage();

			// Add page to list if specified.
			if (addToList) {
				final int listSize = pageList.size();
				if (listSize > 0) {
					final int pageIndex = pageList.indexOf(currentUrl.toString());
					if (pageIndex < listSize - 1) {
						for (int i = listSize - 1; i > pageIndex; i--) {
							pageList.remove(i);
						}
					}
				}
				pageList.add(newUrl.toString());
			}

			// Update location text field with URL of current page.
			locationTextField.setText(newUrl.toString());

			// Update buttons based on the page being displayed.
			updateButtons();
		} catch (final Exception e) {
			// Show error messsage.
			showError("Unable to load page");
		} finally {
			// Return to default cursor.
			setCursor(Cursor.getDefaultCursor());
		}
	}

	/*
	 * Update back and forward buttons based on the page being displayed.
	 */
	private void updateButtons() {
		if (pageList.size() < 2) {
			backButton.setEnabled(false);
			forwardButton.setEnabled(false);
		} else {
			final URL currentUrl = displayEditorPane.getPage();
			final int pageIndex = pageList.indexOf(currentUrl.toString());
			backButton.setEnabled(pageIndex > 0);
			forwardButton.setEnabled(pageIndex < (pageList.size() - 1));
		}
	}

	// Handle hyperlink's being clicked.
	public void hyperlinkUpdate(final HyperlinkEvent event) {
		final HyperlinkEvent.EventType eventType = event.getEventType();
		if (eventType == HyperlinkEvent.EventType.ACTIVATED) {
			if (event instanceof HTMLFrameHyperlinkEvent) {
				final HTMLFrameHyperlinkEvent linkEvent = (HTMLFrameHyperlinkEvent) event;
				final HTMLDocument document = (HTMLDocument) displayEditorPane.getDocument();
				document.processHTMLFrameHyperlinkEvent(linkEvent);
			} else {
				showPage(event.getURL(), true);
			}
		}
	}

	// Run the Mini Browser.
	public static void main(final String[] args) {
		final MiniBrowser browser = new MiniBrowser();
		browser.setVisible(true);
	}
}