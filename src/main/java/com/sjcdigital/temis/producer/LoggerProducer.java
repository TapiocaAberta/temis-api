package com.sjcdigital.temis.producer;

import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * @author William Siqueira
 */
@ApplicationScoped
public class LoggerProducer {

	@Produces
	public Logger producer(InjectionPoint ip) {
		return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
	}

}
