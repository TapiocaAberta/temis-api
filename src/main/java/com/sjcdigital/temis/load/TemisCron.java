/**
 *
 */
package com.sjcdigital.temis.load;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.model.service.bots.BotService;

/**
 * @author pedro-hos
 * 		Executa a cada mês, no dia 01, a busca por novas leis
 *
 */
@Component
public class TemisCron {

	@Autowired
	private BotService service;

	@Scheduled(cron = "0 0 0 1 * ?")
	public void run() {
		service.run();
	}

}
