package ru.pool;


import ru.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();
    public ThreadPool () {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {

            threads.add(new Thread (() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }}));
        }
        threads.forEach(Thread::start);
    }
    public void work(Runnable job) throws InterruptedException {

            tasks.offer(job);

    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        threadPool.work(new Thread(() -> System.out.println("Первая")));
        threadPool.work(new Thread(() -> System.out.println("Вторая")));
        threadPool.work(new Thread(() -> System.out.println("Третья")));
        threadPool.shutdown();
    }
}
