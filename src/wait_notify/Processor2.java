package wait_notify;

import java.util.Scanner;

class Processor2 {

    void consume() {
        synchronized (this) {
            System.out.println("Consume code entered (1)");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Consume code resumed...(4)");
        }
    }

    void produce() {
        synchronized (this) {
            System.out.println("Produce code entered (2)");
            Scanner scanner = new Scanner(System.in);
            scanner.next();
            this.notify();
            System.out.println("Produce code finished (3)");
        }
    }
}
