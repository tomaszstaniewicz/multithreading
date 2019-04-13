package sharedmemory;

/*
 * two thread are incrementing counter.
 * This is not an atomic operation thats why counter is usally less tran 20000 after doWrok()
 */
public class SharedMemoryDemo {

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
        new SharedMemoryDemo().doWork();
    }
}
