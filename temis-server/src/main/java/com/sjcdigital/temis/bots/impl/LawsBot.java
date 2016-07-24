package com.sjcdigital.temis.bots.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.bots.AbstractBot;
import com.sjcdigital.temis.exceptions.BotException;
import com.sjcdigital.temis.util.File;

/**
 * 
 * @author pedro-hos
 *         
 *         Classe responsável por pegar as páginas das leis em:
 *         http://www.ceaam.net/sjc/legislacao/index.php, e salvá-las em uma
 *         pasta para realização de parse posteriormente.
 * 		
 */
@Component
public class LawsBot extends AbstractBot {
	
	private static final Logger LOGGER = LogManager.getLogger(LawsBot.class);
			
	@Value("${url.laws}")
	private String lawsUrl;
	
	@Value("${path.leis}")
	private String path;
	
	@Autowired
	private File file;
	
	public void savePages() throws BotException {
		
		LocalTime start = LocalTime.now();
		List<Integer> allYears = getAllYears();
		
		String code = "L0001"; //TODO: Recuperar do banco, caso ja exista dado
		String url = "";
		String body = "";
		
		boolean tryNextYear = false;
		
		int currentYear = LocalDate.now().getYear();
		int index = 0;
		int limitToTry = 10;
		Integer year = null;
		
		while (index != allYears.size()) {
			
			try {
				
				year = allYears.get(index);
				url = buildURL(year, code);
				body = getPage(url).get().html();
				
				LOGGER.info("READ URL: " + url);
				
				file.createFile(getPath(), body, code, year);
				code = buildLawCode(getNextLawCode(code));
				tryNextYear = false;
				limitToTry = 10;
				
			} catch (InterruptedException | ExecutionException | IOException exception) {
				
				if (exception instanceof HttpStatusException) {
					
					HttpStatusException statusException = (HttpStatusException) exception;
					
					if (statusException.getStatusCode() == HttpStatus.NOT_FOUND.value()) {
						
						LOGGER.error("Error 404: " + url);
						
						if (year != currentYear && !tryNextYear) {
							index += 1;
							tryNextYear = true;
							
						} else {
							
							if(limitToTry == 0) {
								break;
							}
							
							if (year != currentYear) {
								index -= 1;
							}
							
							limitToTry-=1;
							code = buildLawCode(getNextLawCode(code));
						}
						
					} else {
						LOGGER.error(ExceptionUtils.getStackTrace(statusException));
						throw new BotException(statusException);
					}
					
				} else {
					LOGGER.error(ExceptionUtils.getStackTrace(exception));
					throw new BotException(exception);
				}
				
			}
			
		}
		
		LOGGER.info("ELAPSED TIME: " + Duration.between(start, LocalTime.now()));
		
	}
	
	private List<Integer> getAllYears() {
		
		List<Integer> years = new LinkedList<>();
		
		int year = 1948;
		int currentYear = LocalDate.now().getYear();
		
		boolean dataBaseEmpty = false;
		
		if (!dataBaseEmpty) {
			years.add(currentYear);
		}
		
		while (year <= currentYear) {
			years.add(year);
			year += 1;
		}
		
		return years;
		
	}
	
	private String buildURL(Integer year, String code) {
		return lawsUrl.concat(year.toString()).concat("/").concat(code).concat(".htm");
	}
	
	private BigInteger getNextLawCode(String current) {
		BigInteger nextLawCode = new BigInteger(current.replace("L", "")).add(BigInteger.ONE);
		return nextLawCode;
	}
	
	private String buildLawCode(BigInteger code) {
		return "L" + StringUtils.leftPad(code.toString(), 4, "0");
	}

	@Override
	protected String getPath() {
		return path.concat("leis/");
	}
	
}
