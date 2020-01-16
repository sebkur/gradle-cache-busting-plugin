package com.example.pages;

import java.io.IOException;

import de.topobyte.cachebusting.CacheBusting;
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

		content.ac(HTML.img(CacheBusting.resolve("images/image.png")));
	}

}
