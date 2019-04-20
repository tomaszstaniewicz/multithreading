package cyclic_barrier;

import java.time.Instant;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// CyclicBarrier - all threads are waiting on cyclicBarrier.await() until the number passed in constructor  is reached
public class CyclicBarrierDemo {

    public static void main(String[] args) throws InterruptedException {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        Thread thread1 = new Thread(() -> {
            while (true) {
                System.out.println("Thread 1 started in: " + Instant.now());
                try {
                    cyclicBarrier.await(); // when two threads calls await then the thread is woken up
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 1 woken up in: " + Instant.now());
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                System.out.println("Thread 2 started in: " + Instant.now());
                try {
                    Thread.sleep(3000);
                    cyclicBarrier.await(); // when two threads calls await then the thread is woken up
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 2 woken up in: " + Instant.now());
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
