package og.basics.gui;

import java.awt.BorderLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeCellEditor;

public class TreeEdit {
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object array[] = { Boolean.TRUE, Boolean.FALSE, "Hello" }; // Hello will
																	// map to
																	// false
		JTree tree = new JTree(array);
		tree.setEditable(true);
		tree.setRootVisible(true);

		JCheckBox checkBox = new JCheckBox();

		TreeCellEditor editor = new DefaultCellEditor(checkBox);
		tree.setCellEditor(editor);

		JScrollPane scrollPane = new JScrollPane(tree);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(300, 150);
		frame.setVisible(true);
	}
}