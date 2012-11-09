package og.basics.gui;

import javax.swing.JTree;

public class LTree extends JTree {

	public void expandAllRows() {
		for (int i = 0; i < getRowCount(); i++)
			expandRow(i);
	}

	public void collapseAllRows() {
		for (int i = 0; i < getRowCount(); i++)
			collapseRow(i);
	}

}
