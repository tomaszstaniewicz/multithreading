package wait_notify;

import java.util.Scanner;

class Processor {

    synchronized void consume() {
        System.out.println("Consume code entered (1)");
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Consume code resumed...(4)");
    }

    synchronized void produce() {
        System.out.println("Produce code entered (2)");
        Scanner scanner = new Scanner(System.in);
        scanner.next();
        notify();
        System.out.println("Produce code finished (3)");
    }
}
