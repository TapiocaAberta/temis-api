package com.sjcdigital.temis.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author pedro-hos
 *
 */

@Path("leis")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface LeiResource {

	@GET
	Response buscaTodosPaginados(@QueryParam("total") int total, @QueryParam("pg") int pg);
	
	@GET
	@Path("/{id}")
	Response buscaPorId(@PathParam("id") Long id);
	
	@GET
	@Path("/situacao-simplificada/{id}")
	Response buscaPorSituacaoSimplificada(@PathParam("id") Long id);
	
	@GET
	@Path("/tipo/{id}")
	Response buscaPorTipo(@PathParam("id") Long id, @QueryParam("total") int total, @QueryParam("pg") int pg);
	
	@GET
	@Path("/classe/{id}")
	Response buscaPorClasse(@PathParam("id") Long id);
}
