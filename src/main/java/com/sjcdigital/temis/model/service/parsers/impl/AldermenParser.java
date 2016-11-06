package com.sjcdigital.temis.model.service.parsers.impl;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.model.document.Alderman;
import com.sjcdigital.temis.model.repositories.AldermanRepository;
import com.sjcdigital.temis.model.service.parsers.AbstractParser;
import com.sjcdigital.temis.util.StringUtil;
import com.sjcdigital.temis.util.TemisFileUtil;

/**
 * @author pedro-hos
 */

@Component
public class AldermenParser extends AbstractParser {

	private static final Logger LOGGER = LogManager.getLogger(AldermenParser.class);
	
	@Value("${url.context}")
	private String urlContext;

	@Value("${path.webapp}")
	private String pathWebapp;
	
	@Value("${path.images}")
	private String pathImages;

	@Autowired
	private AldermanRepository aldermanRepository;
	
	@Autowired
	private TemisFileUtil fileUtil;
	
	@Override
	public void parse(final File file) {

		try {

			final Document document = readFile(file).get();
			final Alderman alderman = extractAldermenInfo(document.select("div.row.info")); // <div class="row info">
			saveOrUpdate(alderman);

		} catch (InterruptedException | ExecutionException | IOException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}

	}

	protected Alderman extractAldermenInfo(final Elements elements) {

		final Alderman alderman = new Alderman();

		for (final Element element : elements) {

			final Elements elementsInfo = element.select("div.col-sm-7.texto");

			String politicianName = elementsInfo.select("h3").text().trim();
			alderman.setName(politicianName);
			alderman.setEmail(getElementValue(elementsInfo, "E-mail").nextElementSibling().text().trim());
			alderman.setInfo(getElementValue(elementsInfo, "Dados Pessoais").nextElementSibling().text().trim());
			alderman.setLegislature(getElementValue(elementsInfo, "Legislatura").nextSibling().toString().trim());
			alderman.setPhone(getElementValue(elementsInfo, "Telefone").nextSibling().toString().trim());
			alderman.setPoliticalParty(extractedPoliticalParty(elementsInfo).trim());
			alderman.setWorkplace(getElementValue(elementsInfo, "Local de Trabalho").nextSibling().toString().trim());
			alderman.setPhoto(createPhoto(element.getElementsByClass("img-responsive").attr("src").trim(), politicianName));

		}

		return alderman;

	}

	protected String createPhoto(String url, String politicianName) {
		politicianName = StringUtil.unaccent(politicianName.toLowerCase().replaceAll(" ", "_"));
		String fullImagePath = pathImages.concat(politicianName).concat(".jpg");
		fileUtil.savePhoto(url, pathWebapp.concat(fullImagePath));
		return urlContext.concat(fullImagePath);
	}

	protected String extractedPoliticalParty(final Elements elements) {
		return elements.select("h3").first().nextElementSibling().text().replaceAll("Partido: ", "");
	}

	protected Element getElementValue(final Elements elementsInfo, final String key) {
		return elementsInfo.select("h4:contains(" + key + ")").first();
	}

	protected void saveOrUpdate(final Alderman aldermanToSave) {

        final Optional<Alderman> alderman = aldermanRepository.findByName(aldermanToSave.getName());

        if (alderman.isPresent()) {
            aldermanToSave.setId(alderman.get().getId());
            aldermanToSave.setLawsCount(alderman.get().getLawsCount());
            aldermanRepository.save(aldermanToSave);

        } else {
            aldermanRepository.save(aldermanToSave);
        }

    }

}
