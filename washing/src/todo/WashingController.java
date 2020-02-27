package todo;

import done.*;

public class WashingController implements ButtonListener {
    private AbstractWashingMachine theM;
    private double speed;
    private WashingProgram prog;
    private SpinController spinC;
    private WaterController waterLevel;
    private  TemperatureController tempLevel;
	// TODO: add suitable attributes
	
    public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
        theM = theMachine;
        speed = theSpeed;
        spinC = new SpinController(theM, theSpeed);
        spinC.start();
        waterLevel = new WaterController(theM, theSpeed);
        waterLevel.start();
        tempLevel = new TemperatureController(theM, theSpeed);
        tempLevel.start();
		// TODO: implement this constructor
    }

    public void processButton(int theButton) {
		switch(theButton) {
            case 0:
		        if(prog != null) {
		            prog.interrupt();
		        }
		        break;
            case 1: prog = new WashingProgram1(theM, speed, tempLevel,waterLevel, spinC);
                    prog.start();
                    break;
            case 2: prog = new WashingProgram2(theM, speed, tempLevel, waterLevel, spinC);
                    prog.start();
                    break;
            case 3:
                prog = new WashingProgram3(theM, speed, tempLevel, waterLevel, spinC);
                prog.start();
                break;


		}
    }
}
