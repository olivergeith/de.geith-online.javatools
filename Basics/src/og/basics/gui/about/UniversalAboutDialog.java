package og.basics.gui.about;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class UniversalAboutDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private final VersionDetails version;

	public UniversalAboutDialog(final Frame parent, VersionDetails version) {
		super(parent);
		this.version = version;
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("About: " + version.getApplicationname());
		setResizable(false);
		myGuiInit();
		pack();
		centerOnParent();
	}

	/**
	 * Centers this Dialog on the Parent
	 */
	private void centerOnParent() {
		Container c = getParent();
		if (c != null) {
			Rectangle cb = c.getBounds();
			this.setBounds(cb.x + cb.width / 2 - getWidth() / 2, cb.y + cb.height / 2 - getHeight() / 2, getWidth(), getHeight());
		}
	}

	/**
	 * Gui INit
	 */
	private void myGuiInit() {
		getContentPane().setLayout(new BorderLayout());
		final JLabel logo = new JLabel();
		logo.setIcon(version.getLogo());

		String v = createVersionHtml();
		final JLabel version = new JLabel(v);
		version.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

		final JLabel description = new JLabel(createDescriptionHtml());
		description.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

		getContentPane().add(logo, BorderLayout.WEST);
		getContentPane().add(version, BorderLayout.CENTER);
		getContentPane().add(description, BorderLayout.SOUTH);
	}

	private String createDescriptionHtml() {
		String html = "<html><hr>";

		html += "<font size=3 color=black>";
		html += version.getDescription() + "<br>";
		html += "</font>";

		html += "</html>";
		return html;
	}

	/**
	 * Erzeugt den Versions HTML-String
	 * 
	 * @return
	 */
	private String createVersionHtml() {
		String html = "<html>";

		html += "<font size=5 color=blue>";
		html += "<b>" + version.getApplicationname() + "</b><br><hr>";
		html += "</font>";

		html += "<font size=4 color=black>";
		html += "<b>Copyright : " + version.getCopyright() + "</b><br>";
		html += "</font>";

		html += "<font size=3 color=black>";
		html += version.getCompany() + "<br>";
		html += "<hr>";
		html += "</font>";

		html += "<font size=3 color=black>";
		html += "Version: " + version.getVersion() + "<br>";
		html += "Date: " + version.getDate() + "<br>";
		html += "</font>";

		html += "</html>";
		return html;
	}
}
