package de.og.batterycreator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import og.basics.gui.about.UniversalAboutDialog;
import og.basics.gui.about.VersionDetails;
import og.basics.gui.icon.CommonIconProvider;
import de.og.batterycreator.creators.ArcCreator;
import de.og.batterycreator.creators.ArcDecimalCreator;
import de.og.batterycreator.creators.BinaryBarsCreator;
import de.og.batterycreator.creators.BinarySquaresCreator;
import de.og.batterycreator.creators.BrickBattCreator;
import de.og.batterycreator.creators.BrickBattNoGapCreator;
import de.og.batterycreator.creators.BrickDecimalCreator;
import de.og.batterycreator.creators.DefaultCreator;

public class IconCreatorFrame extends JFrame {

	private static final String APP_NAME = "Battery Icon Creator";
	private static final long serialVersionUID = 1L;
	private static final ImageIcon logoIcon = new ImageIcon(ConfigPanel.class.getResource("logo.png"));

	private static IconCreatorFrame frame;

	private final Vector<ImageIcon> icons = new Vector<ImageIcon>();
	private final Vector<DefaultCreator> creators = new Vector<DefaultCreator>();
	private final JMenuBar menuBar = new JMenuBar();
	private final JToolBar toolBar = new JToolBar();
	private BeendenAktion beendenAktion;
	private AboutAktion aboutAktion;
	private CreateAktion createAktion;
	private final JList<String> list = new JList<String>();
	private JComboBox<DefaultCreator> creatorBox;
	private final ConfigPanel configPane = new ConfigPanel();

	public static void main(final String[] args) {
		frame = new IconCreatorFrame();

	}

	/**
	 * Creates the desired Icons ;-)
	 */
	private void create() {
		final DefaultCreator creator = (DefaultCreator) creatorBox.getSelectedItem();
		creator.setSettings(configPane.getSettings());
		creator.createAllImages();

		list.removeAll();
		icons.removeAllElements();
		// icons = new Vector<ImageIcon>();
		System.gc();
		final Vector<String> filenames = new Vector<String>();
		for (int i = 0; i <= 100; i++) {
			filenames.add(creator.getFileName(i, false));
			icons.add(creator.getIcon(i, false));
		}
		for (int i = 0; i <= 100; i++) {
			filenames.add(creator.getFileName(i, true));
			icons.add(creator.getIcon(i, true));
		}
		list.repaint(0);
		list.setListData(filenames);
		list.repaint();
		pack();
	}

	public IconCreatorFrame() {
		super();
		System.out.println("Starting " + APP_NAME);
		setTitle(APP_NAME);
		setIconImage(logoIcon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 100, 300, 600);

		creators.add(new ArcCreator());
		creators.add(new ArcDecimalCreator());
		creators.add(new BrickBattCreator());
		creators.add(new BrickBattNoGapCreator());
		creators.add(new BrickDecimalCreator());
		creators.add(new BinaryBarsCreator());
		creators.add(new BinarySquaresCreator());

		initUI();
		setVisible(true);

		pack();
	}

	/**
	 * Gui Init
	 */
	private void initUI() {

		getContentPane().setLayout(new BorderLayout());
		toolBar.setFloatable(false);
		setJMenuBar(menuBar);
		getContentPane().add(toolBar, BorderLayout.NORTH);

		// Icon Liste
		list.setCellRenderer(new IconListCellRenderer());
		list.setBackground(Color.black);
		final JScrollPane scroller = new JScrollPane();
		scroller.add(list);
		scroller.getViewport().setView(list);
		getContentPane().add(scroller, BorderLayout.CENTER);
		getContentPane().add(configPane, BorderLayout.WEST);

		// Comobox mit creatoren anzeigen
		creatorBox = new JComboBox<DefaultCreator>(creators);
		creatorBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final DefaultCreator creator = (DefaultCreator) creatorBox.getSelectedItem();
				if (creator != null) {
					configPane.setSettings(creator.getSettings());
				}
			}
		});
		creatorBox.setSelectedIndex(0);

		// Menü und Buttonbar erzeugen
		createAktionen();
		makeMenuAndButtonBar();
	}

	/**
	 * Aktionen erzeugen und in einem Vector mit Aktionen ablegen
	 */
	private void createAktionen() {
		beendenAktion = new BeendenAktion("Beenden", CommonIconProvider.getInstance().BUTTON_ICON_EXIT);
		aboutAktion = new AboutAktion("About", CommonIconProvider.getInstance().BUTTON_ICON_INFO);
		createAktion = new CreateAktion("Create Icons", CommonIconProvider.getInstance().BUTTON_ICON_START);
	}

	/**
	 * Menüleiste Zusammensetzen
	 */
	private void makeMenuAndButtonBar() {
		final JMenu dateiMenu = new JMenu("Datei");
		menuBar.add(dateiMenu);
		dateiMenu.add(createAktion);
		dateiMenu.addSeparator();
		dateiMenu.add(beendenAktion);
		dateiMenu.add(aboutAktion);
		toolBar.add(beendenAktion);
		toolBar.add(creatorBox);
		toolBar.add(createAktion);
		toolBar.addSeparator();
		toolBar.add(aboutAktion);
	}

	/**
	 * @return the logoicon
	 */
	public static ImageIcon getLogoicon() {
		return logoIcon;
	}

	/**
	 * Renderer für IconList
	 */
	private class IconListCellRenderer implements ListCellRenderer<Object> {
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {
			String iconName = null;

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof String) {
				iconName = (String) value;
				renderer.setBorder(new EmptyBorder(1, 1, 1, 1));
				renderer.setText(iconName);
				renderer.setBackground(Color.black);
				renderer.setForeground(Color.white);
				renderer.setIcon(icons.elementAt(index));
			}
			return renderer;
		}
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

	private class CreateAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public CreateAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			create();

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
			details.setVersion("1.0");
			details.setDate("30.09.2012");
			details.setLogo(logoIcon);
			details.setCopyright("by Oliver Geith");
			details.setDescription("This application can create icons for batteries...");
			final UniversalAboutDialog aboutDialog = new UniversalAboutDialog(frame, details);
			aboutDialog.setVisible(true);

		}
	}

}
