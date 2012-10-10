package de.og.autobingo;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import sun.awt.image.BufferedImageGraphicsConfig;

public class Hauptfenster extends JFrame {
	private static final long serialVersionUID = 6134182537494982755L;
	JLabel iconLabel = new JLabel();

	public Hauptfenster() throws HeadlessException {
		setTitle("AutoBingo Creator");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(iconLabel, BorderLayout.CENTER);
		final ImageIcon icon = createIcon();
		iconLabel.setIcon(icon);
		pack();
	}

	private ImageIcon createIcon() {
		final int targeticonsize = 100;
		final int zielraster = 5;
		final File startdir = new File("./icons/");
		final BufferedImage over = new BufferedImage(zielraster * targeticonsize + zielraster + 1, zielraster * targeticonsize + zielraster + 1,
				BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = over.createGraphics();
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (startdir.exists() && startdir.isDirectory()) {
			final File[] pngs = findPNGFiles(startdir);
			numberssofar.removeAllElements();
			for (int i = 0; i < pngs.length; i++) {
				// i randomizen
				final int rand = getRandomIndex(pngs.length);

				final File png = pngs[rand];
				final int e = i % zielraster;
				final int z = i / zielraster;
				System.out.println("Image = " + png.getPath());
				final ImageIcon img = new ImageIcon(png.getPath());
				final BufferedImage temp = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
				final Graphics2D g = temp.createGraphics();
				g.drawImage(img.getImage(), 1, 1, null);
				final BufferedImage bimg = resize(temp, targeticonsize);
				g2d.drawImage(bimg, 1 + e * (targeticonsize + 1), 1 + z * (targeticonsize + 1), null);
				if (i == zielraster * zielraster)
					break;
			}
			writeOverviewFile(over);
			return new ImageIcon(over);
		}
		return null;
	}

	final Random randomGenerator = new Random();
	Vector<Integer> numberssofar = new Vector<Integer>();

	private int getRandomIndex(final int max) {
		int rand = randomGenerator.nextInt(max);
		while (numberssofar.contains(rand)) {
			rand = randomGenerator.nextInt(max);
		}
		numberssofar.add(rand);
		return rand;
	}

	private void writeOverviewFile(final BufferedImage overview) {
		final File dir = new File("./overviews/");
		if (!dir.exists())
			dir.mkdirs();
		final File file = new File("./overviews/overview_" + getTimestamp() + ".png");
		try {
			ImageIO.write(overview, "png", file);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private final static String getTimestamp() {
		final DateFormat df = new SimpleDateFormat("yyyyMMdd_hhmmss");
		return (df.format(new Date()));
	}

	private File[] findPNGFiles(final File dir) {
		// Liste aller MP3-Files
		final File[] musicFiles = dir.listFiles(new FilenameFilter() {

			public boolean accept(final File dir, final String name) {
				return name.toLowerCase().endsWith(".png");
			}
		});
		return musicFiles;
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		System.out.println("Starting...");
		final Hauptfenster f = new Hauptfenster();
		f.setVisible(true);

	}

	/**
	 * Method to resize an Image
	 * 
	 * @param img
	 * @param width
	 * @param height
	 * @return
	 */
	protected BufferedImage resize(final BufferedImage img, final int height) {
		// get the aspect ratio right...
		final int width = Math.round((float) img.getWidth() / (float) img.getHeight() * height);

		final int type = img.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : img.getType();
		final BufferedImage resizedImage = new BufferedImage(width, height, type);
		final Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(img, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	protected BufferedImage resizeTrick(BufferedImage image, final int height) {
		image = createCompatibleImage(image);
		image = resize(image, 100);
		image = blurImage(image);
		image = resize(image, height);
		return image;
	}

	private BufferedImage blurImage(final BufferedImage image) {
		final float ninth = 1.0f / 9.0f;
		final float[] blurKernel = {
				ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth
		};

		final Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
		map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		final RenderingHints hints = new RenderingHints(map);
		final BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, hints);
		return op.filter(image, null);
	}

	private static BufferedImage createCompatibleImage(final BufferedImage image) {
		final GraphicsConfiguration gc = BufferedImageGraphicsConfig.getConfig(image);
		final int w = image.getWidth();
		final int h = image.getHeight();
		final BufferedImage result = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
		final Graphics2D g2 = result.createGraphics();
		g2.drawRenderedImage(image, null);
		g2.dispose();
		return result;
	}

}
