package com.sjcdigital.temis.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author pedro-hos
 *
 */
@Path("ḧello")
public class HelloController {

	@GET
	@Path("/say/{word}")
	@Produces(MediaType.TEXT_PLAIN)
	public String say(@PathParam("word") String word) {
		return "Hello " + word;
	}

}
