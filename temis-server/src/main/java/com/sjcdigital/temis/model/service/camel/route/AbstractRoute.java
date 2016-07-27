package com.sjcdigital.temis.model.service.camel.route;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author pedro-hos
 */
public abstract class AbstractRoute extends RouteBuilder {
	protected abstract String buildPath();
	protected abstract String buildOptions();
}
