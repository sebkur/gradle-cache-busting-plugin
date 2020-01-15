package de.topobyte.gradle;


import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestNaming {

    private static String emptyFileHash = "d41d8cd98f00b204e9800998ecf8427e";

    @Test
    public void test() throws IOException {
        String extension = ".css";
        Path file = Files.createTempFile("file", extension);
        String hash = Util.hash(file);
        Path newFile = Util.changeName(file, hash);

        String filename = file.getFileName().toString();
        String expectedFilename = filename.substring(0, filename.length() - 4)
                + "-" + emptyFileHash + extension;
        Path expectedFile = file.resolveSibling(expectedFilename);

        System.out.println("hashed filename:   " + newFile);
        System.out.println("expected filename: " + expectedFile);

        Assert.assertEquals(expectedFile, newFile);
        Files.delete(file);
    }

    @Test
    public void testNoExtension() throws IOException {
        Path dir = Files.createTempDirectory("directory");
        Path file = Files.createFile(dir.resolve("test"));
        String hash = Util.hash(file);
        Path newFile = Util.changeName(file, hash);

        String filename = file.getFileName().toString();
        String expectedFilename = filename + "-" + emptyFileHash;
        Path expectedFile = file.resolveSibling(expectedFilename);

        System.out.println("hashed filename:   " + newFile);
        System.out.println("expected filename: " + expectedFile);

        Assert.assertEquals(expectedFile, newFile);
        Files.delete(file);
        Files.delete(dir);
    }

}
