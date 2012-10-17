package og.basics.gui.image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import sun.awt.image.BufferedImageGraphicsConfig;

public class ImageResizer {

	public ImageResizer() {
		// TODO Auto-generated constructor stub
	}

	public static BufferedImage convertImageIcon(final ImageIcon img) {
		final BufferedImage buImg = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		buImg.getGraphics().drawImage(img.getImage(), 0, 0, null);
		return buImg;
	}

	/**
	 * Method to resize an Image
	 * 
	 * @param img
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage resize(final BufferedImage img, final int height) {
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

	public static BufferedImage resizeAdvanced(BufferedImage image, final int height) {
		image = createCompatibleImage(image);
		image = resize(image, 100);
		image = blurImage(image);
		image = resize(image, height);
		return image;
	}

	private static BufferedImage blurImage(final BufferedImage image) {
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
