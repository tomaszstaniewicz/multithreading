package blocking_queue_implementation;

import java.util.LinkedList;

public class ExampleBlockingQueue {

    private final int LIMIT;
    private final LinkedList<Integer> queue;

    private final Object lock = new Object();

    public ExampleBlockingQueue(int size) {
        this.queue = new LinkedList<>();
        this.LIMIT = size;
    }

    public void add(Integer elem) {
        synchronized (lock) {
            while (queue.size() == LIMIT) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            queue.add(elem);
            lock.notify();
        }
    }

    public Integer poll() {
        synchronized (lock) {
            while (queue.size() == 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Integer firstElem = queue.removeFirst();
            System.out.println("Polled element: " + firstElem + ". Queue size is: " + queue.size());

            lock.notify();
            return firstElem;
        }
    }
}
