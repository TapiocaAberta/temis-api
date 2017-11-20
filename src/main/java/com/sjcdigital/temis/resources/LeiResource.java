package com.sjcdigital.temis.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
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
	@Path("/filtra")
	Response filtraPaginado( @QueryParam("idSituacao") Long idSituacao, @QueryParam("idClasse") Long idClasse, 
							 @QueryParam("idTipo") Long idTipo, @QueryParam("ano") Integer ano, @QueryParam("idAutor") Long idAutor,
							 @QueryParam("total") int total, @QueryParam("pg") int pg );
	
	@GET
	@Path("/grafico")
	Response graficos();
	
	@GET
	@Path("/anos")
	Response buscaAnos(@QueryParam("idAutor") Long idAutor);
	
	@PUT
	@Path("/{id}/vota")
	Response votar(@PathParam("id") Long id, @QueryParam("rating") Integer rating, @Context HttpServletRequest request);
	
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
