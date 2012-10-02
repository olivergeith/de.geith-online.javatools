package og.basics.util.timer;

/**
 * Dies ist eine Timeout-Classe, die bei erreichen des Timeouts Wenn der Timer
 * nach dem start() abgelaufen ist, wird die abstrakte Methode reachedTimeout()
 * aufgerufen <br>
 * Da dies ein Thread ist, darf er nicht wiederverwendet werden und muss immer
 * new angelegt werden
 * 
 * @author Oliver
 * 
 */
public abstract class AbstractTimer extends Thread {

	private final int timeoutMillies;
	private boolean timedout = false;

	/**
	 * Konstruktor
	 * 
	 * @param timeoutMillies
	 */
	public AbstractTimer(int timeoutMillies) {
		this.timeoutMillies = timeoutMillies;
	}

	@Override
	public final void run() {
		try {
			Thread.sleep(timeoutMillies);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timedout = true;
		reachedTimeout();
	}

	/**
	 * Methode wird aufgerufen, wenn der Timeout abgelaufen ist.
	 */
	abstract void reachedTimeout();

	/**
	 * Sagt, ob der Timeout abgelaufen ist<br>
	 * 
	 * @return true -> Timeout ist abgelaufen<br>
	 *         false -> der timer noch läuft<br>
	 */
	public boolean hasTimedout() {
		return timedout;
	}

}
