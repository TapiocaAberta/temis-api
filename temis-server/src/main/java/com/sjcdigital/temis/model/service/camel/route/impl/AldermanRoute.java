package com.sjcdigital.temis.model.service.camel.route.impl;

import java.time.LocalDate;

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
		from("file://" + buildPath() + buildOptions())
		.process(processor)
		.to("file://" + buildPath().concat("/read"));
	}

	@Override
	protected String buildPath() {
		return path + "vereadores/" + LocalDate.now().getYear();
	}

	@Override
	protected String buildOptions() {
		return "?delay=5s";
	}

}
