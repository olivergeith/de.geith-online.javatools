/************************************************************
 * Funkwerk Information Technologies GmbH
 * Edisonstr. 3
 * 24145 Kiel
 * Copyright by Funkwerk Information Technologies GmbH
 * Diese Software ist urheberrechtlich geschützt.
 *
 * Projekt : HPA
 *
 * Modul : LDialog.java
 *
 * Datum : 12.07.2007
 * Autor : Udo Simon
 * 
 * Dialog, der
 *  - modal (DOCUMENT_MODAL, früher Treemodal) aufgerufen 
 *    werden kann -> Methode modalProcess
 *  - "always on top) aufgerufen werden kann
 *    -> Methode modlessProcess
 *
 **************************************************************/
package og.basics.gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * @author geith
 * 
 */
public class ModalProcessJDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private Object data = null;
	private boolean mIsOk = false;

	/**
	 * 
	 */
	public ModalProcessJDialog() {
		super();
		myInit();
	}

	/**
	 * @param parent
	 * @param title
	 */
	public ModalProcessJDialog(Window parent, String title) {
		super(parent, title);
		myInit();
	}

	/**
	 * 
	 */
	private void myInit() {
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setModalityType(ModalityType.MODELESS);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cancel();
			}
		});
	}

	/**
	 * soll vom Dialog aufgerufen werden, wenn der Cancel-Button gedrückt wird
	 */
	public void cancel() {
		mIsOk = false;
		setVisible(false);
		dispose();
	}

	/**
	 * soll vom Dialog aufgerufen werden, wenn der ok-Button gedrückt wird
	 */
	public void ok() {
		mIsOk = true;
		setVisible(false);
		dispose();
	}

	/**
	 * zu überschreibene Methode, um Daten, die zurückgegeben werden, zu
	 * bearbeiten
	 * 
	 * @return
	 * 
	 */
	public Object getData() {
		return data;
	}

	/**
	 * zu überschreibende Methode, um in "modalProcess" übergene Werte
	 * darzustellen
	 * 
	 * @param data
	 * 
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * diese Methode muss von aussen aufgerufen werden, wenn der Dialog modal
	 * (DOCUMENT_MODAL, früher TreeModal) gestartet werden soll; der Dialog ist
	 * dann nur zu dem aufrufenden Window modal
	 * 
	 * @param data
	 *            z.B. das Object, das im Dialog bearbeitet werden soll
	 * @param index
	 *            z.B. der index in einem übergebenen Vector
	 * @return
	 * 
	 */
	public Object modalProcess(Object data, int index) {
		setModalityType(ModalityType.DOCUMENT_MODAL);

		setData(data);
		setVisible(true);
		if (mIsOk)
			return getData();
		else
			return null;
	}

	/**
	 * @return
	 */
	public JFrame getRootFrame() {
		JFrame frame = null;
		Window parent = (Window) getParent();
		while (parent != null) {
			if (parent instanceof JFrame)
				frame = (JFrame) parent;
			parent = (Window) parent.getParent();
		}
		return frame;
	}

	/**
	 * Setlocation wurde überschrieben, um Dialoge immer relativ zu ihren
	 * Parents zu positionieren. Probleme gab es beim Aufschalten auf
	 * Mehrmonitor-Systemen
	 */
	@Override
	public void setLocation(int x, int y) {
		if (getParent() != null) {
			Point parentLocation = getParent().getLocation();
			super.setLocation(parentLocation.x + x, parentLocation.y + y);
		} else
			super.setLocation(x, y);
	}

	/**
	 * Zentriert den Client über dem Parent
	 */
	public void centerToParent() {
		if (getParent() != null) {
			Dimension parentSize = getParent().getSize();
			Point loc = getParent().getLocation();
			Dimension size = this.getSize();
			parentSize.height = parentSize.height / 2;
			parentSize.width = parentSize.width / 2;
			size.height = size.height / 2;
			size.width = size.width / 2;
			int y = loc.y + parentSize.height - size.height;
			int x = loc.x + parentSize.width - size.width;
			super.setLocation(x, y);
		}
	}
}
