package interweaving;

/*
 * with synchronized threads interweaving is solved - counter is always 20000 after doWork()
 */
public class SynchronizedThreadsInterweavingDemo {

    private int counter;

    private synchronized void incrementCounter() {
        counter++;
    }

    private void doWork() throws InterruptedException {
        Thread incrementThread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                incrementCounter();
            }
        });

        Thread incrementThread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                incrementCounter();
            }
        });

        incrementThread1.start();
        incrementThread2.start();

        incrementThread1.join();
        incrementThread2.join();

        System.out.println(counter);
    }

    public static void main(String[] args) throws InterruptedException {
        new SynchronizedThreadsInterweavingDemo().doWork();
    }
}
