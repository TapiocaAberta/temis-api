package com.sjcdigital.temis.model.service.camel.route.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.model.service.camel.processor.LawsProcessor;
import com.sjcdigital.temis.model.service.camel.route.AbstractRoute;

/**
 * @author pedro-hos
 */

@Component
public class LawsRoute extends AbstractRoute {

	@Autowired
	private LawsProcessor processor;

	@Override
	public void configure() throws Exception {
		from(FILE + path() + options()).process(processor);
	}

	@Override
	protected String path() {
		return path + "leis/";
	}

	@Override
	protected String options() {
		return "?delay=30s&recursive=true&delete=true";
	}

}
