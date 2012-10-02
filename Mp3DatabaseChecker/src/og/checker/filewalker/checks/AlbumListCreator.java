package og.checker.filewalker.checks;

import og.basics.gui.tracepanel.ITracer;
import og.checker.filewalker.DirectoryInfos;

public class AlbumListCreator extends AbstractChecker {

	private int countMp3 = 0;

	public AlbumListCreator(boolean doCheck, ITracer tracer) {
		super(doCheck, tracer);
	}

	@Override
	String getErrorText() {
		return "";
	}

	@Override
	boolean performCheck(DirectoryInfos dirInfo) {
		if (dirInfo.isMusicDir()) {
			countMp3 += dirInfo.getMusicFiles().length;
			return false;
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
		return getCount() + " Alben with " + countMp3 + " Mp3's";
	}

}
