package com.sjcdigital.temis.model.service.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author pedro-hos
 */
public abstract class AbstractRoute extends RouteBuilder {

	@Value("${path.leis}")
	protected String path;

	protected abstract String buildPath();
	protected abstract String buildOptions();
}
