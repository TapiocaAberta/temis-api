package com.sjcdigital.temis.model.service.parsers.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.model.document.Law;
import com.sjcdigital.temis.model.repositories.LawsRepository;
import com.sjcdigital.temis.model.service.machine_learn.ClassifyLaw;
import com.sjcdigital.temis.model.service.parsers.AbstractParser;
import com.sjcdigital.temis.model.service.parsers.util.AldermanParserUtil;
import com.sjcdigital.temis.util.RegexUtils;

/**
 * @author pedro-hos
 */

@Component
public class LawsParser extends AbstractParser {
	
	private static final Logger LOGGER = LogManager.getLogger(LawsParser.class);

	@Autowired
	private LawsRepository lawsRepository;
	
	@Autowired
	private ClassifyLaw classifyLaw;
	
	@Autowired
	private AldermanParserUtil aldermanParserUtil;
	
	@Override
	public void parse(final File file) {
		
		try {
			
			final Document document = readFile(file).get();
			
			final Law law = new Law();
			final Optional<String> title = buildTitle(document.title().trim());
			
			String summary = buildSummary(document.head().select("script").toString()).orElse(null);
			
			law.setSummary(summary);
			law.setType(Objects.nonNull(summary) ? classifyLaw.classify(summary) : null);
			law.setTitle(title.orElse(null));
			law.setDate(buildDate(title.orElse("")).orElse(LocalDate.now()));
			
			cleanDocument(document);
			
			final Element body = document.body();
			law.setAuthor(aldermanParserUtil.buildAuthor(Optional.ofNullable(body.getElementsByClass("RegPub").first().text()).orElse("")));
			law.setDesc(body.html().trim());
			law.setCode(extractedCode(file).orElse(null));
			law.setProjectLawNumber(buildProjectLawNumber(body).orElse(null));
			
			saveLaw(law);
			
		} catch (InterruptedException | ExecutionException | IOException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
		
	}

	private void cleanDocument(final Document document) {
		document.select("script").remove();
		document.select("a[href]").remove();
	}
	
	private Optional<String> buildTitle(String title) {
		
		final Matcher matcher = RegexUtils.getMatcher("lei\\s*municipal\\s*nº?\\s*\\d+\\.?\\d+,?\\s*(de)?\\s*\\d{1,2}/\\d{1,2}/\\d{2,4}", title);
				
		if (matcher.find()) {
			return Optional.of(matcher.group(0));
		}
		
		return Optional.empty();
	}
	
	private Optional<String> buildSummary(String script) {
		
		final Matcher matcher = RegexUtils.getMatcher("Xtesta\\((.+)\\)", script);
		
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

	private Optional<String> extractedCode(final File file) {
		return Optional.of(file.getName().replace("L", "").replace(".html", ""));
	}
	
	private Optional<String> buildProjectLawNumber(final Element body) {
		
		final String regPub = body.getElementsByClass("RegPub").text();
		final Matcher matcher = RegexUtils.getMatcher("Proj(\\.|eto)\\s*de\\s*Lei\\s*(n.?)?\\s*(\\d+\\/\\d+)", regPub);
		
		if (matcher.find()) {
			return Optional.of(matcher.group(3));
		}
		
		LOGGER.info("Project law no match: " + regPub);
		return Optional.empty();
	}
	
	
	
	private Optional<LocalDate> buildDate(final String title) {
		
		final Matcher matcher = RegexUtils.getMatcher("\\d{1,2}/\\d{1,2}/\\d{2,4}", title);
		
		if (matcher.find()) {
			
			final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("pt", "br"));
			String date = matcher.group(0);
			
			try {
				return Optional.of(LocalDate.parse(date, dateTimeFormat));
				
			} catch (DateTimeParseException exception) {
				LOGGER.error("Data: " + date + " não pode ser parseada!");
				return Optional.empty();
			}
			
		}
		
		return Optional.empty();
	}
	
	
}
