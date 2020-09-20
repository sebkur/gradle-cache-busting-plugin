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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.pages.IndexGenerator;

import de.topobyte.jsoup.ContentGeneratable;
import de.topobyte.jsoup.JsoupServletUtil;
import de.topobyte.webpaths.WebPath;
import de.topobyte.webpaths.WebPaths;

@WebServlet("/pages/*")
public class IndexServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		// Remove "/pages" prefix
		String uri = request.getRequestURI().substring(6);
		WebPath path = WebPaths.get(uri);

		System.out.println("URI: " + uri);
		System.out.println("Path: " + path);

		WebContext context = new WebContext();

		ContentGeneratable generator = null;

		if (path.toString().equals("")) {
			generator = new IndexGenerator(context, path);
		}

		if (generator != null) {
			JsoupServletUtil.respond(response, generator);
		} else {
			ServletUtil.respond404(context, path, response);
		}
	}

}
