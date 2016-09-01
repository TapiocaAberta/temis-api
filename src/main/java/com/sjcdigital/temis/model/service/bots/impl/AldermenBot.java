package com.sjcdigital.temis.model.service.bots.impl;

import com.sjcdigital.temis.model.exceptions.BotException;
import com.sjcdigital.temis.model.repositories.AldermanRepository;
import com.sjcdigital.temis.model.service.bots.AbstractBot;
import com.sjcdigital.temis.util.File;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author pedro-hos
 *
 */

@Component
@Order(1)
public class AldermenBot extends AbstractBot {

	private static final Logger LOGGER = LogManager.getLogger(AldermenBot.class);

	@Value("${url.aldermen}")
	private String aldermenUrl;

	@Value("${path.leis}")
	private String path;

	@Autowired
	private File file;

	@Autowired
	private AldermanRepository aldermanRepository;

	public void saveData() throws BotException {

		if (aldermanRepository.count() != 0) {
			return;
		}

		final Collection<String> allLinks = getAldermenLinks();

		try {

			for (final String link : allLinks) {
				final Document document = Optional.ofNullable(getPage(link).get()).orElseThrow(BotException::new);
				file.createFile(getPath(), document.html(), getFileName(link), LocalDate.now().getYear());
			}

		} catch (IOException | InterruptedException | ExecutionException exception) {
			LOGGER.error(ExceptionUtils.getStackTrace(exception));
		}

	}

	private Collection<String> getAldermenLinks() throws BotException {

		final Collection<String> links = new HashSet<>();

		try {

			final Document document = Optional.ofNullable(getPage(aldermenUrl).get()).orElseThrow(BotException::new);
			final Elements divsBack = document.getElementsByClass("back"); // <div
			// class="back">

			for (final Element element : divsBack) {
				element.select("a").stream().map(l -> l.attr("href")).peek(System.out::println).forEach(links::add); // <a
				// href="http://..."><a/>
			}

		} catch (IOException | InterruptedException | ExecutionException exception) {
			LOGGER.error(ExceptionUtils.getStackTrace(exception));
		}

		return links;
	}

	private String getFileName(final String link) {
		final String[] split = link.split("/");
		return split[split.length - 1];
	}

	@Override
	protected String getPath() {
		return path.concat("vereadores/");
	}

}
