package ru;

import org.junit.jupiter.api.Test;
import java.util.stream.IntStream;


import static org.assertj.core.api.Assertions.assertThat;


class CASCountTest {

    @Test
    public void whenExecute10ThreadThen2() throws InterruptedException {
        CASCount count = new CASCount();
        Thread first = new Thread(() -> IntStream.range(0, 5).forEach(i -> count.increment()));
        Thread second = new Thread(() -> IntStream.range(0, 5).forEach(i -> count.increment()));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(count.get()).isEqualTo(10);
    }
}