package deadlocks;

import java.util.SplittableRandom;
import java.util.concurrent.locks.ReentrantLock;

// here we have no deadlock because both threads acquire locks in the same order
class NoDeadLockDemo {

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock account1Lock = new ReentrantLock();
        ReentrantLock account2Lock = new ReentrantLock();

        SplittableRandom random = new SplittableRandom();

        Account account1 = new Account();
        Account account2 = new Account();

        account1.deposit(10000);
        account2.deposit(10000);

        System.out.println("Balance before transfers: " + (account1.getBalance() + account2.getBalance()));

        Thread fromAccount1ToAccount2TransferThread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                account1Lock.lock();
                account2Lock.lock();
                Account.transfer(account1, account2, random.nextInt(10));
                account2Lock.unlock();
                account1Lock.unlock();
            }
        });

        Thread fromAccount2ToAccount1TransferThread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                account1Lock.lock();
                account2Lock.lock();
                Account.transfer(account2, account1, random.nextInt());
                account2Lock.unlock();
                account1Lock.unlock();
            }
        });

        fromAccount1ToAccount2TransferThread.start();
        fromAccount2ToAccount1TransferThread.start();

        fromAccount1ToAccount2TransferThread.join();
        fromAccount2ToAccount1TransferThread.join();

        System.out.println("Balance after transfers: " + (account1.getBalance() + account2.getBalance()));
    }

}

// here we have deadlock because two threads acquire locks in defferent order
class DeadLockDemo {

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock account1Lock = new ReentrantLock();
        ReentrantLock account2Lock = new ReentrantLock();

        SplittableRandom random = new SplittableRandom();

        Account account1 = new Account();
        Account account2 = new Account();

        account1.deposit(10000);
        account2.deposit(10000);

        System.out.println("Balance before transfers: " + (account1.getBalance() + account2.getBalance()));

        Thread fromAccount1ToAccount2TransferThread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                account1Lock.lock();
                account2Lock.lock();
                Account.transfer(account1, account2, random.nextInt(10));
                account2Lock.unlock();
                account1Lock.unlock();
            }
        });

        Thread fromAccount2ToAccount1TransferThread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                account2Lock.lock();
                account1Lock.lock();
                Account.transfer(account2, account1, random.nextInt());
                account1Lock.unlock();
                account2Lock.unlock();
            }
        });

        fromAccount1ToAccount2TransferThread.start();
        fromAccount2ToAccount1TransferThread.start();

        fromAccount1ToAccount2TransferThread.join();
        fromAccount2ToAccount1TransferThread.join();

        System.out.println("Balance after transfers: " + (account1.getBalance() + account2.getBalance()));
    }

}

// lock acquire order solved with ReentrantLock.tryLock
class TryLockDemo {

    private static void acquireLocks(ReentrantLock lock1, ReentrantLock lock2) {

        boolean gotLock1 = false;
        boolean gotLock2 = false;

        while (true) {
            gotLock1 = lock1.tryLock();
            gotLock2 = lock2.tryLock();

            if (gotLock1 && gotLock2) {
                return;
            }

            if (gotLock1) lock1.unlock();
            if (gotLock2) lock2.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock account1Lock = new ReentrantLock();
        ReentrantLock account2Lock = new ReentrantLock();

        SplittableRandom random = new SplittableRandom();

        Account account1 = new Account();
        Account account2 = new Account();

        account1.deposit(10000);
        account2.deposit(10000);

        System.out.println("Balance before transfers: " + (account1.getBalance() + account2.getBalance()));

        Thread fromAccount1ToAccount2TransferThread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                acquireLocks(account1Lock, account2Lock);
                Account.transfer(account1, account2, random.nextInt(10));
                account2Lock.unlock(); account1Lock.unlock();
            }
        });

        Thread fromAccount2ToAccount1TransferThread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                acquireLocks(account2Lock, account1Lock);
                Account.transfer(account2, account1, random.nextInt(10));
                account2Lock.unlock(); account1Lock.unlock();
            }
        });

        fromAccount1ToAccount2TransferThread.start();
        fromAccount2ToAccount1TransferThread.start();

        fromAccount1ToAccount2TransferThread.join();
        fromAccount2ToAccount1TransferThread.join();

        System.out.println("Balance of account 1: " + account1.getBalance());
        System.out.println("Balance of account 2: " + account2.getBalance());
        System.out.println("Balance after transfers: " + (account1.getBalance() + account2.getBalance()));
    }

}