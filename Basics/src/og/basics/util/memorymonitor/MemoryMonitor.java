/*
 * modul : MemoryMonitor.java
 *
 * date : 26.08.2002 15:17
 *
 * Copyright (c) 2000 by
 *
 * Vossloh System-Technik GmbH
 * Edisonstr. 3, D-24145 Kiel, Germany
 *
 * Diese Software ist urheberrechtlich geschützt.
 * All rights reserved.
 *
 * @author  : Gerhard Späth
 * @version : 
 *
 */

package og.basics.util.memorymonitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Tracks Memory allocated & used, displayed in graph form.
 */
public class MemoryMonitor extends JPanel {
	private static final long serialVersionUID = 1L;
	static JCheckBox dateStampCB = new JCheckBox("Output Date Stamp");
	public Surface surf;
	JPanel controls;
	boolean doControls = false;
	JTextField tf;
	JTextField maxmem;

	/**
	 * Konstruktor
	 */
	public MemoryMonitor() {
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(new EtchedBorder(), "Memory Monitor"));
		add(surf = new Surface());
		controls = new JPanel();
		controls.setLayout(new GridLayout(3, 2));
		controls.setPreferredSize(new Dimension(135, 80));
		final Font font = new Font("serif", Font.PLAIN, 12);

		JLabel label = new JLabel("Sample Rate (ms):");
		label.setFont(font);
		label.setForeground(Color.black);
		controls.add(label);
		tf = new JTextField("500");
		tf.setFont(font);
		tf.setPreferredSize(new Dimension(45, 20));
		controls.add(tf);

		controls.add(label = new JLabel("MaxMemory (KB):"));
		label.setFont(font);
		label.setForeground(Color.blue);
		maxmem = new JTextField("64000");
		maxmem.setFont(font);
		maxmem.setForeground(Color.blue);
		maxmem.setPreferredSize(new Dimension(60, 20));
		controls.add(maxmem);

		controls.add(dateStampCB);
		dateStampCB.setFont(font);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (doControls == false) {
					doControls = true;
					surf.stop();
					remove(surf);
					add(controls);
					controls.requestFocus();
				} else {
					doControls = false;
					remove(controls);
					surf.start();
					add(surf);
				}
				validate();
				repaint();
			}
		});
	}

	@Override
	public void addNotify() {
		super.addNotify();
		surf.start();
	}

	@Override
	public void removeNotify() {
		super.removeNotify();
		surf.stop();
	}

	/**
	 * Inner Class...mals die grafische Oberfläche
	 * 
	 * @author Oliver
	 * 
	 */
	private class Surface extends JPanel implements Runnable {
		private static final long serialVersionUID = 1L;
		public Thread thread;
		public long sleepAmount = 500;
		public float maxMemory = (float) 64000.0 * 1024;
		private int w, h;
		private BufferedImage bimg;
		private Graphics2D big;
		private final Font font = new Font("Times New Roman", Font.PLAIN, 11);
		private final Runtime r = Runtime.getRuntime();
		private int pts[];
		private int pts1[];
		private int ptNum;
		private int ptNum1;
		private int ascent, descent;
		private final Rectangle graphOutlineRect = new Rectangle();
		private final Rectangle2D mfRect = new Rectangle2D.Float();
		private final Rectangle2D muRect = new Rectangle2D.Float();
		private final Color graphColor = new Color(46, 139, 87);
		private final Color mfColor = new Color(0, 100, 0);
		private String usedStr;

		public Surface() {
			setBackground(Color.black);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(final MouseEvent e) {
					if (thread == null)
						start();
					else
						stop();
				}
			});
		}

		@Override
		public Dimension getMinimumSize() {
			return getPreferredSize();
		}

		@Override
		public Dimension getMaximumSize() {
			return getPreferredSize();
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(200, 150);
		}

		@Override
		public void paint(final Graphics g) {

			if (big == null) {
				return;
			}

			big.setBackground(getBackground());
			big.clearRect(0, 0, w, h);

			final float freeMemory = r.freeMemory();
			final float totalMemory = r.totalMemory();

			// .. Draw allocated and used strings ..
			big.setColor(Color.green);
			// big.drawString(String.valueOf((int) totalMemory/1024) +
			// "K allocated", 4.0f, (float) ascent+0.5f);
			big.drawString(String.valueOf((int) maxMemory / 1024) + "K ", 4.0f, ascent + 0.5f);
			usedStr = String.valueOf(((int) (totalMemory - freeMemory)) / 1024) + "K used " + String.valueOf((int) totalMemory / 1024) + "K allocated";
			big.drawString(usedStr, 4, h - descent);

			// Calculate remaining size
			final float ssH = ascent + descent;
			final float remainingHeight = (h - (ssH * 2) - 0.5f);
			// float blockHeight = remainingHeight/10;
			final float blockHeight = remainingHeight / 32;
			final float blockWidth = 20.0f;

			// .. Memory Free ..
			big.setColor(mfColor);
			// int MemUsage = (int) ((freeMemory / totalMemory) * 10);
			final int MemUsage = (int) (((maxMemory - (totalMemory)) / maxMemory) * 32);
			int i = 0;
			for (; i < MemUsage; i++) {
				mfRect.setRect(5, ssH + i * blockHeight, blockWidth, blockHeight - 1);
				big.fill(mfRect);
			}

			// .. Memory alloziert aber frei
			big.setColor(Color.red.darker());
			// int MemUsage = (int) ((freeMemory / totalMemory) * 10);
			final int MemUsage2 = (int) (((maxMemory - (totalMemory - freeMemory)) / maxMemory) * 32);
			for (; i < MemUsage2; i++) {
				mfRect.setRect(5, ssH + i * blockHeight, blockWidth, blockHeight - 1);
				big.fill(mfRect);
			}

			// .. Memory Used ..
			big.setColor(Color.green);
			for (; i < 32; i++) {
				muRect.setRect(5, ssH + i * blockHeight, blockWidth, blockHeight - 1);
				big.fill(muRect);
			}

			// .. Draw History Graph ..
			big.setColor(graphColor);
			final int graphX = 30;
			final int graphY = (int) ssH;
			final int graphW = w - graphX - 5;
			final int graphH = (int) remainingHeight;
			graphOutlineRect.setRect(graphX, graphY, graphW, graphH);
			big.draw(graphOutlineRect);

			if (pts == null) {
				pts = new int[graphW];
				ptNum = 0;
			} else if (pts.length != graphW) {
				int tmp[] = null;
				if (ptNum < graphW) {
					tmp = new int[ptNum];
					System.arraycopy(pts, 0, tmp, 0, tmp.length);
				} else {
					tmp = new int[graphW];
					System.arraycopy(pts, pts.length - tmp.length, tmp, 0, tmp.length);
					ptNum = tmp.length - 2;
				}
				pts = new int[graphW];
				System.arraycopy(tmp, 0, pts, 0, tmp.length);
			} else {
				big.setColor(Color.green);
				pts[ptNum] = (int) (graphY + graphH * ((maxMemory - (totalMemory - freeMemory)) / maxMemory));
				for (int j = graphX + graphW - ptNum, k = 0; k < ptNum; k++, j++) {
					if (k != 0) {
						if (pts[k] != pts[k - 1]) {
							big.drawLine(j - 1, pts[k - 1], j, pts[k]);
						} else {
							big.fillRect(j, pts[k], 1, 1);
						}
					}
				}
				if (ptNum + 2 == pts.length) {
					// throw out oldest point
					for (int j = 1; j < ptNum; j++) {
						pts[j - 1] = pts[j];
					}
					--ptNum;
				} else {
					ptNum++;
				}
			}

			if (pts1 == null) {
				pts1 = new int[graphW];
				ptNum1 = 0;
			} else if (pts1.length != graphW) {
				int tmp1[] = null;
				if (ptNum1 < graphW) {
					tmp1 = new int[ptNum1];
					System.arraycopy(pts1, 0, tmp1, 0, tmp1.length);
				} else {
					tmp1 = new int[graphW];
					System.arraycopy(pts1, pts1.length - tmp1.length, tmp1, 0, tmp1.length);
					ptNum1 = tmp1.length - 2;
				}
				pts1 = new int[graphW];
				System.arraycopy(tmp1, 0, pts1, 0, tmp1.length);
			} else {
				big.setColor(Color.red);
				pts1[ptNum1] = (int) (graphY + graphH * ((maxMemory - totalMemory) / maxMemory));
				for (int j = graphX + graphW - ptNum1, k = 0; k < ptNum1; k++, j++) {
					if (k != 0) {
						if (pts1[k] != pts1[k - 1]) {
							big.drawLine(j - 1, pts1[k - 1], j, pts1[k]);
						} else {
							big.fillRect(j, pts1[k], 1, 1);
						}
					}
				}
				if (ptNum1 + 2 == pts1.length) {
					// throw out oldest point
					for (int j = 1; j < ptNum1; j++) {
						pts1[j - 1] = pts1[j];
					}
					--ptNum1;
				} else {
					ptNum1++;
				}
			}
			g.drawImage(bimg, 0, 0, this);
		}

		public void start() {
			thread = new Thread(this);
			thread.setPriority(Thread.MIN_PRIORITY);
			thread.setName("MemoryMonitor");
			thread.start();
		}

		public synchronized void stop() {
			if (thread != null) {
				thread.interrupt();
				thread = null;
			}
			notify();
		}

		public void run() {

			final Thread me = Thread.currentThread();

			while (thread == me && !isShowing() || getSize().width == 0) {
				try {
					Thread.sleep(500);
				} catch (final InterruptedException e) {
					return;
				}
			}

			while (thread == me && isShowing()) {
				final Dimension d = getSize();
				if (d.width != w || d.height != h) {
					w = d.width;
					h = d.height;
					bimg = (BufferedImage) createImage(w, h);
					big = bimg.createGraphics();
					big.setFont(font);
					final FontMetrics fm = big.getFontMetrics(font);
					ascent = fm.getAscent();
					descent = fm.getDescent();
				}
				repaint();
				try {
					Thread.sleep(sleepAmount);
				} catch (final InterruptedException e) {
					break;
				}
				if (MemoryMonitor.dateStampCB.isSelected()) {
					System.out.println(new Date().toString() + " " + usedStr);
				}
			}
		}
	}

	/**
	 * Kompfort: Creates an instance and puts it into a JFrame. The JFrame is
	 * sized, displayed and returned to the caller.
	 * 
	 * @return javax.swing.JFrame
	 */
	public static JFrame createFramedInstance() {
		final MemoryMonitor demo = new MemoryMonitor();
		final WindowListener l = new WindowAdapter() {
			@Override
			public void windowDeiconified(final WindowEvent e) {
				demo.surf.start();
			}

			@Override
			public void windowIconified(final WindowEvent e) {
				demo.surf.stop();
			}
		};
		final JFrame f = new JFrame("MemoryMonitor");
		f.addWindowListener(l);
		f.getContentPane().add("Center", demo);
		f.pack();
		f.setSize(new Dimension(200, 200));
		f.setVisible(true);
		demo.surf.start();
		return f;
	}

}
