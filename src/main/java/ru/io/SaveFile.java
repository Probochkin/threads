package ru.io;

import java.io.*;

public  final class SaveFile {

    private final File file;

    public SaveFile (File file) {
        this.file = file;
    }

    public synchronized File getFile() {
        return file;
    }

    public void saveContent(String content) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            out.write(content.getBytes());
        }
    }
}
