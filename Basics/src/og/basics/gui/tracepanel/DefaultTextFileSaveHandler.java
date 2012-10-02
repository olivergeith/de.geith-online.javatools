package og.basics.gui.tracepanel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import og.basics.gui.file.FileDialogs;

/**
 * Implementtier einen SaveDialog
 * 
 * @author geith
 * 
 */
public class DefaultTextFileSaveHandler implements ISaveHandler {
	private static final String DEFAULT_TRACE_TXT = "Trace";
	private static final String DEFAULT_TRACE_EXTENSION = ".txt";
	private static final String DEFAULT_TRACE_DESCRIPTION = "Tracefile";
	private static final String DEFAULT_StartDir = ".";

	private String saveFileName = DEFAULT_TRACE_TXT;
	private String saveFileExtension = DEFAULT_TRACE_EXTENSION;
	private String saveFileDescription = DEFAULT_TRACE_DESCRIPTION;
	private String saveFileStartDir = DEFAULT_StartDir;

	public DefaultTextFileSaveHandler(String saveFileStartDir, String saveFileName, String saveFileExtension, String saveFileDescription) {
		this.saveFileName = saveFileName;
		this.saveFileExtension = saveFileExtension;
		this.saveFileDescription = saveFileDescription;
		this.saveFileStartDir = saveFileStartDir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see og.basics.gui.tracepanel.ISaveHandler#save(java.lang.String)
	 */
	public boolean save(String text) {
		final SimpleDateFormat zeitFormater = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
		final String date = zeitFormater.format(new Date());
		final String fileName = saveFileName + "-" + date + saveFileExtension;
		File file = FileDialogs.saveFile(new File(saveFileStartDir), new File(fileName), saveFileExtension, saveFileDescription);
		if (file != null) {
			if (text != null && text.length() != 0) {
				System.out.println("File to save " + file.getPath());
				try {
					BufferedWriter writer = null;
					writer = new BufferedWriter(new FileWriter(file));
					writer.write(text);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
}
