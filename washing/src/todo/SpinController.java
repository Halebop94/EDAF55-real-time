package todo;


import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;
import se.lth.cs.realtime.event.RTEvent;

import java.sql.SQLOutput;


public class SpinController extends PeriodicThread {
    private AbstractWashingMachine m;
    private boolean spinRight;
    private SpinEvent spine;
	// TODO: add suitable attributes

	public SpinController(AbstractWashingMachine mach, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
        m = mach;
        spinRight = true;
	}

	public void perform() {
	    RTEvent ev = mailbox.tryFetch();
	    if(ev instanceof SpinEvent) {
            spine = (SpinEvent) ev;
        }
        if(spine != null) {
            if (spine.getMode() == SpinEvent.SPIN_SLOW) {
                if (spinRight) {
                    m.setSpin(2);
                } else {
                    m.setSpin(1);
                }
                spinRight = !spinRight;
            } else if (spine.getMode() == SpinEvent.SPIN_OFF) {
                m.setSpin(0);
            } else if(spine.getMode() == SpinEvent.SPIN_FAST) {
                m.setSpin(3);
            }
        }
	}
}
