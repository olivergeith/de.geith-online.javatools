package og.basics.gui;

import javax.swing.JTree;

/**
 * Diese Klasse erweiter JTree um ein paar Extras ;-)
 * 
 * @author Oliver
 * 
 */
public class OTree extends JTree {
	private static final long serialVersionUID = 1L;

	public void expandAllRows() {
		for (int i = 0; i < getRowCount(); i++)
			expandRow(i);
	}

	public void collapseAllRows() {
		for (int i = 0; i < getRowCount(); i++)
			collapseRow(i);
	}

}
