package com.czd.concurrent.testQueue;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * LinkedBlockingQueue实现
 *
 * @author: czd
 * @create: 2019/2/28 14:13
 */
public class LinkedBlockingQueueTest<E> extends AbstractQueue<E>
        implements BlockingQueue<E>, java.io.Serializable  {


    private static final long serialVersionUID = -4053982802608631065L;

    private final int capacity;

    private final AtomicInteger count = new AtomicInteger();

    private final ReentrantLock takeLock = new ReentrantLock();
    private final Condition notEmpty = takeLock.newCondition();

    private final ReentrantLock putLock = new ReentrantLock();
    private final Condition notFull = putLock.newCondition();
    /**
     * 内部节点
     */
    static class Node<E> {
        E item;
        Node<E> next;
        Node(E x) {
            item = x;
        }
    }
    transient Node<E> head;
    private transient Node<E> last;

    public LinkedBlockingQueueTest(int capacity) {
        if (capacity <= 0) {
            throw  new IllegalArgumentException();
        }
        this.capacity = capacity;
        last = head = new Node<E>(null);
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return count.get();
    }

    @Override
    public void put(E e) throws InterruptedException {
        if (e == null) {
            throw  new NullPointerException();
        }
        // 记录操作后的队列数量
        int c = -1;
        Node<E> node = new Node<>(e);
        final ReentrantLock putLock = this.putLock;
        final AtomicInteger count = this.count;
        putLock.lockInterruptibly();
        try {
            while (count.get() == capacity) {
                notFull.await();
            }
            insert(node);
            c = count.getAndIncrement();
            if ( c + 1 < capacity) {
                notFull.signal();
            }
        } finally {
            putLock.unlock();
        }
        // Todo WHY C MUST EQUAL TO 0 ?
        if (c == 0) {
            signalNotEmpty();
        }
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        if (e == null) {
            throw new NullPointerException();
        }
        final AtomicInteger count = this.count;
        if (count.get() == capacity) {
            return false;
        }
        int c = -1;
        long nanos = unit.toNanos(timeout);
        final ReentrantLock putLock = this.putLock;
        putLock.lockInterruptibly();
        try {
            while (count.get() == capacity) {
                notFull.awaitNanos(nanos);
            }
            insert(new Node<>(e));
            c = count.getAndIncrement();
            if (c + 1 < capacity) {
                notFull.signal();
            }
        } finally {
            putLock.unlock();
        }
        if (c == 0) {
            signalNotEmpty();
        }
        return true;
    }

    @Override
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        final AtomicInteger count = this.count;
        if (count.get() == capacity) {
            return false;
        }
        int c = -1;
        Node<E> node = new Node<>(e);
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            if (count.get() < capacity) {
                insert(node);
                c = count.getAndIncrement();
                if (c + 1 < capacity) {
                    notFull.signal();
                }

            }
        } finally {
            putLock.unlock();
        }
        if (c == 0) {
            signalNotEmpty();
        }
        return c >= 0;
    }

    public void insert(Node<E> node) {
        last = last.next;
        last = node;
    }
    public E extract() {
        Node<E> h = this.head;
        Node<E> first = head.next;
        h.next = h;
        head = first;
        E x = first.item;
        first.item = null;
        return x;
    }
    @Override
    public E take() throws InterruptedException {
        E x;
        int c = -1;
        final ReentrantLock takeLock = this.takeLock;
        final AtomicInteger count = this.count;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                notEmpty.await();
            }
            x = (E)extract();
            c = count.getAndDecrement();
            if (c - 1 > 0) {
                notEmpty.signal();
            }
        } finally {
            takeLock.unlock();
        }
        if (c == capacity) {
            signalNotFull();
        }
        return x;

    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        E x = null;
        int c = -1;
        final ReentrantLock takeLock = this.takeLock;
        final AtomicInteger count = this.count;
        takeLock.lockInterruptibly();
        try {
            while (count.get() == 0) {
                if (nanos <= 0 ) {
                    return null;
                }
                notEmpty.awaitNanos(nanos);
            }
            x = (E)extract();
            c = count.getAndDecrement();
            if (c  > 1) {
                notEmpty.signal();
            }
        } finally {
            takeLock.unlock();
        }
        if (c == capacity) {
            signalNotFull();
        }
        return x;

    }

    @Override
    public E poll() {
        final AtomicInteger count = this.count;
        if (count.get() == 0) {
            return null;
        }
        final ReentrantLock takeLock = this.takeLock;
        int c = -1;
        E x = null;
        takeLock.lock();
        try {
            if (count.get() > 0) {
                x = (E) extract();
                c = count.getAndDecrement();
                if (c > 1) {
                    notEmpty.signal();
                }
            }
        } finally {
            takeLock.unlock();
        }
        if (c == capacity) {
            signalNotFull();
        }
        return x;
    }

    @Override
    public E peek() {
        final AtomicInteger count = this.count;
        if (count.get() == 0) {
            return null;
        }
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            Node<E> first = head.next;
            if (first == null) {
                return null;
            } else {
                return first.item;
            }
        } finally {
            takeLock.unlock();
        }
    }

    @Override
    public int remainingCapacity() {
        return capacity - count.get();
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        return 0;
    }
    private void signalNotEmpty() {
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }

    /**
     * Signals a waiting put. Called only from take/poll.
     */
    private void signalNotFull() {
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }

}
