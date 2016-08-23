package com.sjcdigital.temis.model.service.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author pedro-hos
 */
public abstract class AbstractRoute extends RouteBuilder {

	protected static final String FILE = "file://";

	private static final String DELAY 	  = "delay=30s";
	private static final String RECURSIVE = "recursive=true";
	private static final String DELETE 	  = "delete=true";
	private static final String AND	  	  = "&";

	@Value("${path.leis}")
	protected String path;

	protected String options() {
		return "?".concat(DELAY).concat(AND).concat(RECURSIVE).concat(AND).concat(DELETE);
	}

	protected abstract String path();

}
