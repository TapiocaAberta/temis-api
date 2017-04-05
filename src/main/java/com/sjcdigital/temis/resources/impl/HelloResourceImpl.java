package com.sjcdigital.temis.resources.impl;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.sjcdigital.temis.annotations.Property;
import com.sjcdigital.temis.resources.HelloResource;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class HelloResourceImpl implements HelloResource {
	
	@Inject
	@Property("hello.world")
	private String helloWorld;
	
	@Inject
	@Property("path.images")
	private String path;

	@Override
	public Response sayHello( String text ) {
		
		if (Optional.ofNullable(text).isPresent()) {
			return Response.ok("Hello ".concat(text)).build();
		}

		return Response.ok(helloWorld).build();
	}

}
