package com.sjcdigital.temis.bots;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.bots.impl.AldermenBot;
import com.sjcdigital.temis.bots.impl.LawsBot;
import com.sjcdigital.temis.exceptions.BotException;

/**
 * 
 * @author pedro-hos Apenas um starter para fazer a carga assim que o servidor
 *         subir.
 */
@Component
public class TemisBot {
	
	private final Logger LOGGER = LogManager.getLogger(this.getClass());
	
	@Autowired
	private LawsBot lawsBot;
	
	@Autowired
	private AldermenBot aldermenBot;
	
	@PostConstruct
	public void run() {
		
		try {
			
			aldermenBot.saveAldermen();
			lawsBot.savePages();
			
		} catch (BotException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}
	}
	
}
