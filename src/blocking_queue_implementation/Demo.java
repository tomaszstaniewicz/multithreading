package blocking_queue_implementation;

public class Demo {

    public static void main(String[] args) {

        ExampleBlockingQueue blockingQueue = new ExampleBlockingQueue(10);

        Thread producerThread = new Thread(() -> {
            int value = 0;
            while (true) {
                blockingQueue.add(value++);
            }
        });

        Thread consumerThread = new Thread(() -> {
            while (true) {
                Integer consumedValue = blockingQueue.poll();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producerThread.start();
        consumerThread.start();
    }
}
