package wait_notify;

// implicitly locking on this object
class Demo1 {

    public static void main(String[] args) throws InterruptedException {
        Processor processor = new Processor();

        Thread consumerThread = new Thread(processor::consume);
        Thread producerThread = new Thread(processor::produce);

        consumerThread.start();
        producerThread.start();
    }
}

// explicitly locking on this object
class Demo2 {

    public static void main(String[] args) {
        Processor2 processor = new Processor2();

        Thread consumerThread = new Thread(processor::consume);
        Thread producerThread = new Thread(processor::produce);

        consumerThread.start();
        producerThread.start();
    }
}

// explicitly locking on separate lock object
class Demo3 {

    public static void main(String[] args) {
        Processor3 processor = new Processor3();

        Thread consumerThread = new Thread(processor::consume);
        Thread producerThread = new Thread(processor::produce);

        consumerThread.start();
        producerThread.start();
    }
}
