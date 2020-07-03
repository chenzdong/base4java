package com.czd.thread.test;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * @author: czd
 * @create: 2020-06-05 13:30
 */
class ZeroEvenOdd {
    private int n;
    Semaphore semaphore4Zero = new Semaphore(1);
    Semaphore semaphore4Even = new Semaphore(0);
    Semaphore semaphore4Odd = new Semaphore(0);
    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        semaphore4Zero.acquire();
        printNumber.accept(0);

    }

    public void even(IntConsumer printNumber) throws InterruptedException {

    }

    public void odd(IntConsumer printNumber) throws InterruptedException {

    }
}
