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

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class SourceGenerator {

    public StringBuilder createSource(String packageName, String className,
                                       List<Path> mappedFiles, Map<Path, Path> map)
            throws IOException {
        FieldSpec fMap = FieldSpec.builder(ParameterizedTypeName.get(
                Map.class, String.class, String.class), "map")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .initializer("new $T<>()", HashMap.class)
                .build();

        CodeBlock.Builder initMap = CodeBlock.builder();

        for (Path path : mappedFiles) {
            Path mappedTo = map.get(path);
            initMap.addStatement("map.put($S, $S)", path, mappedTo);
        }

        MethodSpec resolve = MethodSpec.methodBuilder("resolve")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(String.class, "filename")
                .returns(String.class)
                .addStatement("return map.get(filename)")
                .build();

        MethodSpec getEntries = MethodSpec.methodBuilder("getEntries")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(Set.class, String.class))
                .addStatement("return $T.unmodifiableSet(map.keySet())", Collections.class)
                .build();

        MethodSpec getValues = MethodSpec.methodBuilder("getValues")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(Collection.class, String.class))
                .addStatement("return $T.unmodifiableCollection(map.values())", Collections.class)
                .build();

        TypeSpec c = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addField(fMap)
                .addStaticBlock(initMap.build())
                .addMethod(resolve)
                .addMethod(getEntries)
                .addMethod(getValues)
                .build();

        JavaFile java = JavaFile.builder(packageName, c)
                .indent("    ")
                .addFileComment("this file is generated, do not edit")
                .build();

        StringBuilder buffer = new StringBuilder();
        java.writeTo(buffer);
        return buffer;
    }

}
