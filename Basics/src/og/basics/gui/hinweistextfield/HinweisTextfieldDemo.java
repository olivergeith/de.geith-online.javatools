package og.basics.gui.hinweistextfield;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Classe, die ein Hinweistextfield beispielhaft vorführt
 * 
 * @author Oliver
 * 
 */
class HinweisTextfieldDemo {
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.add(new HinweisTextfield("Click on 'x' to clear", "Text eingeben!!!!"), BorderLayout.CENTER);
		f.pack();
		f.setVisible(true);
	}

}
