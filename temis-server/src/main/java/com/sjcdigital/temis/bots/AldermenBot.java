package com.sjcdigital.temis.bots;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.exceptions.BotException;

/**
 * 
 * @author pedro-hos
 *         
 */

@Component
public class AldermenBot extends AbstractBot {
	
	private static final Logger LOGGER = LogManager.getLogger(AldermenBot.class);
	
	@Value("${url.aldermen}")
	private String aldermenUrl;
	
	protected Collection<String> getAldermenLinks() throws BotException {
		
		Collection<String> links = new HashSet<>();
		
		try {
			
			Document document = Optional.ofNullable(getPage(aldermenUrl).get()).orElseThrow(BotException::new);
			Elements divsBack = document.getElementsByClass("back"); // <div class="back">
			
			for (Element element : divsBack) {
				element.select("a").stream().map(l -> l.attr("href"))
											.peek(System.out :: println)
										    .forEach(links :: add);
			}
			
			
		} catch (IOException | InterruptedException | ExecutionException exception) {
			LOGGER.error(ExceptionUtils.getStackTrace(exception));
		}
		
		return links;
	}
	
}
