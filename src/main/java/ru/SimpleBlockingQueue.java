package ru;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
   private int  limit = 3;

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= limit) {
            this.wait();
        }
        queue.add(value);
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
           while (queue.isEmpty()) {
               this.wait();
           }
        T rsl = queue.poll();
        this.notifyAll();
        return rsl;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}