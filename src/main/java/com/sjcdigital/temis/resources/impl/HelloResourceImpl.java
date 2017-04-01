package com.sjcdigital.temis.resources.impl;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import com.sjcdigital.temis.resources.HelloResource;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class HelloResourceImpl implements HelloResource {

	@Override
	public Response sayHello(/* String text */) {

		String text = "";

		if (Optional.of(text).isPresent()) {
			return Response.ok("Hello ".concat(text)).build();
		}

		return Response.ok("Hello World").build();
	}

}
