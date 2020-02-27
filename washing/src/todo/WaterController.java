package todo;


import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;
import se.lth.cs.realtime.event.RTEvent;


public class WaterController extends PeriodicThread {
	private AbstractWashingMachine m;
	private WaterEvent latest;

	// TODO: add suitable attributes

	public WaterController(AbstractWashingMachine mach, double speed) {
		super((long) (2000/speed)); // TODO: replace with suitable period
        m = mach;
        //latest = we;
	}

	public void perform() {
        RTEvent ev = mailbox.tryFetch();
        if(ev instanceof WaterEvent) {
            latest = (WaterEvent) ev;
        }
        if(latest != null) {
	           if(latest.getMode() == WaterEvent.WATER_FILL){
	               if(m.getWaterLevel() < latest.getLevel()) {
                       m.setFill(true);
                   } else {
	                   m.setFill(false);
                       ((WashingProgram) latest.getSource()).putEvent((new AckEvent(this)));
                   }
               } else if(latest.getMode() == WaterEvent.WATER_DRAIN) {
	               m.setDrain(true);
	               if(m.getWaterLevel() == latest.getLevel()){
                       ((WashingProgram) latest.getSource()).putEvent((new AckEvent(this)));
                   }
                } else if(latest.getMode() == WaterEvent.WATER_IDLE){
                m.setDrain(false);
                m.setFill(false);
               }
           }

        }

}
