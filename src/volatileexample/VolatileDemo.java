package volatileexample;

import java.util.Scanner;

class Processor extends Thread {

    private volatile boolean isShutdown = false;

    @Override
    public void run() {

        // if isShutdown is not volatile JIT compiler can optimize this check
        // and will be replaced with while(true).
        while (!isShutdown) {
            System.out.println("Thead is still running");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void shutdown() {
        this.isShutdown = true;
    }
}

public class VolatileDemo {

    public static void main(String[] args) {
        Processor processor = new Processor();
        processor.start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        processor.shutdown();
    }

}
