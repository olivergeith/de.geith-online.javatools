package og.basics.gui.zeigerslider;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Demoklasse, die die Verwendung eines Zeigerslider zeigt
 * 
 * @author Oliver
 * 
 */
class ZeigerSliderDemo {

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame("BarGraf", null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(150, 75);
		f.setLayout(new BorderLayout());
		ZeigerSlider spindial = new ZeigerSlider(new ZeigerSliderModel(0, 0, 100, 5));
		f.getContentPane().add(spindial, BorderLayout.CENTER);
		f.setVisible(true);
	}

}
