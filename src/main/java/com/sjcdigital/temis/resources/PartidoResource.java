package com.sjcdigital.temis.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author pedro-hos
 *
 */

@Path("partidos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PartidoResource {
	
	@GET
	Response todos();
	
	@GET
	@Path("/sigla/{sigla}")
	Response porSigla(@PathParam("sigla") String sigla);
	
	@GET
	@Path("/{id}/vereadores")
	Response vereadoresDoPartido(@PathParam("id") Long id);

}
