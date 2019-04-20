package reentrant_lock;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread firstThread = new Thread(() -> {
            lock.lock();
            System.out.println("First thread waiting for user input");
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("First thread finished");
            lock.unlock();

        });

        Thread secondThread = new Thread(() -> {
            lock.lock();
            System.out.println("Second thread started. Print something:");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            condition.signal();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Second thread finished");
            lock.unlock();
        });

        firstThread.start();
        Thread.sleep(3000);
        secondThread.start();

        firstThread.join();
        secondThread.join();
    }
}
