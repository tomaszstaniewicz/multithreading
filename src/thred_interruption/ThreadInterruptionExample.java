package thred_interruption;

import java.time.Instant;

public class ThreadInterruptionExample {

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            System.out.println("Thread started in " + Instant.now());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted in " + Instant.now());
            }

            System.out.println("Thread finished normally");
        });

        thread1.start();

        Thread.sleep(1000);

        thread1.interrupt();

        System.out.println("Thread isInterrupted flag is: " + thread1.isInterrupted());
        System.out.println("Thread isAlive flag is: " + thread1.isAlive());
        System.out.println("Thread isDeamon flag is: " + thread1.isDaemon());

        thread1.join();
        System.out.println("Thread joined");


    }

}
