package og.basics.gui.icon;

import javax.swing.ImageIcon;

public class CommonIconProvider {

	public final ImageIcon BUTTON_ICON_CANCEL = getImageIcon("cancel.png");
	public final ImageIcon BUTTON_ICON_OK = getImageIcon("ok.png");
	public final ImageIcon BUTTON_ICON_HELP = getImageIcon("help.png");
	public final ImageIcon BUTTON_ICON_INFO = getImageIcon("info.png");
	public final ImageIcon BUTTON_ICON_EXIT = getImageIcon("exit.png");
	public final ImageIcon BUTTON_ICON_START = getImageIcon("start.png");
	public final ImageIcon BUTTON_ICON_STOP = getImageIcon("stop.png");
	public final ImageIcon BUTTON_ICON_OPEN = getImageIcon("open.png");
	public final ImageIcon BUTTON_ICON_SAVE = getImageIcon("save.png");

	/**
	 * Privater Konstruktor
	 */
	private CommonIconProvider() {
	}

	private static final CommonIconProvider instance = new CommonIconProvider();

	public static CommonIconProvider getInstance() {
		return instance;
	}

	/**
	 * Holt ein Icon aus dem Pfad dieser Klasse
	 * 
	 * @param name
	 * @return
	 */
	public ImageIcon getImageIcon(final String name) {
		try {
			return new ImageIcon(this.getClass().getResource(name));
		} catch (final Exception e) {
			System.out.println("Icon " + name + " not found");
		}
		return null;
	}

}
