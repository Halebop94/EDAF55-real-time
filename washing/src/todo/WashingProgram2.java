/*
 * Real-time and concurrent programming course, laboratory 3
 * Department of Computer Science, Lund Institute of Technology
 *
 * PP 980812 Created
 * PP 990924 Revised
 */

package todo;

import done.AbstractWashingMachine;

/**
 * Program 3 of washing machine. Does the following:
 * <UL>
 *   <LI>Switches off heating
 *   <LI>Switches off spin
 *   <LI>Pumps out water
 *   <LI>Unlocks the hatch.
 * </UL>
 */
class WashingProgram2 extends WashingProgram {

	// ------------------------------------------------------------- CONSTRUCTOR

	/**
	 * @param   mach             The washing machine to control
	 * @param   speed            Simulation speed
	 * @param   tempController   The TemperatureController to use
	 * @param   waterController  The WaterController to use
	 * @param   spinController   The SpinController to use
	 */
	public WashingProgram2(AbstractWashingMachine mach,
                           double speed,
                           TemperatureController tempController,
                           WaterController waterController,
                           SpinController spinController) {
		super(mach, speed, tempController, waterController, spinController);
	}

	// ---------------------------------------------------------- PUBLIC METHODS

	/**
	 * This method contains the actual code for the washing program. Executed
	 * when the start() method is called.
	 */
	protected void wash() throws InterruptedException {

	    myMachine.setLock(true);

	    myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL, 0.5));
	    mailbox.doFetch();
	    mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));

	    myTempController.putEvent(new TemperatureEvent(this,TemperatureEvent.TEMP_SET, 40.0));
	    mailbox.doFetch();

		long sleepTime = System.currentTimeMillis () + (long) (15.0 * 60.0 * 1000.0 / mySpeed);
		while (sleepTime > System.currentTimeMillis()) { sleep(sleepTime - System.currentTimeMillis()); }
		// Switch of temp regulation
		myTempController.putEvent(new TemperatureEvent(this,
				TemperatureEvent.TEMP_SET,
				90.0));
		mailbox.doFetch();

		long sleepTime2 = System.currentTimeMillis () + (long) (30.0 * 60.0 * 1000.0 / mySpeed);
		while (sleepTime2 > System.currentTimeMillis()) { sleep(sleepTime2 - System.currentTimeMillis()); }
		// Switch off spin
		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE,0.0));
		myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN, 0.0));

		for(int i = 0; i < 5; i++) {
			myWaterController.putEvent(new WaterEvent(this,WaterEvent.WATER_FILL, 0.5));
			mailbox.doFetch();
			long sleepTime3 = System.currentTimeMillis () + (long) (2.0 * 60.0 * 1000.0 / mySpeed);
			while (sleepTime3 > System.currentTimeMillis()) { sleep(sleepTime3 - System.currentTimeMillis()); }
			myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN,0.0));
		}
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_FAST));

		long sleepTime4 = System.currentTimeMillis () + (long) (5.0 * 60.0 * 1000.0 / mySpeed);
		while (sleepTime4 > System.currentTimeMillis()) { sleep(sleepTime4 - System.currentTimeMillis()); }

		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
		// Unlock
		myMachine.setLock(false);
	}
}
