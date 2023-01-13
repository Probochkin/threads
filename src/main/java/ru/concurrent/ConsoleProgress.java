package ru.concurrent;


public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        try {
        var process = new char[] {'-', '\\', '|', '/'};
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {

            if (i > process.length -1) {
                i = 0;
            }
            System.out.print("\r load: " + process[i]);
            i++;
            Thread.sleep(500);
        }
            } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            }


    }
    public static void main(String[] args)  throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        progress.sleep(5000); /* симулируем выполнение параллельной задачи в течение 5 секунд. */
        progress.interrupt();
    }



}
