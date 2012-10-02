package og.basics.gui.zeigerslider;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class ZeigerSlider extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {
	private static final String CANVAS_CARD = "Canvas";
	private static final String EDITOR_CARD = "Editor";
	private static final long serialVersionUID = 1L;

	JSpinner spinner = null;
	JButton buttonOk = null;
	private ZeigerSliderCanvas canvas;
	private ZeigerSliderModel model = null;
	Point mousePos = new Point();
	private boolean dragging = false;

	private CardLayout cardLayout;

	private JPanel cardPanel;

	/**
	 * Konstruktor
	 * 
	 * @param model
	 */
	public ZeigerSlider(final ZeigerSliderModel model) {
		this.model = model;
		initGui();
	}

	private void initGui() {
		setMinimumSize(new Dimension(50, 50));

		cardPanel = new JPanel();
		final JPanel pEditor = new JPanel(new BorderLayout());
		cardLayout = new CardLayout();
		cardPanel.setLayout(cardLayout);

		setLayout(new BorderLayout());
		setPreferredSize(new java.awt.Dimension(100, 100));
		setMinimumSize(new java.awt.Dimension(100, 100));
		canvas = new ZeigerSliderCanvas();
		canvas.setValue((Integer) model.getValue());
		canvas.addMouseMotionListener(this);
		canvas.addMouseListener(this);
		addMouseWheelListener(this);
		addComponentListener(this);
		this.add(cardPanel, BorderLayout.CENTER);
		cardPanel.add(canvas, CANVAS_CARD);
		cardPanel.add(pEditor, EDITOR_CARD);
		spinner = new JSpinner(model);
		buttonOk = new JButton("ok");
		buttonOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				okButtonClicked(model.getIntValue());
			}

		});
		pEditor.add(buttonOk, BorderLayout.EAST);
		pEditor.add(spinner, BorderLayout.CENTER);
	}

	public void setValue(final int value) {
		model.setValue(value);
		canvas.repaint();
	}

	public int getValue() {
		return model.getIntValue();
	}

	/**
	 * Konvertierungsmethode, um von einer Mouse-X-Koordinate auf ein IntValue
	 * zu kommen
	 * 
	 * @param mouseX
	 * @return
	 */
	private int convertMouseX2ModelValue(final int mouseX) {
		final int value = model.getIntMin() + mouseX * (model.getIntMax() - model.getIntMin()) / canvas.getWidth();
		return value;
	}

	/**
	 * Konvertierungsmethode, um von einem IntValue eauf eine Mouse-X-Koordinate
	 * zu kommen
	 * 
	 * @param value
	 * @return
	 */
	private int convertModelValue2MouseX(final int value) {
		final int mouseX = (value - model.getIntMin()) * canvas.getWidth() / (model.getIntMax() - model.getIntMin());
		return mouseX;
	}

	/**
	 * OK-Button Aktion
	 * 
	 * @param value
	 */
	private void okButtonClicked(final int value) {
		model.setValue(value);
		cardLayout.show(cardPanel, CANVAS_CARD);
		repaintCanvas();
		updateUI();
		this.requestFocus();
	}

	/**
	 * Updatemethode, die beim Mouse-draggen aufgerufen wird
	 */
	public void updateComps(final MouseEvent e) {
		mousePos.move(e.getX(), e.getY());
		model.setValue(convertMouseX2ModelValue(e.getX()));
		repaintCanvas();
	}

	/**
	 * Methode um das Vanvas zu repainten, wenn von aussen das Value geändert
	 * wurde
	 * 
	 */
	private void repaintCanvas() {
		canvas.setZeigerPos(convertModelValue2MouseX(model.getIntValue()));
		canvas.setValue(model.getIntValue());
		canvas.repaint();
	}

	/**
	 * MouseListener
	 */
	@Override
	public void mouseClicked(final MouseEvent e) {
		if (e.getClickCount() == 2) {
			System.out.println("DoubleClick! " + isDragging());
			cardLayout.show(cardPanel, EDITOR_CARD);
			getRootPane().setDefaultButton(buttonOk);
		}
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		if (canvas.isTouched(e.getX(), e.getY())) {
			setDragging(true);
			updateComps(e);
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (isDragging()) {
			setDragging(false);
			updateComps(e);
		}
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		if (isDragging()) {
			updateComps(e);
		}
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
	}

	/**
	 * wird gerade gedragged?
	 * 
	 * @return
	 */
	private boolean isDragging() {
		return dragging;
	}

	/**
	 * DraggingMode an- und abschalten
	 * 
	 * @param dragging
	 */
	private void setDragging(final boolean dragging) {
		this.dragging = dragging;
	}

	/**
	 * MousWheelListener
	 */
	@Override
	public void mouseWheelMoved(final MouseWheelEvent e) {
		final int rotation = e.getWheelRotation();
		if (rotation > 0)
			model.stepDown();
		else
			model.stepUp();
		repaintCanvas();
	}

	/**
	 * ComponentListener
	 */
	@Override
	public void componentHidden(final ComponentEvent e) {
	}

	@Override
	public void componentMoved(final ComponentEvent e) {
	}

	@Override
	public void componentResized(final ComponentEvent e) {
		repaintCanvas();
	}

	@Override
	public void componentShown(final ComponentEvent e) {
	}

}
