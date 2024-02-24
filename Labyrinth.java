import java.util.concurrent.atomic.AtomicInteger;

public class Labyrinth {
    private static final int numGuests = 5; 
    private static final AtomicInteger guestsVisited = new AtomicInteger(0);
    private static final Object lock = new Object();
    private static boolean killThreads = false;

    static class Guest extends Thread {
        private final int guestNum;

        Guest(int guestNum) {
            this.guestNum = guestNum;
        }

        @Override
        public void run() {
            try {
                while (!killThreads) {
                    enterLabyrinth();
                    leaveLabyrinth();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // enter guest
        private void enterLabyrinth() throws InterruptedException {
            synchronized (lock) {
                while (!killThreads && guestsVisited.get() < numGuests) {
                    lock.wait();
                }
                if (!killThreads) {
                    System.out.println("guest: " + guestNum + " enters");
                }
            }
        }

        // increment atomic value, make guest exit
        private void leaveLabyrinth() {
            synchronized (lock) {
                guestsVisited.incrementAndGet();
                System.out.println("guest:  " + guestNum + " exits");
                if (guestsVisited.get() == numGuests) {
                    System.out.println("all guests went through");
                    lock.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        // start threads
        for (int i = 1; i <= numGuests; i++) {
            new Guest(i).start();
        }

        // wait for threads to finish
        try {
            Thread.sleep(5000);
            synchronized (lock) {
                killThreads = true;
                lock.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
