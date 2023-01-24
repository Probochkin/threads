package ru.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    public static final int BUFFER = 1024;
    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
        FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[speed];
            int bytesRead;
            int downloadData = 0;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, BUFFER)) != -1) {
                downloadData += bytesRead;
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                if (downloadData >= speed) {
                    long workTime = System.currentTimeMillis() - startTime;
                    if (workTime < 1000) {
                        Thread.sleep(1000 - workTime);
                    }
                    downloadData = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        if (!args[0].startsWith("https://") && !args[0].isBlank() ) {
            throw new  IllegalArgumentException("Не верная ссылка");
        }
        if (Integer.parseInt(args[1]) < 1 ) {
            throw new  IllegalArgumentException("Не верно указана скорость.Необходимо указать сколько скачивать байт в секунду");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}

