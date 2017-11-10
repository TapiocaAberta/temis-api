package com.sjcdigital.temis.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author pedro-hos
 *
 */

@Path("carga")
public interface CargaResource {
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	Response carregaVereadoresELeis();
	
	@POST
	@Path("/machine-learn")
	@Produces(MediaType.APPLICATION_JSON)
	Response geraDataParaML();
	

}
