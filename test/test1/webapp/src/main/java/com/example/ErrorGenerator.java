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

import com.example.pages.BaseGenerator;

import de.topobyte.pagegen.core.Context;
import de.topobyte.webpaths.WebPath;

public class ErrorGenerator extends BaseGenerator
{

	public ErrorGenerator(Context context, WebPath path)
	{
		super(context, path);
	}

	@Override
	public void generate() throws IOException
	{
		super.generate();
	}

}
