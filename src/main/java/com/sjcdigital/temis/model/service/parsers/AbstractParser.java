package com.sjcdigital.temis.model.service.parsers;

import java.io.File;
import java.io.IOException;
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

}
