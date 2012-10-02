package og.basics.gui.tracepanel;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class TracerDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame("Tracer");
		f.getContentPane().setLayout(new BorderLayout());
		f.setSize(600, 400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		TracePanel pan = new TracePanel();
		// NewTracePanel pan = new NewTracePanel(new
		// DefaultTextFileSaveHandler(".", "AlbumListing", ".txt",
		// "Tracefile"));
		f.getContentPane().add(pan, BorderLayout.CENTER);
		f.setVisible(true);
		pan.appendWarningText("Warning");
		pan.appendErrorText("Error");
		pan.appendInfoText("Info");
		pan.appendText("Normal");
		pan.appendSuccessText("Success");

	}
}
