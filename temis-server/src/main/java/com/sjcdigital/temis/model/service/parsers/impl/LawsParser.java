package com.sjcdigital.temis.model.service.parsers.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.model.document.Alderman;
import com.sjcdigital.temis.model.document.Law;
import com.sjcdigital.temis.model.repositories.AldermanRepository;
import com.sjcdigital.temis.model.repositories.LawsRepository;
import com.sjcdigital.temis.model.service.parsers.AbstractParser;

/**
 * @author pedro-hos
 */

@Component
public class LawsParser extends AbstractParser {

	private static final Logger LOGGER = LogManager.getLogger(LawsParser.class);

	@Autowired
	private LawsRepository lawsRepository;

	@Autowired
	private AldermanRepository aldermanRepository;

	@Override
	public void parse(final File file) {

		try {

			LOGGER.info("Parsing e Save: " + file.getName());

			final Document document = readFile(file).get();
			document.select("script").remove();
			document.select("href").remove();

			final Element body = document.body();

			final Law law = new Law();
			law.setDesc(body.html());
			law.setTitle(document.title());
			law.setAuthor(buildAuthor(body));
			law.setDate(buildDate(body));
			law.setCode(extractedCode(file));
			law.setProjectLawNumber(buildProjectLawNumber(body));

			saveLaw(law);

		} catch (InterruptedException | ExecutionException | IOException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}

	}

	private void saveLaw(final Law law) {
		if (!lawsRepository.findByCode(law.getCode()).isPresent()) {
			lawsRepository.save(law);
		}
	}

	private Optional<Alderman> getAlderman(final String authorName) {

		final Optional<Alderman> alderman = aldermanRepository.findByNameContainingIgnoreCase(authorName);

		if (alderman.isPresent()) {
			return alderman;

		} else {

			LOGGER.info("Author: " + authorName);

			final Alderman aldermanNotfound = new Alderman(authorName, true);
			return Optional.of(aldermanRepository.save(aldermanNotfound));

		}

	}

	private String extractedCode(final File file) {
		return file.getName().replace("L", "").replace(".html", "");
	}

	private String buildProjectLawNumber(final Element body) {

		final String regPub = body.getElementsByClass("RegPub").text();
		final Matcher matcher = getMatcher("nº [0-9]+/[0-9]+", regPub);

		if (matcher.find()) {
			return matcher.group(0);
		}

		return "";
	}

	private Collection<Alderman> buildAuthor(final Element body) {

		final String regPub = body.getElementsByClass("RegPub").text();
		final Matcher matcher = getMatcher("de autoria [A-Za-z]{0,3} ", regPub);

		if (matcher.find()) {

			final Collection<Alderman> aldermen = new HashSet<>();

			final String[] authors = regPub.substring(matcher.end()).toLowerCase().replaceAll("vereador(a|es|as){0,1}", "") // Ex. vereadores A, B, C
			        														   	  .replaceAll("(Pi|pi|PI)( )*(\\d+)*(-\\d+)*(/\\d+)*", "") //Ex. pi 1234-09/34
			        														      .replaceAll("professor", "") //Ex. Professor Calasans Camargo
			        														      .replaceAll("^ dr(a*)(\\.*)", "") //Ex. dr(a) e dr(a).
			        														      .replaceAll("mensagem .*", "") //Ex. mensagem 83/atl/14
			        														      .replaceAll("e outro(s{0,1})", "") //Ex. e outros
			        														      .replace(")", "")
			        														      .trim()
			        														      .split("(, | e )");

			for (final String author : authors) {
				aldermen.add(getAlderman(WordUtils.capitalize(author)).get());
			}

			return aldermen;

		}

		return null;
	}

	private LocalDate buildDate(final Element body) {

		final String footerText = body.getElementsByClass("RP").text();
		final Matcher matcher = getMatcher("[0-9]{1,2} de [a-zA-Z]+ de [0-9]{4}", footerText);

		if (matcher.find()) {

			final String de = " de ";
			final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "br"));
			final String[] dateSplit = matcher.group(0).split(de);
			final String day = StringUtils.leftPad(dateSplit[0].trim(), 2, "0");
			final String month = WordUtils.capitalize(dateSplit[1].trim());
			final String year = dateSplit[2].trim();
			final String date = day.concat(de).concat(month).concat(de).concat(year);

			return LocalDate.parse(date, dateTimeFormat);
		}

		return LocalDate.now();
	}

	private Matcher getMatcher(final String regex, final String input) {
		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		return pattern.matcher(input);
	}

}
