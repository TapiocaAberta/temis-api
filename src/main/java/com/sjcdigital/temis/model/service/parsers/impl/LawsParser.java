package com.sjcdigital.temis.model.service.parsers.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.model.document.Alderman;
import com.sjcdigital.temis.model.document.Law;
import com.sjcdigital.temis.model.repositories.AldermanRepository;
import com.sjcdigital.temis.model.repositories.LawsRepository;
import com.sjcdigital.temis.model.service.machine_learn.ClassifyLaw;
import com.sjcdigital.temis.model.service.parsers.AbstractParser;

/**
 * @author pedro-hos
 */

@Component
public class LawsParser extends AbstractParser {
	
	private static final Logger LOGGER = LogManager.getLogger(LawsParser.class);
	
	@Value("${path.images}")
	private String pathImages;
	
	@Value("${url.context}")
	private String urlContext;
	
	@Value("${politico.sem_foto}")
	private String noPhoto;
	
	@Autowired
	private LawsRepository lawsRepository;
	
	@Autowired
	private AldermanRepository aldermanRepository;
	
	@Autowired
	private ClassifyLaw classifyLaw;
	
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
			
			document.select("script").remove();
			document.select("a[href]").remove();
			
			final Element body = document.body();
			
			law.setDesc(body.html().trim());
			law.setAuthor(buildAuthor(body));
			law.setCode(extractedCode(file).orElse(null));
			law.setProjectLawNumber(buildProjectLawNumber(body).orElse(null));
			
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
	
	private Alderman getAldemerman() {
		final String photo = urlContext.concat(pathImages).concat(noPhoto);
		Alderman alderman = aldermanRepository.findByName("Sem autor").orElse(new Alderman("Sem autor", true, photo));
		alderman.plusLaw();
		return aldermanRepository.save(alderman);
	}
	
	private Optional<Alderman> getAlderman(final String authorName) {
		final Alderman alderman = aldermanRepository.findByNameContainingIgnoreCase(authorName).orElse(getByDistance(authorName));
		alderman.plusLaw();
		return Optional.of(aldermanRepository.save(alderman));
	}
	
	private Alderman getByDistance(String authorName) {
		
		final String photo = urlContext.concat(pathImages).concat(noPhoto);
		Map<Double, Alderman> names = new HashMap<>();
		names.put(Double.MIN_VALUE, new Alderman(authorName, true, photo));
		
		for(Alderman alderman : aldermanRepository.findAll()) {
			
			double distance = StringUtils.getJaroWinklerDistance(authorName, alderman.getName());
			
			if(distance >= 0.95) {
				LOGGER.info("Jaro Winkler Distance: [" + distance + "] " + authorName + " e " + alderman.getName());
				names.put(distance, alderman);
			}
		}
		
		return names.entrySet().stream()
							   .sorted(Map.Entry.<Double, Alderman>comparingByKey().reversed())
							   .findFirst().get().getValue();
	}

	private Optional<String> extractedCode(final File file) {
		return Optional.of(file.getName().replace("L", "").replace(".html", ""));
	}
	
	private Optional<String> buildProjectLawNumber(final Element body) {
		
		final String regPub = body.getElementsByClass("RegPub").text();
		final Matcher matcher = getMatcher("Proj(\\.|eto)\\s*de\\s*Lei\\s*(n.?)?\\s*(\\d+\\/\\d+)", regPub);
		
		if (matcher.find()) {
			return Optional.of(matcher.group(3));
		}
		
		LOGGER.info("Project law no match: " + regPub);
		return Optional.empty();
	}
	
	private Collection<Alderman> buildAuthor(final Element body) {
		
		final Element first = body.getElementsByClass("RegPub").first();
		final String regPub = Objects.nonNull(first) ? first.text() : "";
		
		final Matcher matcher = getMatcher("autoria:?\\s*(dos|das|doe|da|de|do|o)?\\s*(.*)\\)?", regPub);
		
		if (matcher.find()) {
			
			final Collection<Alderman> aldermen = new HashSet<>();
			final String[] authors = cleanAuthor(matcher.group(2));
			
			for (final String author : authors) {
				aldermen.add(getAlderman(author).get());
			}
			
			return aldermen;
			
		}
		
		LOGGER.info("Author no match: " + regPub);
		return Arrays.asList(getAldemerman()); //when no match
	}
	
	private String[] cleanAuthor(final String author) {
		
		String authorClean = author.toLowerCase();
		
		//TODO: refatorar
		if (authorClean.contains("poder executivo")) {
			return new String[] {"Poder Executivo"};
			
		} else if (authorClean.contains("mesa diretora")) {
			return new String[] {"Mesa Diretora"};
			
		} else if(authorClean.contains("comissão de justiça, redação e direitos humanos")) {
			return new String[] {"Comissão de Justiça, Redação e Direitos Humanos"};
			
		} else if (authorClean.contains("diversos vereadores")) {
			return new String[] {"Diversos Vereadores"};
			
		} else if (authorClean.contains("comissão de servidores públicos")) {
			return new String[] {"Comissão de Servidores Públicos"};
			
		} else if (authorClean.contains("bancada dos vereadores")) {
			return new String[] {"Bancada dos Vereadores"};
			
		} else if (authorClean.contains("mesa da câmara")) {
			return new String[] {"Mesa da Câmara"};
		}
		
		authorClean = authorClean.replaceAll("verª", ""); // Ex. vereadores A, B, C
		authorClean = authorClean.replaceAll("((v?)e(r?)e(r?)ador(es|as|a){0,1}|ver\\.|vers\\.)\\s*", ""); // Ex. vereadores A, B, C
		
		//Dr Angela special case
		authorClean = authorClean.replaceAll("(dra?ª?\\.?)*\\s*[aâ]ngela\\s*(guadagnin)?", " angela ");
		
		//hélio nishimoto
		authorClean = authorClean.replaceAll("hello nishimoto", " hélio nishimoto ");
		
		//Fernando Petiti da Farmácia Comunitária Petiti da Farmácia Comunitária
		authorClean = authorClean.replaceAll("^\\s*pet{1,2}it{1,2}i da farmácia comunitária", " fernando petiti da farmácia comunitária ");
		
		//Dilerminado-Dié Dirlermano Dié  Dilermando Dié 
		authorClean = authorClean.replaceAll("dilerminado\\s*-\\s*dié|dirlermano\\s*dié|dilermando\\s*die", " dilermando dié ");
		
		//José R Romancini  José Raimundo Romancini
		authorClean = authorClean.replaceAll("josé r\\. romancini", " josé raimundo romancini ");
		
		// Aloisio Petiti  Aloínio Petiti 
		authorClean = authorClean.replaceAll("aloisio petiti", " aloísio petiti ");
		
		// Flavia Camarço  Flávia Camarço 
		authorClean = authorClean.replaceAll("flavia camarço|flávia camarço|flavia camargo", " flávia camargo ");
		
		// Aloisio Petiti  Aloisio Petiti 
		authorClean = authorClean.replaceAll("aloisio petiti", " aloisio petiti ");
		
		// Vera Lindonice Brito  Lindonice Brito 
		authorClean = authorClean.replaceAll("vera lindonice de brito", " lindonice brito ");
		
		//  Benedito Sequeira  => Benedito De Siqueira
		authorClean = authorClean.replaceAll("benedito sequeira", " benedito de siqueira ");
		
		// Alexandre Do Farmácia =>  Alexandre Da Farmácia 
		authorClean = authorClean.replaceAll("alexandre do farmácia", " alexandre da farmácia ");
		
		// Carlinhos De Almeida =>  Carlinhos Almeida  
		authorClean = authorClean.replaceAll("carlinhos de almeida", " carlinhos almeida ");
		
		// Cristóvão Gonçalves 
		authorClean = authorClean.replaceAll("crist6vao gonalves|cristdvao gonalves", " Cristóvão Gonçalves ");
		
		authorClean = authorClean.replaceAll("prof(essor(a*)?|\\.*)", "").trim();
		authorClean = authorClean.replaceAll("processo", "").trim();
		authorClean = authorClean.replaceAll("['\\.:\\-\\/\\)]", "").trim(); //) . : -
		authorClean = authorClean.replaceAll("(nº)", "").trim();
		authorClean = authorClean.replaceAll("p(i|1):?\\s*(\\d+)\\.*$", "").trim(); // Ex. pi 1234-09/34
		authorClean = authorClean.replaceAll("mensagem .*", "").trim(); // Ex. mensagem 83/atl/14
		authorClean = authorClean.replaceAll("e outro(s{0,1})", "").trim(); // Ex. e outros
		authorClean = authorClean.replaceAll("\\s*-*\\s*[pP]roc.?\\s*(rio)*", "").trim();
		authorClean = authorClean.replaceAll("\\d*", "").trim();
		authorClean = authorClean.replaceAll("\\s+pl\\s*", "").trim();
		authorClean = authorClean.trim();
		
		return WordUtils.capitalize(authorClean).split("(, | E | e | a | A )");
	}
	
	private Optional<LocalDate> buildDate(final String title) {
		
		final Matcher matcher = getMatcher("\\d{1,2}/\\d{1,2}/\\d{2,4}", title);
		
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
	
	private Matcher getMatcher(final String regex, final String input) {
		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		return pattern.matcher(input);
	}
	
}
