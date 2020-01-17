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

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSourceGenerator {

    public static void main(String[] args) throws IOException {
        String packageName = "de.topobyte.cachebusting";
        String className = "CacheBusting";

        List<Path> mappedFiles = new ArrayList<>();
        Map<Path, Path> map = new HashMap<>();

        mappedFiles.add(Paths.get("test.png"));
        mappedFiles.add(Paths.get("test.css"));
        map.put(Paths.get("test.png"), Paths.get("test-1234.png"));
        map.put(Paths.get("test.css"), Paths.get("test-1234.css"));

        StringBuilder buffer = new SourceGenerator().createSource(packageName,
                className, mappedFiles, map);
        System.out.println(buffer);
    }

}
