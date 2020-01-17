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
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.topobyte.cachebusting.CacheBusting;

@WebFilter("/*")
public class RootFilter implements Filter
{

	private static Set<String> staticFiles = new HashSet<>();

	static {
		for (String entry : CacheBusting.getValues()) {
			staticFiles.add("/" + entry);
		}
		staticFiles.add("/nohash.css");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		// nothing to do here
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException
	{
		if (request instanceof HttpServletRequest) {
			HttpServletRequest hsr = (HttpServletRequest) request;

			String path = hsr.getRequestURI();

			if (staticFiles.contains(path)) {
				HttpServletResponse r = (HttpServletResponse) response;
				r.setHeader("Cache-Control", "public, max-age=31536000");
				chain.doFilter(request, response);
				return;
			}

			// Normalize multiple consecutive forward slashes to single slashes
			if (path.contains("//")) {
				String newUri = path.replaceAll("/+/", "/");
				((HttpServletResponse) response).sendRedirect(newUri);
				return;
			}

			request.setCharacterEncoding("UTF-8");
			request.getRequestDispatcher("/pages" + path).forward(request,
					response);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy()
	{
	}

}
