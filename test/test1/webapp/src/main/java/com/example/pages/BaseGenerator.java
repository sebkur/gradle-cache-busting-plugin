package com.example.pages;

import static de.topobyte.jsoup.ElementBuilder.styleSheet;

import java.io.IOException;

import org.jsoup.nodes.Element;

import de.topobyte.cachebusting.CacheBusting;
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

		head.appendChild(styleSheet(
				getLink(WebPaths.get(CacheBusting.resolve("hashed.css")))));

		FaviconUtil.addToHeader(head,
				CacheBusting.resolve("images/favicon-16.png"), 16);
		FaviconUtil.addToHeader(head,
				CacheBusting.resolve("images/favicon-32.png"), 32);
		FaviconUtil.addToHeader(head,
				CacheBusting.resolve("images/favicon-48.png"), 48);
		FaviconUtil.addToHeader(head,
				CacheBusting.resolve("images/favicon-64.png"), 64);
		FaviconUtil.addToHeader(head,
				CacheBusting.resolve("images/favicon-96.png"), 96);
	}

}
