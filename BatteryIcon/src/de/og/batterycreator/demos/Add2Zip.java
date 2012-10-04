package de.og.batterycreator.demos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author Tom
 * 
 */
public class Add2Zip {

	/**
	 * @param args
	 */
	public static void main(final String[] args) throws Exception {
		final File archiveFile = new File("e:/foo.zip");
		final File file = new File("c:/tmp/Firefox Setup 1.0.3.exe");

		addFileToArchive(file, archiveFile);
	}

	public static void addFileToArchive(final File file, final File archiveFile) throws Exception {

		final File tmpFile = new File(String.valueOf(System.currentTimeMillis()));

		final ZipFile zipSrc = new ZipFile(archiveFile);

		final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(tmpFile));

		final byte[] buffer = new byte[32756];
		int len = 0;

		final Enumeration<? extends ZipEntry> srcEntries = zipSrc.entries();
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

		final ZipEntry newEntry = new ZipEntry(file.getName());
		zos.putNextEntry(newEntry);

		final InputStream is = new FileInputStream(file);

		while ((len = is.read(buffer)) > 0) {
			zos.write(buffer, 0, len);
		}

		zos.closeEntry();
		zos.finish();
		zos.close();
		is.close();
		zipSrc.close();
		archiveFile.delete();
		tmpFile.renameTo(archiveFile);
	}

}