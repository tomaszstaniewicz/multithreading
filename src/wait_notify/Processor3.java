package wait_notify;

import java.util.Scanner;

class Processor3 {

    private final Object lock = new Object();

    void consume() {
        synchronized (lock) {
            System.out.println("Consume code entered (1)");
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Consume code resumed...(4)");
        }
    }

    void produce() {
        synchronized (lock) {
            System.out.println("Produce code entered (2)");
            Scanner scanner = new Scanner(System.in);
            scanner.next();
            // notify makes sense only when called on object on which code section is synchronized
            lock.notify();
            System.out.println("Produce code finished (3)");
        }
    }
}
