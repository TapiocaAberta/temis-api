package com.sjcdigital.temis.model.service.parsers.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.model.document.Law;
import com.sjcdigital.temis.model.service.parsers.AbstractParser;

/**
 * @author pedro-hos
 */

@Component
public class LawsParser extends AbstractParser {

	private static final Logger LOGGER = LogManager.getLogger(LawsParser.class);

	@Override
	public void parse(final File file) {

		try {

			final Document document = readFile(file).get();
			document.select("script").remove();

			final Element body = document.body();

			final Law law = new Law();

			law.setDesc(body.html());
			law.setTitle(document.title());
			law.setAuthor(buildAuthor(body));
			law.setDate(buildDate(body));
			law.setCode(file.getName().replace("L", "").replace(".html", ""));
			law.setNumber(buildProjectLawNumber(body));

			LOGGER.info(law.toString());

		} catch (InterruptedException | ExecutionException | IOException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}

	}

	private String buildProjectLawNumber(final Element body) {

		final String regPub = body.getElementsByClass("RegPub").text();
		final Matcher matcher = getMatcher("nº [0-9]+/[0-9]+", regPub);

		if (matcher.find()) {
			return matcher.group(0);
		}

		return "";
	}

	private String buildAuthor(final Element body) {

		final String regPub = body.getElementsByClass("RegPub").text();
		final Matcher matcher = getMatcher("de autoria [A-Za-z]{0,3} ", regPub);

		if (matcher.find()) {
			final String author = regPub.substring(matcher.end()).toLowerCase()
																 .replaceAll("vereador(a|es|as){0,1}", "")
																 .replaceAll("(Pi|pi|PI) [0-9]+/[0-9]+", "")
																 .replace(")", "").trim();
			return WordUtils.capitalize(author);
		}

		return "";
	}

	private LocalDate buildDate(final Element body) {

		final String footerText = body.getElementsByClass("RP").text();
		final Matcher matcher = getMatcher("[0-9]{1,2} de [a-zA-Z]+ de [0-9]{4}", footerText);

		if (matcher.find()) {

			final String de = "de";
			final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("pt", "br"));
			final String[] dateSplit = matcher.group(0).split(de);
			final String day = StringUtils.leftPad(dateSplit[0], 2, "0");
			final String month = WordUtils.capitalize(dateSplit[1]);
			final String year = dateSplit[2];
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
