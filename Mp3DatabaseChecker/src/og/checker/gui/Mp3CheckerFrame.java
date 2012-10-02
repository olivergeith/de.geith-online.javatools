package og.checker.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import og.basics.gui.about.UniversalAboutDialog;
import og.basics.gui.tracepanel.DefaultTextFileSaveHandler;
import og.basics.gui.tracepanel.TracePanel;
import og.checker.filewalker.DirectoryWalker;
import og.checker.filewalker.IChecker;
import og.checker.filewalker.checks.AlbumListCreator;
import og.checker.filewalker.checks.AlbumListWhatsNewCreator;
import og.checker.filewalker.checks.AlbumWithoutMP3Checker;
import og.checker.filewalker.checks.FolderChecker;
import og.checker.filewalker.checks.FolderHirarchyChecker;
import og.checker.filewalker.checks.HiddenFileCheckerRepairer;
import og.checker.filewalker.checks.ID3AlbumArtistFolderCheckerRepairer;
import og.checker.filewalker.checks.ID3AlbumNameFolderChecker;
import og.checker.filewalker.checks.ID3CoverCheckerRepairer;
import og.checker.filewalker.checks.ID3V2ExistsChecker;
import og.checker.filewalker.checks.ID3V2TagConsistencyChecker;
import og.checker.filewalker.checks.IllegalFileChecker;
import og.checker.filewalker.checks.IllegalSubDirChecker;
import og.checker.filewalker.checks.Mp3BitrateChecker;
import og.checker.filewalker.checks.ReadonlyCheckerRepairer;
import og.checker.main.Checker;
import og.checker.main.MyConfig;

public class Mp3CheckerFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	// icons
	private final ImageIcon titleIcon = new ImageIcon(this.getClass().getResource("titleIcon.png"));
	private final ImageIcon startIcon = new ImageIcon(this.getClass().getResource("start.png"));
	private final ImageIcon stopIcon = new ImageIcon(this.getClass().getResource("stop.png"));
	private final ImageIcon infoIcon = new ImageIcon(this.getClass().getResource("info.png"));
	private final ImageIcon errorIcon = new ImageIcon(this.getClass().getResource("error.png"));
	private final ImageIcon listIcon = new ImageIcon(this.getClass().getResource("list.png"));
	// Gui-elemente
	private final TracePanel tracerErrors = new TracePanel(new DefaultTextFileSaveHandler(".", "Errors", ".txt", "Tracefile"));
	private final TracePanel tracerAlbenList = new TracePanel(new DefaultTextFileSaveHandler(".", "AlbumListing", ".txt", "Tracefile"));
	private final TracePanel tracerWhatsNewList = new TracePanel(new DefaultTextFileSaveHandler(".", "WhatsNewListing", ".txt", "Tracefile"));
	private final ConfigPanel configPanel = new ConfigPanel();
	private final JButton startButton = createStartButton();
	private final JButton stopButton = createStopButton();
	private final JProgressBar progressBar = createProgressBar();
	private final JTabbedPane tabPane = new JTabbedPane();

	// Sonstiges
	private DirectoryWalker walker;

	/**
	 * Konstruktor
	 */
	public Mp3CheckerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(Checker.version.getApplicationname() + " Version: " + Checker.version.getVersion());
		setIconImage(titleIcon.getImage());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.println("Stopping Application");
				Checker.config.write();
			}
		});
		createTabbedPane();
		final JToolBar toolbar = createToolBar();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tabPane, BorderLayout.CENTER);
		getContentPane().add(configPanel, BorderLayout.WEST);
		getContentPane().add(toolbar, BorderLayout.NORTH);
	}

	/**
	 * Erzeugt den Toolbar
	 * 
	 * @return
	 */
	private JToolBar createToolBar() {
		final JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.X_AXIS));
		toolbar.add(createInfoButton());
		toolbar.add(startButton);
		toolbar.add(stopButton);
		toolbar.add(new JLabel("    "));
		toolbar.add(progressBar);
		toolbar.add(new JLabel("    "));
		return toolbar;
	}

	/**
	 * @return
	 */
	private JTabbedPane createTabbedPane() {
		// für die Albenliste die Farben und Icons abschalten
		tracerAlbenList.setUseColorText(false);
		tracerAlbenList.setUseIconsInTextPane(false);
		tracerWhatsNewList.setUseColorText(false);
		tracerWhatsNewList.setUseIconsInTextPane(false);
		// Tabpane zusammenbasteln
		tabPane.addTab("Errors", errorIcon, tracerErrors, "List of Errors");
		// addTabAlbumListing();
		// addTabWhatsNewListing();
		return tabPane;
	}

	/**
	 * 
	 */
	private void addTabWhatsNewListing() {
		tabPane.addTab("What's New Listing", listIcon, tracerWhatsNewList, "Listings");
	}

	/**
	 * 
	 */
	private void addTabAlbumListing() {
		tabPane.addTab("Album Listing", listIcon, tracerAlbenList, "Listings");
	}

	/**
	 * Erzeugt den Progressbar
	 */
	private JProgressBar createProgressBar() {
		JProgressBar bar = new JProgressBar();
		bar.setStringPainted(true);
		bar.setString("Scanning Directory-Tree");
		bar.setVisible(false);
		bar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		return bar;
	}

	/**
	 * Öffnet den Aboutdialog
	 */
	private void showAboutDiolog() {
		final UniversalAboutDialog about = new UniversalAboutDialog(this, Checker.version);
		about.setVisible(true);
	}

	/**
	 * @return der info/about-Button
	 */
	private JButton createInfoButton() {
		JButton button = new JButton(infoIcon);
		button.setToolTipText("Info/About");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent arg0) {
				showAboutDiolog();
			}
		});
		return button;
	}

	/**
	 * @return der StopButton
	 */
	private JButton createStopButton() {
		JButton button = new JButton("Stop");
		button.setIcon(stopIcon);
		button.setToolTipText("Stop the Database-Checks");
		button.setEnabled(false);
		button.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent arg0) {
				stopFilewalker();
			}
		});
		return button;
	}

	/**
	 * Erzeugt den StartButton
	 */
	private JButton createStartButton() {
		JButton button = new JButton("Start");
		button.setIcon(startIcon);
		button.setToolTipText("Start the Database-Checks");
		button.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent arg0) {
				startFilewalker();
			}
		});
		return button;
	}

	/**
	 * Startet die Analyse
	 */
	private void startFilewalker() {
		// StopButton anschalten
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				stopButton.setEnabled(true);
				startButton.setEnabled(false);
				progressBar.setVisible(true);
			}
		});

		// checkerliste zusammenbasteln
		final Vector<IChecker> checkerlist = createCheckerList(Checker.config);
		// Tab-Panes einblenden wenn nötig
		if (Checker.config.bGenerateAlbenList) {
			tracerAlbenList.clear();
			addTabAlbumListing();
		} else {
			tabPane.remove(tracerAlbenList);
		}
		if (Checker.config.bGenerateAlbenListWhatsNew) {
			tracerWhatsNewList.clear();
			addTabWhatsNewListing();
		} else {
			tabPane.remove(tracerWhatsNewList);
		}

		// Tracen
		traceHead();

		// den FileWalker starten
		walker = new DirectoryWalker(progressBar, configPanel.getStartPath(), checkerlist);

		// wir warten bis der thread zuende ist
		// dann button wieder anschalten
		new Thread(new Runnable() {
			public void run() {
				while (walker.isTreadRunning()) {
					try {
						Thread.sleep(500);
					} catch (final InterruptedException e) {
						e.printStackTrace();
					}
				}
				traceStatistics(checkerlist);
				stopButton.setEnabled(false);
				startButton.setEnabled(true);
				progressBar.setVisible(false);
				progressBar.setString("Scanning Directory-Tree");
			}

		}).start();
	}

	/**
	 * Kopf im Logoutput schreiben
	 */
	private void traceHead() {
		tracerAlbenList.appendText("#######################################################");
		tracerAlbenList.appendText(" Creating Album-List " + configPanel.getStartPath());
		tracerAlbenList.appendText("#######################################################");
		tracerWhatsNewList.appendText("#######################################################");
		tracerWhatsNewList.appendText(" Creating What's New-List " + configPanel.getStartPath());
		tracerWhatsNewList.appendText("#######################################################");
		tracerErrors.appendText("#######################################################");
		tracerErrors.appendText(" Scanning for Errors " + configPanel.getStartPath());
		tracerErrors.appendText("#######################################################");
	}

	/**
	 * Statistik im Logoutput schreiben
	 * 
	 * @param checkerlist
	 */
	private void traceStatistics(final Vector<IChecker> checkerlist) {
		tracerAlbenList.appendText("#######################################################");
		tracerWhatsNewList.appendText("#######################################################");
		tracerErrors.appendText("#######################################################");
		for (IChecker checker : checkerlist) {
			checker.traceStatisticLine();
		}
		tracerAlbenList.appendText("#######################################################");
		tracerWhatsNewList.appendText("#######################################################");
		tracerErrors.appendText("#######################################################");
	}

	/**
	 * Stoppt den Filewalker
	 */
	private void stopFilewalker() {
		if (walker != null) {
			walker.stopThread();
		}

	}

	/**
	 * erzeugt die CheckerListe
	 * 
	 * @return
	 */
	private Vector<IChecker> createCheckerList(MyConfig config) {
		// Die Checkerliste erzeugen
		Vector<IChecker> checkerlist = new Vector<IChecker>();
		// Checker für die Error-Liste
		checkerlist.add(new FolderChecker(config.bFolderMissing, tracerErrors));
		checkerlist.add(new HiddenFileCheckerRepairer(config.bHiddenFiles, tracerErrors, config.bHiddenFilesRepair));
		checkerlist.add(new IllegalFileChecker(config.bIllegalFiles, tracerErrors, config.bIllegalFilesList));
		checkerlist.add(new IllegalSubDirChecker(config.bIllegalSubdirs, tracerErrors));
		checkerlist.add(new AlbumWithoutMP3Checker(config.bNoMp3InFolder, tracerErrors));
		checkerlist.add(new FolderHirarchyChecker(config.bAlbumFolderNameHirarchy, tracerErrors));
		checkerlist.add(new ReadonlyCheckerRepairer(config.bReadonlyCheck, tracerErrors, config.bReadonlyRepair));
		checkerlist.add(new ID3V2ExistsChecker(config.bID3V2ExistCheck, tracerErrors, config.bID3V2ExistRepair));
		checkerlist.add(new ID3AlbumNameFolderChecker(config.bID3AlbumNameCheck, tracerErrors));
		checkerlist.add(new ID3AlbumArtistFolderCheckerRepairer(config.bID3AlbumArtistCheck, tracerErrors, config.bID3AlbumArtistRepair));
		checkerlist.add(new ID3CoverCheckerRepairer(config.bID3CoverCheck, tracerErrors, config.bID3CoverRepair));
		checkerlist.add(new ID3V2TagConsistencyChecker(config.bID3TagConsistencyCheck, tracerErrors));
		checkerlist.add(new Mp3BitrateChecker(config.bID3BitrateCheck, tracerErrors, config.bitrateBorder));

		// Checker für die Alben-List
		checkerlist.add(new AlbumListCreator(config.bGenerateAlbenList, tracerAlbenList));
		checkerlist.add(new AlbumListWhatsNewCreator(config.bGenerateAlbenListWhatsNew, tracerWhatsNewList, config.maxdays));
		return checkerlist;
	}

}
