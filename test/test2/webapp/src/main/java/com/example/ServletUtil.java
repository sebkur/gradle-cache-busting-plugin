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

package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.nodes.Document;

import de.topobyte.jsoup.nodes.Element;
import de.topobyte.pagegen.core.Context;
import de.topobyte.webpaths.WebPath;

public class ServletUtil
{

	public static void respond404(Context context, WebPath output,
			HttpServletResponse response) throws IOException
	{
		respond(404, context, output, response, content -> {
			ErrorUtil.write404(content);
		});
	}

	public static void respond404(Context context, WebPath output,
			HttpServletResponse response, Consumer<Element> contentGenerator)
			throws IOException
	{
		respond(404, context, output, response, contentGenerator);
	}

	public static void respond(int code, Context context, WebPath output,
			HttpServletResponse response, Consumer<Element> contentGenerator)
			throws IOException
	{
		response.setStatus(code);

		response.setCharacterEncoding("UTF-8");

		PrintWriter writer = response.getWriter();

		ErrorGenerator generator = new ErrorGenerator(context, output);
		generator.generate();
		Element content = generator.getContent();

		contentGenerator.accept(content);

		Document document = generator.getBuilder().getDocument();
		writer.write(document.toString());

		writer.close();
	}

}
