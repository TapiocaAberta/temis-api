package com.sjcdigital.temis.model.service.bots;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Future;

/**
 * @author pedro-hos
 *
 */
public abstract class AbstractBot implements Bot {

	private static final String AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) "
			+ "Chrome/33.0.1750.152 Safari/537.36";

	@Async
	protected Future<Document> getPage(final String url) throws IOException {

		final URLConnection urlConnection = new URL(url).openConnection();
		urlConnection.addRequestProperty("User-Agent", AGENT);

		final InputStream openStream = urlConnection.getInputStream();
		final Document page = Jsoup.parse(openStream, "ISO-8859-9", url);

		return new AsyncResult<Document>(page);
	}

	protected abstract String getPath();

}
