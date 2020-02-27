package todo;

import done.ClockInput;
import done.ClockOutput;

public class Time extends Thread{

    private static ClockOutput output;
    private static ClockInput input;
    private static Clock clock;

    public Time(ClockOutput co, ClockInput ci, Clock cl){
        output = co;
        input = ci;
        clock = cl;
    }

    public void run(){
        long t0 = System.currentTimeMillis();

        while (true){

            t0 += 1000;
            long now = System.currentTimeMillis();
            long dt = t0 - now;

            if(dt > 0){
                try {
                    Thread.sleep(1000);
                    output.showTime(clock.tick());
                    if(clock.isBeep()) output.doAlarm();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



        }

        }

}
