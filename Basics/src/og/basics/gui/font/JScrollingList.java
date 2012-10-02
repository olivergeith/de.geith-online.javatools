package og.basics.gui.font;

import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

/**
 * Eine JListbox mit integriertem Scroller
 * 
 * @author geith
 * 
 */
public class JScrollingList extends JList {
	private static final long serialVersionUID = 1L;
	private final JScrollPane scroller = new JScrollPane();;

	/**
	 * Gibt den Scroller zurück
	 * 
	 * @return
	 */
	public JScrollPane getScroller() {
		return scroller;
	}

	public JScrollingList() {
		super();
		myInit();
	}

	public JScrollingList(ListModel arg0) {
		super(arg0);
		myInit();
	}

	public JScrollingList(Object[] arg0) {
		super(arg0);
		myInit();
	}

	public JScrollingList(Vector<?> arg0) {
		super(arg0);
		myInit();
	}

	void myInit() {
		scroller.add(this);
		scroller.getViewport().setView(this);
	}

}
