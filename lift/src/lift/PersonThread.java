package lift;

public class PersonThread extends Thread {
        private Monitor m;

        public PersonThread(Monitor monitor) {
            this.m = monitor;
        }

        public void run() {
            while(true) {
                try {
                    int delay =   1000 *((int) (Math.random() * 46.0));
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int from = (int) (Math.random() * 7.0);
                int to = (int) (Math.random() * 7.0);
                while (from == to){
                    to = (int) (Math.random() * 7.0);
                }
                try {
                    m.travel(from, to);
                } catch (InterruptedException e) {}
            }

        }
}
