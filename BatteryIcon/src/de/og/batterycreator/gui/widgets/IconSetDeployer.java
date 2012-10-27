package de.og.batterycreator.gui.widgets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.swing.ImageIcon;

import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.creators.IconProviderInterface;

public class IconSetDeployer implements IconProviderInterface {
	private static final String CUSTOM_OUT_DIR = "./pngs/deploy/";

	private final IconSet iconSet;
	private final Vector<String> filenamesAndPath = new Vector<String>();

	private final String typeName;

	public IconSetDeployer(final IconSet iconSet, final String typeName) {
		this.iconSet = iconSet;
		this.typeName = typeName;
	}

	@Override
	public String getProviderName() {
		return iconSet.getName();
	}

	@Override
	public Vector<String> getAllFilenamesAndPath() {
		return filenamesAndPath;
	}

	@Override
	public void createAllImages(final int size) {
		final Vector<String> files = iconSet.getAllFilenamesIncludingPath();
		filenamesAndPath.removeAllElements();
		if (files != null) {
			for (final String f : files) {
				final File icoFile = new File(f);
				final ImageIcon icon = new ImageIcon(f);
				final BufferedImage buff = StaticImageHelper.resizeLongestSide2Size(StaticImageHelper.convertImageIcon(icon), size);
				final File outdir = new File(CUSTOM_OUT_DIR + typeName + File.separator + iconSet.getName() + File.separator);
				if (!outdir.exists())
					outdir.mkdirs();
				final File outf = new File(outdir.getPath() + File.separator + icoFile.getName());
				filenamesAndPath.add(outf.getPath());
				StaticImageHelper.writePNG(buff, outf);
			}
		}
	}

	@Override
	public boolean isActiv() {
		return true;
	}

}
