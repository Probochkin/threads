package ru.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<V> extends RecursiveTask<Integer> {
    private final V[] array;
    private final V value;
    private  final int from;
    private  final int to;
    private ParallelSearchIndex(V[] array, V index, int from, int to) {
        this.array = array;
        this.value = index;
        this.from = from;
        this.to = to;
    }
    @Override
    protected Integer compute() {
        if (to - from < 10) {
            return linearSearch();
        }
        int mid = (from + to) / 2;
        ParallelSearchIndex<V> leftSort = new ParallelSearchIndex(array, value, from, mid);
        ParallelSearchIndex<V> rightSort = new ParallelSearchIndex(array, value, mid + 1, to);
        leftSort.fork();
        rightSort.fork();
        int left = leftSort.join();
        int right = rightSort.join();
        return Math.max(left, right);
    }
    public static <V> Integer find(V[] array, V value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearchIndex<V>(array, value, 0, array.length - 1));
    }
    private int linearSearch() {
        for (int i = from; i <= to; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }
}
