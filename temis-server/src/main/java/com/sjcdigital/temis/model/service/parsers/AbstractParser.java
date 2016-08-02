package com.sjcdigital.temis.model.service.parsers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Future;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

/**
 * @author pedro-hos
 */
public abstract class AbstractParser {
	
	public abstract void parse(File file);
	
	@Async
	protected Future<Document> readFile(final File file) throws IOException {
		final Document document = Jsoup.parse(file, "UTF-8");
		return new AsyncResult<Document>(document);
	}

	public static void main(final String[] args) throws IOException {

		final String AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) "
				+ "Chrome/33.0.1750.152 Safari/537.36";


		final String url = "http://www.ceaam.net/sjc/legislacao/leis/2013/L8871.htm";

		final URLConnection urlConnection = new URL(url).openConnection();
		urlConnection.addRequestProperty("User-Agent", AGENT);

		final InputStream openStream = urlConnection.getInputStream();

		final Document document = Jsoup.parse(openStream, "ISO-8859-9", url);
		
		System.out.println(document.text());
		
		final Document page = Jsoup.connect(url)
				.header("", "ISO-8859-9")
				.userAgent(AGENT)
				.maxBodySize(0)
				.get();

		System.out.println(page.text());
	}
	
}
