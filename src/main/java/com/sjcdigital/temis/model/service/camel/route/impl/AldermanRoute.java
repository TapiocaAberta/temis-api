package com.sjcdigital.temis.model.service.camel.route.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.model.service.camel.processor.AldermanProcessor;
import com.sjcdigital.temis.model.service.camel.route.AbstractRoute;

/**
 * @author pedro-hos
 */

@Component
public class AldermanRoute extends AbstractRoute {

	@Autowired
	private AldermanProcessor processor;

	@Override
	public void configure() throws Exception {
		from(FILE + path() + options()).process(processor);
	}

	@Override
	protected String path() {
		return path + "vereadores/";
	}

}
