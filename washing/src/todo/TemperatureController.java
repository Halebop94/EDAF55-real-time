package todo;


import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;
import se.lth.cs.realtime.event.RTEvent;


public class TemperatureController extends PeriodicThread {
	// TODO: add suitable attributes
	AbstractWashingMachine theM;

	double tempGoal;

	TemperatureEvent latest;

	int currentMode = TemperatureEvent.TEMP_IDLE;

	WashingProgram source;

	public TemperatureController(AbstractWashingMachine mach, double speed) {
		super((long) (10000/speed)); // TODO: replace with suitable period
		theM = mach;
	}

	public void perform() {
		// TODO: implement this method
		RTEvent ev = mailbox.tryFetch();

		if(ev instanceof TemperatureEvent){
			latest = (TemperatureEvent) ev;

				if(latest.getMode()==TemperatureEvent.TEMP_IDLE){
					theM.setHeating(false);
					currentMode = TemperatureEvent.TEMP_IDLE;
				}
				else if(latest.getMode()==TemperatureEvent.TEMP_SET){
					currentMode = TemperatureEvent.TEMP_SET;
					tempGoal = latest.getTemperature();
					source = (WashingProgram) latest.getSource();
				}
			}

		if(currentMode == TemperatureEvent.TEMP_SET && theM.getWaterLevel()>0.5){
			if(theM.getTemperature() < (tempGoal-0.7)){
				theM.setHeating(true);
			}
			else{
				theM.setHeating(false);

				if(source != null){
					source.putEvent(new AckEvent(this));
					source = null;
				}

			}


		}



	}
}
