package com.sjcdigital.temis.resources.impl;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.sjcdigital.temis.annotations.Property;
import com.sjcdigital.temis.model.service.ExtractLawWS;
import com.sjcdigital.temis.resources.HelloResource;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class HelloResourceImpl implements HelloResource {
	
	@Inject
	private ExtractLawWS extract;
	
	@Inject
	@Property("hello.world")
	private String helloWorld;

	@Override
	public Response sayHello( String text ) {

		if (Optional.ofNullable(text).isPresent()) {
			return Response.ok("Hello ".concat(text)).build();
		}

		return Response.ok(helloWorld).build();
	}

	@Override
	public Response extract() {
		return Response.ok(extract.extract()).build();
	}

}
