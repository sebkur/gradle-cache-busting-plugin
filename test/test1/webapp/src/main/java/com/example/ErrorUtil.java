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

import org.jsoup.nodes.Element;

import de.topobyte.jsoup.HTML;

public class ErrorUtil
{

	public static void write404(Element content)
	{
		content.appendChild(HTML.h1().text("Page not found"));
		content.appendText("That's an error (404)");
	}

	public static void writeError(Element content)
	{
		content.appendChild(HTML.h1().text("Something went wrong"));
		content.appendText("That's an error");
	}

}
