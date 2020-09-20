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

package com.example.pages;

import static de.topobyte.jsoup.ElementBuilder.styleSheet;

import java.io.IOException;

import org.jsoup.nodes.Element;

import com.example.StaticFileMapping;

import de.topobyte.jsoup.FaviconUtil;
import de.topobyte.jsoup.HTML;
import de.topobyte.pagegen.core.BaseFileGenerator;
import de.topobyte.pagegen.core.Context;
import de.topobyte.webpaths.WebPath;
import de.topobyte.webpaths.WebPaths;

public class BaseGenerator extends BaseFileGenerator
{

	public BaseGenerator(Context context, WebPath path)
	{
		super(context, path);
	}

	@Override
	public void generate() throws IOException
	{
		super.generate();
		Element head = builder.getHead();

		content = getBuilder().getBody().ac(HTML.div());

		head.appendChild(styleSheet(getLink(
				WebPaths.get(StaticFileMapping.resolve("hashed.css")))));

		FaviconUtil.addToHeader(head,
				StaticFileMapping.resolve("images/favicon-16.png"), 16);
		FaviconUtil.addToHeader(head,
				StaticFileMapping.resolve("images/favicon-32.png"), 32);
		FaviconUtil.addToHeader(head,
				StaticFileMapping.resolve("images/favicon-48.png"), 48);
		FaviconUtil.addToHeader(head,
				StaticFileMapping.resolve("images/favicon-64.png"), 64);
		FaviconUtil.addToHeader(head,
				StaticFileMapping.resolve("images/favicon-96.png"), 96);
	}

}
