package de.topobyte.gradle

import de.topobyte.melon.paths.PathUtil;
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.InvalidUserDataException

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class CacheBustingPlugin implements Plugin<Project> {
    void apply(Project project) {
        def extension = project.extensions.create('cacheBusting', CacheBustingPluginExtension)
        project.task('generateStaticFiles') {
            group = 'build'
            description = 'Generate static files with hashes in filenames'
            doLast {
                if (extension.input == null) {
                    throw new InvalidUserDataException("You need to specify the input file via 'input'")
                }
                println "Generating files from input files ${extension.input}"

                def projectDir = project.projectDir.toPath();
                def buildDir = project.buildDir.toPath()

                def output = buildDir.resolve("static");

                List<String> dirNames = extension.input;
                for (String dirName : dirNames) {
                    println "dir: " + dirName
                    def dir = projectDir.resolve(dirName);
                    def files = PathUtil.findRecursive(dir, "*", true);
                    for (Path file : files) {
                        def relative = dir.relativize(file)
                        if (!Files.isRegularFile(file)) {
                            continue;
                        }
                        println "file: " + relative
                        def target = output.resolve(relative)
                        Files.createDirectories(target.getParent())
                        Files.copy(file, target, StandardCopyOption.REPLACE_EXISTING)
                    }
                }
            }
        }
    }
}
