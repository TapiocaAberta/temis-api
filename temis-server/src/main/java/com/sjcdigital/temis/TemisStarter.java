package com.sjcdigital.temis;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.model.service.bots.BotService;

/**
 *
 * @author pedro-hos Apenas um starter para fazer a carga assim que o servidor subir.
 */
@Component
public class TemisStarter {

	@Autowired
	private BotService service;

	@PostConstruct
	public void run() {
		service.run();
	}

}
