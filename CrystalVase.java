import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CrystalVase {
    private static final int numGuests = 5;
    private static final Queue<Guest> guestQueue = new LinkedList<>();
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
                    enterShowroom();
                    leaveShowroom();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // enter guest
        private void enterShowroom() throws InterruptedException {
            synchronized (guestQueue) {
                guestQueue.add(this);
                while (guestQueue.peek() != this) {
                    guestQueue.wait();
                }
                System.out.println("guest: " + guestNum + " enters");
            }
        }

        private void leaveShowroom() throws InterruptedException {
            synchronized (guestQueue) {
                guestQueue.poll();
                if (!guestQueue.isEmpty()) {
                    guestQueue.notify();
                }
                System.out.println("guest: " + guestNum + " exits");
            }
        }
    }

    public static void main(String[] args) {
        // create and start threads
        for (int i = 1; i <= numGuests; i++) {
            new Guest(i).start();
        }

        try {
            Thread.sleep(5000);
            synchronized (guestQueue) {
                killThreads = true;
                guestQueue.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // copy to make list iterable
        List<Guest> guestsCopy;
        synchronized (guestQueue) {
            guestsCopy = new ArrayList<>(guestQueue);
            killThreads = true;
            guestQueue.notifyAll();
        }

        for (Guest guest : guestsCopy) {
            try {
                guest.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("all guests went through");
    }
}
