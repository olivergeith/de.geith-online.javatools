package og.basics.util.timer;

/**
 * Zeigt die Verwendung eines AbstracTimers und AbstractTrigges
 * 
 * @author Oliver
 * 
 */
class AbstractTimerTriggerDemo {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		startExampleTriggerAndTimer();

	}

	/**
	 * 1. Example-Implementation<br>
	 * Ruft am Ende die 2. Example-Implementation auf
	 */
	private static void startExampleTriggerAndTimer() {
		System.out.print("1. Example Starting");
		// erstmal konstruieren wir uns einen Trigger, der in regelm‰ﬂigen
		// Abst‰nden einen "." ausgibt
		final AbstractTrigger trigger = new AbstractTrigger(200) {

			@Override
			public void trigger(int triggercount) {
				System.out.print("." + triggercount);
			}
		};
		trigger.start();

		// Dann brauchen wir eine Timer der den Trigger nach 4000ms stoppt!
		AbstractTimer timer = new AbstractTimer(4000) {

			@Override
			void reachedTimeout() {
				trigger.stopTriggering();
				System.out.println("Stopping");
				startExampleTimerOnly();
			}
		};
		timer.start();
	}

	/**
	 * 2. Beispiel implementation
	 */
	private static void startExampleTimerOnly() {
		System.out.print("2. Example Starting");
		AbstractTimer timer = new AbstractTimer(4000) {

			@Override
			void reachedTimeout() {
				System.out.println("Stopping");
			}
		};
		timer.start();
		while (!timer.hasTimedout()) {
			System.out.print(".");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
