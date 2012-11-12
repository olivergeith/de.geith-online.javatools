package de.og.batterycreator.cfg.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import og.basics.gui.Jcolorselectbutton.JColorSelectButton;
import og.basics.jgoodies.JGoodiesHelper;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.og.batterycreator.cfg.WifiSignalSettings;

public class WifiSignaleSettingsPanel extends SettingsPanel {
	private static final long serialVersionUID = 1L;

	private WifiSignalSettings settings = new WifiSignalSettings();

	private final JCheckBox cboxTransparentBgrnd = createCheckbox("Transparent Background (switchOff = experimental !)",
			"Use this, when your statusbar Background is not black!");

	private final JColorSelectButton backgroundColor = new JColorSelectButton("Background Color", "Color if not transparent");
	private final JColorSelectButton iconColorInactiv = new JColorSelectButton("Inactiv", "Color for inactiv Iconelements");
	private final JColorSelectButton inColor = new JColorSelectButton("ColorDataIn", "Color when Data comes in ;-)");
	private final JColorSelectButton outColor = new JColorSelectButton("ColorDataOut", "Color when Data comes in ;-)");
	private final JColorSelectButton color = new JColorSelectButton("Connected", "Color when connected");
	private final JColorSelectButton colorFully = new JColorSelectButton("Fully Connected", "Color when fully connected");

	// Construktor
	public WifiSignaleSettingsPanel() {
		initComponents();
		myInit();
		setSettings(settings);
	}

	private void initComponents() {
	}

	private void myInit() {
		setLayout(new BorderLayout());
		this.add(createTabPaneWifiColors(), BorderLayout.CENTER);
	}

	public JPanel createTabPaneWifiColors() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createGroupLabel("Colors"), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(iconColorInactiv, cc.xyw(2, ++row, 1));
		builder.add(cboxTransparentBgrnd, cc.xyw(2, ++row, 3));
		builder.add(backgroundColor, cc.xyw(6, row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Colors for Connection"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Colors for Data Activity"), cc.xyw(6, row, 3));
		builder.add(color, cc.xyw(2, ++row, 1));
		builder.add(colorFully, cc.xyw(4, row, 1));
		builder.add(inColor, cc.xyw(6, row, 1));
		builder.add(outColor, cc.xyw(8, row, 1));
		final JPanel cfp = builder.getPanel();
		return cfp;
	}

	public void setSettings(final WifiSignalSettings settings) {
		this.settings = settings;
		iconColorInactiv.setColor(settings.getColorInActiv());
		backgroundColor.setColor(settings.getBackgroundColor());
		cboxTransparentBgrnd.setSelected(settings.isTransparentBackground());
		inColor.setColor(settings.getInColor());
		outColor.setColor(settings.getOutColor());
		color.setColor(settings.getColor());
		colorFully.setColor(settings.getColorFully());
		validateControls();
		this.repaint();
	}

	public WifiSignalSettings getSettings() {
		settings.setColorInActiv(iconColorInactiv.getColor());
		settings.setBackgroundColor(backgroundColor.getColor());
		settings.setTransparentBackground(cboxTransparentBgrnd.isSelected());
		settings.setInColor(inColor.getColor());
		settings.setOutColor(outColor.getColor());
		settings.setColor(color.getColor());
		settings.setColorFully(colorFully.getColor());
		return settings;
	}

	@Override
	protected void validateControls() {
		// transparent Backround special behaviour
		backgroundColor.setEnabled(!cboxTransparentBgrnd.isSelected());
		backgroundColor.setVisible(!cboxTransparentBgrnd.isSelected());
		if (!backgroundColor.isEnabled()) {
			backgroundColor.setBackground(Color.black);
		}
	}
}
