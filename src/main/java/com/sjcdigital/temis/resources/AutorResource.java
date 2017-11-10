/**
 * 
 */
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
@Path("autores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AutorResource {
	
	@GET
	Response buscaTodosPaginados(@QueryParam("total") int total, @QueryParam("pg") int pg);
	
	@GET
	@Path("/nome/{nome}")
	Response buscaPorNome( @PathParam("nome") String nome);
	
	@GET
	@Path("/{id}")
	Response buscaId( @PathParam("id") Long id);
	
	@GET
	@Path("/{id}/leis")
	Response buscaLeisPorAutor(@PathParam("id") Long id, @QueryParam("total") int total, @QueryParam("pg") int pg);
	
}
