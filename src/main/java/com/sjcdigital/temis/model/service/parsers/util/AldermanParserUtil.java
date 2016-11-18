package com.sjcdigital.temis.model.service.parsers.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.model.document.Alderman;
import com.sjcdigital.temis.model.repositories.AldermanRepository;
import com.sjcdigital.temis.util.RegexUtils;

/**
 * @author pedro-hos
 */
@Component
public class AldermanParserUtil {
	
	private static final Logger LOGGER = LogManager.getLogger(AldermanParserUtil.class);
	
	private static List<String> noPolitician = Arrays.asList( "Poder Executivo", "Comissão de Justiça", "Mesa Diretora", "Comissão de Servidores Públicos", 
															  "Bancada dos Vereadores", "Comissão de Justiça, Redação e Direitos Humanos", "Mesa da Câmara",
															  "Diversos Vereadores" );
	
	@Value("${path.images}")
	private String pathImages;
	
	@Value("${url.context}")
	private String urlContext;
	
	@Value("${politico.sem_foto}")
	private String noPhoto;
	
	@Autowired
	private AldermanRepository aldermanRepository;
	
	
	public Collection<Alderman> buildAuthor(final String regPub) {
		
		final Matcher matcher = RegexUtils.getMatcher("autoria:?\\s*(dos|das|doe|da|de|do|o)?\\s*(.*)\\)?", regPub);
		
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
		
		Optional<String> noPolitician = specificNoPolitician(authorClean);
		
		if (noPolitician.isPresent()) {
			return new String[] {noPolitician.get()};
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
	
	protected Optional<String> specificNoPolitician(final String author) {
		return noPolitician.stream().filter( n -> author.matches("(?i).*" + n + ".*")).findFirst();
	}

}
