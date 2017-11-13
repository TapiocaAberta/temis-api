package com.sjcdigital.temis.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author pedro-hos
 *
 */

@Path("tipo")
public interface TipoResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Response todos();
	
}
