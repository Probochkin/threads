package ru.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelSearchIndexTest {
    @Test
    public void whenDifferentDataTypes() {
        Object[] array = {1, 2, "one", 4.45, 6.789, "two", 3, 4, "three", 3.14, "four", "five", true, 5};
        int ind = ParallelSearchIndex.find(array, "five");
        assertThat(ind).isEqualTo(11);
    }
    @Test
    public void whenSmallArray() {
        Object[] array = {1, 2, 2, 5};
        int ind = ParallelSearchIndex.find(array, 5);
        assertThat(ind).isEqualTo(3);
    }
    @Test
    public void whenBigArray() {
        Object[] array = new Object[100];
        for (int i = 0; i < 100; i++) {
            array[i] = i;
        }
        int ind = ParallelSearchIndex.find(array, 50);
        assertThat(ind).isEqualTo(50);
    }
    @Test
    public void whenValueNotFound() {
        Object[] array = {1, 2, 2, 5};
        int ind = ParallelSearchIndex.find(array, 11);
        assertThat(ind).isEqualTo(-1);
    }
}