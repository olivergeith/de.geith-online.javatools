package og.basics.util.memorymonitor;

import javax.swing.JFrame;

class MemoryMonitorDemo {

	/**
	 * @param args
	 */
	public static void main(final String s[]) {
		final JFrame f = MemoryMonitor.createFramedInstance();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
