package og.checker.filewalker.checks;

import og.basics.gui.tracepanel.ITracer;
import og.checker.filewalker.DirectoryInfos;

public class AlbumListWhatsNewCreator extends AbstractChecker {

	private int countMp3 = 0;
	private long maxdays = 7;

	public AlbumListWhatsNewCreator(boolean doCheck, ITracer tracer, long maxdays) {
		super(doCheck, tracer);
		this.maxdays = maxdays;
	}

	@Override
	String getErrorText() {
		return "New/Modified Album (maxdays=" + maxdays + ")";
	}

	@Override
	boolean performCheck(DirectoryInfos dirInfo) {
		if (dirInfo.isMusicDir()) {
			// wir schauen hier auf das Modify des Ordners. So kriegen wir raus,
			// wann der Ordner erstellt wurde. Änderungsdaten einzelner File
			// interessieren nicht.
			long mody = dirInfo.getDir().lastModified();
			long now = System.currentTimeMillis();

			// s m h d
			long diffdays = (now - mody) / (1000 * 60 * 60 * 24);
			if (diffdays <= maxdays) {
				countMp3 += dirInfo.getMusicFiles().length;
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see og.checker.filewalker.checks.AbstractChecker#createStatisticLine()
	 */
	@Override
	String createStatisticLine() {
		return getCount() + " new Alben with " + countMp3 + " Mp3's";
	}

}
