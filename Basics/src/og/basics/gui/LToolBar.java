package og.basics.gui;

import java.awt.Component;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * Diese Klasse erweitert den JToolBar um ein neue Methode So ist es einfach
 * möglich einem Button Icon "UND" Text zu geben
 * 
 * @author Geith
 * 
 */
public class LToolBar extends JToolBar {

	/**
	 * Die Normale Methode add(Action) legt einen Button nur mit einem Icon an.
	 * D er ActionName wird verschluckt! Hier kann man nun über das Boolean
	 * wählen, ob mit oder ohne Text.
	 * 
	 * @param a
	 *            Die Aktion
	 * @param withText
	 * @return den JButton, der den ToolBar hinzugefügt wurde
	 */
	public JButton add(final Action a, final boolean withText) {
		final JButton b = super.add(a);
		if (withText) {
			// test aus der Aktion holen
			final Object o = a.getValue(Action.NAME);
			if (o instanceof String)
				b.setText((String) o);
		}
		return b;
	}

	@Override
	public void setEnabled(final boolean arg0) {
		final Component[] c = getComponents();
		for (int i = 0; i < c.length; i++) {
			c[i].setEnabled(arg0);
		}
		super.setEnabled(arg0);
	}

}
