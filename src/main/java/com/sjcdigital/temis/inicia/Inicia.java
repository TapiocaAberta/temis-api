package com.sjcdigital.temis.inicia;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import com.sjcdigital.temis.model.service.bots.autor.AutorBot;
import com.sjcdigital.temis.model.service.bots.exceptions.BotException;
import com.sjcdigital.temis.model.service.bots.lei.LeisBot;

/**
 * @author pedro-hos
 *
 */

@Startup
@Singleton
public class Inicia {

	@Inject
	private AutorBot vereadorBot;
	
	@Inject
	private LeisBot leisBot;
	
	@PostConstruct
	public void executaBots() throws BotException {
		vereadorBot.saveData();
		leisBot.saveData();
		System.out.println("passou ....");
	}
}
