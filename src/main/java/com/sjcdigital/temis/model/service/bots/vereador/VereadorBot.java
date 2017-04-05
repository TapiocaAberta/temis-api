package com.sjcdigital.temis.model.service.bots.vereador;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sjcdigital.temis.annotations.Property;
import com.sjcdigital.temis.model.service.bots.AbstractBot;
import com.sjcdigital.temis.model.service.bots.exceptions.BotException;
import com.sjcdigital.temis.model.service.extrator.vereador.VereadorExtrator;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class VereadorBot extends AbstractBot {
	
	@Inject
	private Logger logger;
	
	@Inject
	@Property("url.vereadores")
	private String vereadoresUrl;
	
	@Inject
	private VereadorExtrator vereadorExtractor;
	
	@Override
	public void saveData() throws BotException {
		
		final Collection<String> allLinks = getAldermenLinks();

		try {

			for (final String link : allLinks) {
				final Document document = Optional.ofNullable(getPage(link)).orElseThrow(BotException::new);
				vereadorExtractor.parse(document);
			}

		} catch (IOException exception) {
			logger.severe(ExceptionUtils.getStackTrace(exception));
		}
	}

	
	private Collection<String> getAldermenLinks() throws BotException {

		final Collection<String> links = new HashSet<>();

		try {

			final Document document = Optional.ofNullable(getPage(vereadoresUrl)).orElseThrow(BotException::new);
			final Elements divsBack = document.getElementsByClass("back"); // <div
			// class="back">

			for (final Element element : divsBack) {
				element.select("a").stream().map(l -> l.attr("href")).forEach(links::add); // <a
				// href="http://..."><a/>
			}

		} catch (IOException exception) {
			logger.severe(ExceptionUtils.getStackTrace(exception));
		}

		return links;
	}

}
