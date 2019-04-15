package interweaving;

/*
 * two thread are incrementing the counter.
 * This is not an atomic operation that's why counter is usally less than 20000 after doWrok()
 */
public class ThreadsInterweavingDemo {

    private int counter;

    private void doWork() throws InterruptedException {
        Thread incrementThread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter++;
            }
        });

        Thread incrementThread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter++;
            }
        });

        incrementThread1.start();
        incrementThread2.start();

        incrementThread1.join();
        incrementThread2.join();

        System.out.println(counter);
    }

    public static void main(String[] args) throws InterruptedException {
        new ThreadsInterweavingDemo().doWork();
    }
}
