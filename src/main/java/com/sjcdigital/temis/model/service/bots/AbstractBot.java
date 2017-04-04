package com.sjcdigital.temis.model.service.bots;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.sjcdigital.temis.model.service.bots.exceptions.BotException;

/**
 * @author pedro-hos
 *
 */
public abstract class AbstractBot {
	
	private static final String AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) " 
									  + "Chrome/33.0.1750.152 Safari/537.36";

	protected Document getPage(final String url) throws IOException {

		final URLConnection urlConnection = new URL(url).openConnection();
		urlConnection.addRequestProperty("User-Agent", AGENT);

		final InputStream openStream = urlConnection.getInputStream();
		final Document page = Jsoup.parse(openStream, "ISO-8859-9", url);

		return page;
	}
	
	public abstract void saveData() throws BotException;

}
