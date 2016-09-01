/**
 *
 */
package com.sjcdigital.temis.model.service.bots;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sjcdigital.temis.model.exceptions.BotException;
import com.sjcdigital.temis.model.service.bots.impl.CompositeBot;

/**
 * @author pedro-hos
 */

@Service
public class BotService {

	private final Logger LOGGER = LogManager.getLogger(this.getClass());

	@Autowired
	private CompositeBot compositeBot;

	@Async
	public void run() {

		try {
			
			compositeBot.saveData();
			
		} catch (final BotException e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
		}

	}

}
