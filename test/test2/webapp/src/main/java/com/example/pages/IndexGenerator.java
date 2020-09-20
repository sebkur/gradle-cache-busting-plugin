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

import java.io.IOException;

import com.example.StaticFileMapping;

import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.components.P;
import de.topobyte.pagegen.core.Context;
import de.topobyte.webpaths.WebPath;

public class IndexGenerator extends BaseGenerator
{

	public IndexGenerator(Context context, WebPath path)
	{
		super(context, path);
	}

	@Override
	public void generate() throws IOException
	{
		super.generate();

		getBuilder().getTitle().appendText("Example webapp");

		content.ac(HTML.h1("Example webapp"));
		P p = content.ac(HTML.p());
		p.appendText(
				"A simple webapp that demonstrates cache busting capabilities");

		p = content.ac(HTML.p());
		p.addClass("styled");
		p.appendText("This paragraph is styled from file hashed.css");

		content.ac(HTML.img(StaticFileMapping.resolve("images/image.png")));
	}

}
