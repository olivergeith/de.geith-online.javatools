package de.og.batterycreator.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;

import og.basics.gui.about.UniversalAboutDialog;
import og.basics.gui.about.VersionDetails;
import og.basics.gui.icon.CommonIconProvider;
import og.basics.gui.tracepanel.DefaultTextFileSaveHandler;
import og.basics.gui.tracepanel.TracePanel;
import de.og.batterycreator.gui.IconCreatingPanel;
import de.og.batterycreator.gui.IconOverviewPanel;

public class IconCreatorFrame extends JFrame {

	private static final String APP_NAME = "Battery Icon Creator";
	public static final String VERSION_NR = "8.0";

	private static final long serialVersionUID = 1L;
	private static final ImageIcon logoIcon = new ImageIcon(IconCreatorFrame.class.getResource("logo.png"));
	private final ImageIcon errorIcon = new ImageIcon(this.getClass().getResource("error.png"));
	private final ImageIcon cfgIcon = new ImageIcon(this.getClass().getResource("cfg.png"));
	// private final ImageIcon zipIcon = new
	// ImageIcon(this.getClass().getResource("zip.png"));

	private static IconCreatorFrame frame;
	private final TracePanel tracer = new TracePanel(new DefaultTextFileSaveHandler(".", "Logging", ".txt", "Tracefile"));
	private final JTabbedPane tabPane = new JTabbedPane();
	// private final JToolBar toolBar = new JToolBar();

	private BeendenAktion beendenAktion;
	private AboutAktion aboutAktion;
	private final IconOverviewPanel overviewPanel = new IconOverviewPanel();
	private final IconCreatingPanel iconCreatingPanel = new IconCreatingPanel(tracer, overviewPanel);
	private final JMenuBar menuBar = new JMenuBar();

	public static void main(final String[] args) {
		frame = new IconCreatorFrame();

	}

	public IconCreatorFrame() {
		super();
		tracer.appendInfoText("Starting " + APP_NAME + " ----- Version " + VERSION_NR);
		setTitle(APP_NAME + " ----- Version " + VERSION_NR);
		setIconImage(logoIcon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 100, 300, 800);
		initUI();
		setVisible(true);
		pack();
	}

	/**
	 * Gui Init
	 */
	private void initUI() {
		getContentPane().setLayout(new BorderLayout());
		setJMenuBar(menuBar);
		// toolBar.setFloatable(false);
		// getContentPane().add(toolBar, BorderLayout.NORTH);

		// Menü und Buttonbar erzeugen
		createAktionen();
		makeMenuAndButtonBar();
		getContentPane().add(createTabbedPane());
	}

	private void makeMenuAndButtonBar() {
		final JMenu dateiMenu = new JMenu("Datei");
		menuBar.add(dateiMenu);
		dateiMenu.add(beendenAktion);
		dateiMenu.add(aboutAktion);
		// toolBar.add(beendenAktion);
		// toolBar.add(aboutAktion);
	}

	private JTabbedPane createTabbedPane() {
		// Tabpane zusammenbasteln
		tabPane.addTab("IconCreating", cfgIcon, iconCreatingPanel, "Create your icons here...");
		tabPane.addTab("Overview-Image", cfgIcon, overviewPanel, "Get an Overview of your icons");
		tabPane.addTab("TraceLog", errorIcon, tracer, "TraceLog");
		return tabPane;
	}

	/**
	 * Aktionen erzeugen und in einem Vector mit Aktionen ablegen
	 */
	private void createAktionen() {
		beendenAktion = new BeendenAktion("Beenden", CommonIconProvider.getInstance().BUTTON_ICON_CANCEL);
		aboutAktion = new AboutAktion("About", CommonIconProvider.getInstance().BUTTON_ICON_INFO);
	}

	private class BeendenAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public BeendenAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			System.exit(0);

		}
	}

	private class AboutAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public AboutAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			final VersionDetails details = new VersionDetails();
			details.setApplicationname(APP_NAME);
			details.setCompany("Geith-Online.de");
			details.setVersion(VERSION_NR);
			details.setDate("30.09.2012");
			details.setLogo(logoIcon);
			details.setCopyright("by Oliver Geith");
			details.setDescription("This application can create icons for batteries...");
			final UniversalAboutDialog aboutDialog = new UniversalAboutDialog(frame, details);
			aboutDialog.setVisible(true);

		}
	}
}
