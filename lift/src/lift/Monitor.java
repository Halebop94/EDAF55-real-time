package lift;

public class Monitor {
    private int here; // If here !=next , here (floor number) tells from which floor
    // the lift is moving and next to which floor it is moving.
    private int next; // If here ==next , the lift is standing still on the floor
    // given by here.
    private int[] waitEntry; // The number of persons waiting to enter the lift at the
    // various floors.
    private int[] waitExit; // The number of persons (inside the lift) waiting to leave
    // the lift at the various floors.
    private int load; // The number of people currently occupying the lift.
    private boolean isGoingUp;
    private LiftView lv;

    public Monitor(LiftView lv) {
        this.lv = lv;
        isGoingUp = true;
        here = 0;
        next = 1;
        waitEntry = new int[7];
        waitExit = new int[7];
    }

    public synchronized void travel(int fromFloor, int toFloor)  throws InterruptedException {
        waitEntry[fromFloor]++;
        lv.drawLevel(fromFloor, waitEntry[fromFloor]);
        while(fromFloor != here || load == 4 || here != next) {
            wait();
        }
        waitExit[toFloor]++;
            load++;
            lv.drawLift(fromFloor, load);
            waitEntry[fromFloor]--;
            lv.drawLevel(fromFloor, waitEntry[fromFloor]);
        notifyAll();
        while(toFloor != here) {
            wait();
        }
        waitExit[toFloor]--;
        load--;
        lv.drawLift(toFloor, load);

        notifyAll();
    }


    public void moveLift() throws InterruptedException {
        synchronized (this) {
            if(here == 6) {
                isGoingUp = false;
                next= here-1;
            } else if (here == 0){
                isGoingUp = true;
                next= here+1;
            }
        }
        lv.moveLift(here, next);
        synchronized (this) {
            here = next;
            notifyAll();
            while(waitExit[here] != 0) {
                wait();
            }
            while(waitEntry[here] != 0 && load < 4) {
                wait();
            }
            if(isGoingUp) {
                next = next+1;
            } else {
                next = next-1;
            }
        }
    }
}
