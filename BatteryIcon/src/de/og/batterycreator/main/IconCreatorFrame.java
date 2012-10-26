package de.og.batterycreator.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import og.basics.gui.about.UniversalAboutDialog;
import og.basics.gui.about.VersionDetails;
import og.basics.gui.icon.CommonIconProvider;
import de.og.batterycreator.gui.IconCreatingPanelNew;
import de.og.batterycreator.gui.iconstore.IconStore;

public class IconCreatorFrame extends JFrame {

	private static final String APP_NAME = "Battery Icon Creator";
	public static final String VERSION_NR = "11.0";
	private static final String VERSION_DATE = "xx.xx.2012";
	private static final long serialVersionUID = 1L;
	private static IconCreatorFrame frame;
	private BeendenAktion beendenAktion;
	private AboutAktion aboutAktion;
	private final IconCreatingPanelNew iconCreatingPanel = new IconCreatingPanelNew();
	private final JMenuBar menuBar = new JMenuBar();

	public static void main(final String[] args) {
		System.out.println("Starting The Battery Icon Creator");
		frame = new IconCreatorFrame();

	}

	public IconCreatorFrame() {
		super();
		setTitle(APP_NAME + " ----- Version " + VERSION_NR);
		setIconImage(IconStore.logoIcon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 100, 300, 1280);
		initUI();
		System.out.println("Showing up Frame!");
		setVisible(true);
		pack();
	}

	/**
	 * Gui Init
	 */
	private void initUI() {
		getContentPane().setLayout(new BorderLayout());
		setJMenuBar(menuBar);
		// Menü und Buttonbar erzeugen
		createAktionen();
		makeMenuAndButtonBar();
		getContentPane().add(iconCreatingPanel);
	}

	private void makeMenuAndButtonBar() {
		final JMenu dateiMenu = new JMenu("Datei");
		menuBar.add(dateiMenu);
		dateiMenu.add(beendenAktion);
		dateiMenu.add(aboutAktion);
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
			details.setCompany("www.geith-online.de");
			details.setVersion(VERSION_NR);
			details.setDate(VERSION_DATE);
			details.setLogo(IconStore.logoIcon);
			details.setCopyright("by Oliver Geith");
			details.setDescription("This application can create icons for batteries...and much more !");
			final UniversalAboutDialog aboutDialog = new UniversalAboutDialog(frame, details);
			aboutDialog.setVisible(true);

		}
	}
}
