package com.sjcdigital.temis.model.service.parsers.impl;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.model.document.Alderman;
import com.sjcdigital.temis.model.service.parsers.AbstractParser;

/**
 *
 * @author pedro-hos
 *
 */
@Component
public class AldermenParser extends AbstractParser {

	private static final Logger LOGGER = LogManager.getLogger(AldermenParser.class);

	@Override
	public void parse(final File file) {

		try {

			final Document document = readFile(file).get();
			final Alderman alderman = extractAldermenInfo(document.select("div.row.info")); // <div class="row info">
			LOGGER.info(alderman.toString() + "\n");

		} catch (InterruptedException | ExecutionException | IOException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}

	}

	protected Alderman extractAldermenInfo(final Elements elements) {

		final Alderman alderman = new Alderman();

		for (final Element element : elements) {

			final Elements elementsInfo = element.select("div.col-sm-7.texto");

			alderman.setName(elementsInfo.select("h3").text());
			alderman.setEmail(getElementValue(elementsInfo, "E-mail").nextElementSibling().text());
			alderman.setInfo(getElementValue(elementsInfo, "Dados Pessoais").nextElementSibling().text());
			alderman.setLegislature(getElementValue(elementsInfo, "Legislatura").nextSibling().toString());
			alderman.setPhone(getElementValue(elementsInfo, "Telefone").nextSibling().toString());
			alderman.setPoliticalParty(elementsInfo.select("h3").first().nextElementSibling().text());
			alderman.setWorkplace(getElementValue(elementsInfo, "Local de Trabalho").nextSibling().toString());
			alderman.setPhoto(element.getElementsByClass("img-responsive").attr("src"));

		}

		return alderman;

	}

	protected Element getElementValue(final Elements elementsInfo, final String key) {
		return elementsInfo.select("h4:contains(" + key + ")").first();
	}

}
