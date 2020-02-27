package lift;

public class Main {

    public static void main(String[] args) {
        LiftView liftv = new LiftView();
        Monitor m = new Monitor(liftv);
        LiftThread lv = new LiftThread(m);
        for(int i = 0; i < 20; i++) {
            PersonThread pt = new PersonThread(m);
            pt.start();
        }
        lv.start();

    }
}
