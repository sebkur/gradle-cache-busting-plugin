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

import de.topobyte.melon.paths.PathUtil;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.Project;
import org.gradle.api.internal.ConventionTask;
import org.gradle.api.tasks.TaskAction;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateStaticFilesTask extends ConventionTask {

    private CacheBustingPluginExtension extension;

    public GenerateStaticFilesTask() {
        setGroup("build");
    }

    public void setConfiguration(CacheBustingPluginExtension extension) {
        this.extension = extension;
    }

    @TaskAction
    protected void copyFiles() throws IOException {
        Project project = getProject();

        if (extension.getInput() == null) {
            throw new InvalidUserDataException("You need to specify the input files via 'input'");
        }

        System.out.println("Generating files from input directories:");

        List<String> dirNames = extension.getInput();
        for (String dirName : dirNames) {
            System.out.println("dir: " + dirName);
        }

        Path projectDir = project.getProjectDir().toPath();
        Path buildDir = project.getBuildDir().toPath();

        Path output = buildDir.resolve("static");

        List<Path> mappedFiles = new ArrayList<>();
        Map<Path, Path> map = new HashMap<>();

        for (String dirName : dirNames) {
            System.out.println("processing dir: " + dirName);
            Path dir = projectDir.resolve(dirName);
            List<Path> files = PathUtil.findRecursive(dir, "*", true);
            for (Path file : files) {
                Path relative = dir.relativize(file);
                if (!Files.isRegularFile(file)) {
                    continue;
                }
                System.out.println("file: " + relative);

                String hash = Util.hash(file);
                Path altered = Util.changeName(relative, hash);

                Path target = output.resolve(altered);

                Files.createDirectories(target.getParent());
                Files.copy(file, target, StandardCopyOption.REPLACE_EXISTING);

                mappedFiles.add(relative);
                map.put(relative, altered);
            }
        }

        generateSource(project, mappedFiles, map);
    }

    private void generateSource(Project project, List<Path> mappedFiles, Map<Path, Path> map) throws IOException {
        String packageName = "de.topobyte.cachebusting";
        String className = "CacheBusting";
        String[] parts = packageName.split("\\.");

        Path pathBuildDir = project.getBuildDir().toPath();
        Path source = Util.getSourceDir(pathBuildDir);

        Path path = source;
        for (int i = 0; i < parts.length; i++) {
            path = path.resolve(parts[i]);
        }

        Files.createDirectories(path);
        Path file = path.resolve(className + ".java");

        StringBuilder buffer = new SourceGenerator().createSource(packageName,
                className, mappedFiles, map);

        InputStream in = new ByteArrayInputStream(
                buffer.toString().getBytes(StandardCharsets.UTF_8));
        Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
    }

}
