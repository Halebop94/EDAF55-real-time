package todo;
import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;
import se.lth.cs.realtime.semaphore.MutexSem;

import java.util.concurrent.Semaphore;

public class AlarmClock extends Thread {

	private static ClockInput	input;
	private static ClockOutput	output;
	private static Semaphore sem;
	private static Time time;
	private static Edit edit;
	private static Clock clock;

	public AlarmClock(ClockInput i, ClockOutput o) {
		output = o;
		clock = new Clock();
		edit = new Edit(o, i, clock);
		time = new Time(o, i, clock);
		input = i;
		sem = input.getSemaphoreInstance();

		time.start();
		edit.start();

		}

	// The AlarmClock thread is started by the simulator. No
	// need to start it by yourself, if you do you will get
	// an IllegalThreadStateException. The implementation
	// below is a simple alarmclock thread that beeps upon 
	// each keypress. To be modified in the lab.
	public void run() {
		while (true){

		}

	}
}
