package com.sjcdigital.temis.inicia;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import com.sjcdigital.temis.model.service.bots.exceptions.BotException;
import com.sjcdigital.temis.model.service.bots.lei.LeisBot;
import com.sjcdigital.temis.model.service.bots.vereador.VereadorBot;

/**
 * @author pedro-hos
 *
 */

@Startup
@Singleton
public class Inicia {

	@Inject
	private VereadorBot vereadorBot;
	
	@Inject
	private LeisBot leisBot;
	
	@PostConstruct
	public void executaBots() throws BotException {
		vereadorBot.saveData();
		leisBot.saveData();
	}
}
