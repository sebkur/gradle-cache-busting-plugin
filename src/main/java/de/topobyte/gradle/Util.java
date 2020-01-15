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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Util
{

	public static Path getSourceDir(Path pathBuildDir)
	{
		return pathBuildDir.resolve("generatedSourceCacheBusting");
	}

	public static Path changeName(Path file, String hash) {
		String name = file.getFileName().toString();
		String newName;
		if (!name.contains(".")) {
			newName = name + "-" + hash;
		}else {
			int pos = name.lastIndexOf(".");
			String prefix = name.substring(0, pos);
			String extension = name.substring(pos);
			newName = prefix + "-" + hash + extension;
		}
		return file.resolveSibling(newName);
	}

	public static String hash(Path path) throws IOException {
		try (InputStream is = Files.newInputStream(path)) {
			String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
			return md5;
		}
	}

}
