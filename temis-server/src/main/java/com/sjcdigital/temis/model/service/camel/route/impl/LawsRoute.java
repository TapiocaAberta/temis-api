package com.sjcdigital.temis.model.service.camel.route.impl;

import java.time.LocalDate;

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

		from("file://" + buildPath() + "2013" + buildOptions()).to("direct:send-process");
		from("file://" + buildPath() + "2014" + buildOptions()).to("direct:send-process");
		from("file://" + buildPath() + "2015" + buildOptions()).to("direct:send-process");
		from("file://" + buildPath() + LocalDate.now().getYear() + buildOptions()).to("direct:send-process");

		from("direct:send-process").process(processor);
	}

	@Override
	protected String buildPath() {
		return path + "leis/";
	}

	@Override
	protected String buildOptions() {
		return "?delay=5s&include=.*.html&move=${file:parent}/read/${file:onlyname}";
	}

}
