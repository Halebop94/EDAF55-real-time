package lift;

public class LiftThread extends Thread {
    private Monitor m;

    public LiftThread(Monitor monitor) {
        this.m = monitor;
    }

    public void run() {
        try{
            while (true) {
                m.moveLift();
                //m.awaitForPeople();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
