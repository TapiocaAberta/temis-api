package com.sjcdigital.temis.bots;

import java.io.IOException;
import java.util.concurrent.Future;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

/**
 * @author pedro-hos
 *         
 */
public abstract class AbstractBot {
	
	private static final String AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) "
			+ "Chrome/33.0.1750.152 Safari/537.36";
	
	@Async
	protected Future<Document> getPage(final String url) throws IOException {
		Document page = Jsoup.connect(url).userAgent(AGENT).get();
		return new AsyncResult<Document>(page);
	}
	
}
