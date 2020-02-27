package todo;

import done.ClockInput;
import done.ClockOutput;

public class Edit extends Thread {
    private Clock clock;
    private ClockInput input;
    private ClockOutput output;
    private int prevChoice;
    private int choice;
    public Edit(ClockOutput out, ClockInput in, Clock cl){

            output = out;
            clock = cl;
            input = in;
            prevChoice = 0;
            choice = 0;
    }
    public void run() {

        while(true){


            input.getSemaphoreInstance().take();
            clock.remove();
            prevChoice = choice;
            clock.setAlarmOn(input.getAlarmFlag());
            choice = input.getChoice();
            if(prevChoice != choice) {

                if (prevChoice == 1) {

                    clock.setAlarmTime(input.getValue());


                } else if (prevChoice == 2 && choice == 0) {
                    int time = clock.setClockTime(input.getValue());
                    output.showTime(time);
                }
            }




        }




    }
}
