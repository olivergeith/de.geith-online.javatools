package og.basics.util.timer;

/**
 * Dies ist eine Trigger-Klasse, die in regelmäßigen Abständen trigger()
 * aufruft. Sie Stoppt nach Aufruf von stopTriggering();<br>
 * Da dies ein Thread ist, darf er nicht wiederverwendet werden und muss immer
 * wieder new() angelegt werden
 * 
 * @author Oliver
 * 
 */
public abstract class AbstractTrigger extends Thread {
	private final int triggerDelay;
	private boolean stopped = false;
	private int triggercount = 0;

	/**
	 * Konstruktor
	 * 
	 * @param triggerDelay
	 *            in millisec
	 */
	public AbstractTrigger(int triggerDelay) {
		this.triggerDelay = triggerDelay;
	}

	@Override
	public final void run() {
		while (!stopped) {
			triggercount++;
			trigger(triggercount);
			try {
				Thread.sleep(triggerDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Stoppet das Triggern
	 */
	public void stopTriggering() {
		stopped = true;
	}

	/**
	 * Methode muss vom Aufrufer implementiert werden und wird mit jedem Trigger
	 * aufgerufen<br>
	 * Der triggercount wird mit jedem Aufruf erhöht! Zählung beginnt bei 1
	 */
	public abstract void trigger(int triggercount);

}
