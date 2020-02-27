package todo;

import se.lth.cs.realtime.semaphore.MutexSem;
import se.lth.cs.realtime.semaphore.Semaphore;

public class Clock {

    private int clockTime;
    private int alarmTime;
    private boolean alarmOn;
    private int beepCount;

    private se.lth.cs.realtime.semaphore.Semaphore sem;

    public Clock(){

        clockTime = 0;
        sem = new MutexSem();
    }

    // 12 05 30
    public int tick(){
        sem.take();
        clockTime++;
        int h = clockTime/10000;
        int m = (clockTime-h*10000)/100;
        int s = (clockTime-h*10000-m*100);

        if(s == 60)
        {
            clockTime+=40;
            if(m == 59){

                if(h==23){
                    clockTime = 0;
                }else{
                    clockTime+=4000;
                }
            }

        }
        sem.give();
        return clockTime;
    }

    public int setAlarmTime(int alarmTime) {
        sem.take();
        this.alarmTime = alarmTime;
        sem.give();
        return alarmTime;
    }

    public int setClockTime(int clockTime){
        sem.take();
        this.clockTime = clockTime;
        sem.give();
        return clockTime;
    }

    public Semaphore getSem() {
        return sem;
    }

    public boolean isBeep(){
        if(beepCount>0){
            beepCount--;
            return true;
        }

        if(alarmOn){
            if(alarmTime == clockTime){
                beepCount = 20;
                return true;
            }
        }
        return false;

    }

    public void setAlarmOn(boolean alarmOn) {
        this.alarmOn = alarmOn;
    }

    public void remove(){
        beepCount = 0;
    }
}
