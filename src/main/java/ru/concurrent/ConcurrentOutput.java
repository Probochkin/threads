package ru.concurrent;

public class ConcurrentOutput {

    public static void main(String[] args) {
        another();
        second();
        System.out.println("работа завершена");
    }
    public static void another() {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start();
        while (another.getState() != Thread.State.TERMINATED) {
        }
    }
    public static void second() {
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
        while (second.getState() != Thread.State.TERMINATED) {

        }
    }
}
