package de.og.batterycreator.zipcreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import og.basics.gui.file.FileDialogs;

public class ZipMaker {

	public static void main(final String[] args) {
		final Vector<String> files2add = new Vector<String>();
		files2add.add("./tmp/stat_sys_battery_circle_0.png");
		files2add.add("./tmp/stat_sys_battery_circle_1.png");
		files2add.add("./tmp/stat_sys_battery_circle_2.png");
		final ZipMaker zipper = new ZipMaker();
		try {
			zipper.addFilesToArchive(files2add, "MORPH/system/app/SystemUI.apk/res/drawable-xhdpi/", "ArcBattery");
		} catch (final Exception e) {

			e.printStackTrace();
		}

	}

	private File template = new File("./template/template.zip");
	private final static String OUT_DIR = "./flashablezip_out/";

	public ZipMaker() {
		// creating outdirs
		final File pa = new File(OUT_DIR);
		if (!pa.exists())
			pa.mkdirs();
	}

	private void traceInfo(final String txt) {
		System.out.println(txt);
	}

	// private void traceError(final String txt) {
	// if (tracer != null)
	// tracer.appendErrorText(txt);
	// }

	public File getTemplate() {
		return template;
	}

	public void setTemplate(final File template) {
		this.template = template;
	}

	public boolean addFilesToArchive(final Vector<String> files2add, final String pathWithinArchive, final String outzipName) throws Exception {
		// defining output zip
		final ZipFile zipSrc = new ZipFile(template);

		// Verzeichnis anlegen
		final File pa = new File(OUT_DIR);
		if (!pa.exists())
			pa.mkdirs();
		// ausgabezipname vorbelegen
		File outzipFile = new File(OUT_DIR + outzipName + "_" + getTimestamp() + ".zip");
		outzipFile = FileDialogs.saveFile(pa, outzipFile, ".zip", "Save flashable Zip");
		if (outzipFile == null) {
			zipSrc.close();
			return false;
		}

		traceInfo("ZipCreator: Opening Output-Zip " + outzipFile.getPath());
		final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outzipFile));

		final byte[] buffer = new byte[32756];
		int len = 0;

		final Enumeration<? extends ZipEntry> srcEntries = zipSrc.entries();
		traceInfo("ZipCreator: Opening Template-Zip " + template.getPath());
		while (srcEntries.hasMoreElements()) {
			final ZipEntry entry = srcEntries.nextElement();
			final ZipEntry newEntry = new ZipEntry(entry.getName());
			zos.putNextEntry(newEntry);
			final InputStream is = zipSrc.getInputStream(entry);
			while ((len = is.read(buffer)) > 0) {
				zos.write(buffer, 0, len);
			}
			zos.flush();
			is.close();
		}
		for (final String name : files2add) {
			final File file = new File(name);
			traceInfo("Adding File to " + pathWithinArchive + file.getName());
			final ZipEntry newEntry = new ZipEntry(pathWithinArchive + file.getName());
			zos.putNextEntry(newEntry);

			final InputStream is = new FileInputStream(file);

			while ((len = is.read(buffer)) > 0) {
				zos.write(buffer, 0, len);
			}
			is.close();
		}
		traceInfo("ZipCreator: Closing Output-Zip " + outzipFile.getPath());
		zos.closeEntry();
		zos.finish();
		zos.close();
		traceInfo("ZipCreator: Closing Template-Zip " + template.getPath());
		zipSrc.close();
		return true;
	}

	private final static String getTimestamp() {
		final DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return (df.format(new Date()));
	}

	/**
	 * @return the outDir
	 */
	public String getOutDir() {
		return OUT_DIR;
	}

}
