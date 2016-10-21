package com.sjcdigital.temis.model.service.parsers.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

			LOGGER.info("Parsing and save: " + file.getParentFile().getName());

			final Document document = readFile(file).get();
			
			final Law law = new Law();
			final Optional<String> title = buildTitle(document.title().trim());
			
			law.setSummary(buildSummary(document.head().select("script").toString()).orElse(""));
			law.setTitle(title.orElse(""));
			law.setDate(buildDate(title.orElse("")).orElse(LocalDate.now()));
			
			document.select("script").remove();
			document.select("a[href]").remove();

			final Element body = document.body();

			law.setDesc(body.html().trim());
			law.setAuthor(buildAuthor(body));
			law.setCode(extractedCode(file).orElse(""));
			law.setProjectLawNumber(buildProjectLawNumber(body).orElse(""));

			saveLaw(law);

		} catch (InterruptedException | ExecutionException | IOException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}

	}

	private Optional<String> buildTitle(String title) {
		
		final Matcher matcher = getMatcher("lei\\s*municipal\\s*nº?\\s*\\d+\\.?\\d+,?\\s*(de)?\\s*\\d{1,2}/\\d{1,2}/\\d{2,4}", title);

		if (matcher.find()) {
			return Optional.of(matcher.group(0));
		}
		
		return Optional.empty();
	}

	private Optional<String> buildSummary(String script) {
		
		final Matcher matcher = getMatcher("Xtesta\\((.+)\\)", script);

		if (matcher.find()) {
			return Optional.of(matcher.group(1).split("\",\"")[1].replaceAll("<!--", "").replaceAll("--!?>", ""));
		}
		
		return Optional.empty();
	}

	private void saveLaw(final Law law) {
		if (!lawsRepository.findByCode(law.getCode()).isPresent()) {
			lawsRepository.save(law);
		}
	}

	private Optional<Alderman> getAlderman(final String authorName) {
		final Alderman alderman = aldermanRepository.findByNameContainingIgnoreCase(authorName).orElse(new Alderman(authorName, true));
		alderman.plusLaw();
		return Optional.of(aldermanRepository.save(alderman));
	}

	private Optional<String> extractedCode(final File file) {
		return Optional.of(file.getName().replace("L", "").replace(".html", ""));
	}

	private Optional<String> buildProjectLawNumber(final Element body) {

		final String regPub = body.getElementsByClass("RegPub").text();
		final Matcher matcher = getMatcher("n. \\d+/\\d+", regPub);

		if (matcher.find()) {
			return Optional.of(matcher.group(0));
		}

		return Optional.empty();
	}

	private Collection<Alderman> buildAuthor(final Element body) {

		final String regPub = body.getElementsByClass("RegPub").text();
		final Matcher matcher = getMatcher("de autoria [A-Za-z]{0,3} ", regPub);

		if (matcher.find()) {

			final Collection<Alderman> aldermen = new HashSet<>();
			final String[] authors = cleanAuthor(regPub.substring(matcher.end())).split("(, | E | e )");

			for (final String author : authors) {
				aldermen.add(getAlderman(author).get());
			}

			return aldermen;

		}

		return null;
	}

	private String cleanAuthor(final String author) {

		String authorClean = author.toLowerCase();

		if (authorClean.contains("poder executivo")) {
			return "Poder Executivo";
		} else if (authorClean.contains("mesa diretora")) {
			return "Mesa Diretora";
		} else if (authorClean.contains("ângela")) {
			return "angela";
		}

		authorClean = authorClean.replaceAll("(vere(r?)ador(es|as|a){0,1}|ver\\.*)\\s*", ""); // Ex. vereadores A, B, C
		authorClean = authorClean.replaceAll("(Pi|pi|PI)( )*(\\d+)*(-\\d+)*(/\\d+)*", ""); // Ex. pi 1234-09/34
		authorClean = authorClean.replaceAll("professor", ""); // Ex. Professor Calasans Camargo
		authorClean = authorClean.replaceAll("mensagem .*", ""); // Ex. mensagem 83/atl/14
		authorClean = authorClean.replaceAll("(^\\s*dr|^dr)(a*)(\\.*)", " ");
		authorClean = authorClean.replaceAll("e outro(s{0,1})", ""); // Ex. e outros
		authorClean = authorClean.replace(")\\.:", "");
		authorClean = authorClean.trim();

		return WordUtils.capitalize(authorClean);
	}

	private Optional<LocalDate> buildDate(final String title) {

		final Matcher matcher = getMatcher("\\d{1,2}/\\d{1,2}/\\d{2,4}", title);

		if (matcher.find()) {

			final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "br"));
			
			String date = matcher.group(0);
			LOGGER.info("Data: " + date);
			
			try {
				return Optional.of(LocalDate.parse(date, dateTimeFormat));
				
			} catch (DateTimeParseException exception) {
				LOGGER.error("Data: " + date + " não pode ser parseada!");
				return Optional.empty();
			}

		}

		return Optional.empty();
	}

	private Matcher getMatcher(final String regex, final String input) {
		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		return pattern.matcher(input);
	}

}
