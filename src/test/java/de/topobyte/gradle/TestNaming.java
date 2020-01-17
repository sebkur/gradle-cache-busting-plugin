// Copyright 2020 Sebastian Kuerten
//
// This file is part of gradle-cache-busting-plugin.
//
// gradle-cache-busting-plugin is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// gradle-cache-busting-plugin is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with gradle-cache-busting-plugin. If not, see <http://www.gnu.org/licenses/>.

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
