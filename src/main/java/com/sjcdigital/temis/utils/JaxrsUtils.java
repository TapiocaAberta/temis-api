package com.sjcdigital.temis.utils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * 
 * @author pedro-hos
 * @author William Siqueira
 *
 */

public class JaxrsUtils {

	public static final String PARAMETRO_TOTAL = "total";
	public final static String PARAMETRO_PAGINA = "pg";
	public final static String HEADER_TOTAL_ITENS = "TOTAL_ITENS";

	public static <T> T lanca404SeNulo(T object) {

		if (Objects.isNull(object)) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).build());
		}

		return object;
	}

	public static <T> T lanca404SeNulo(Optional<T> object) {

		if (!object.isPresent()) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).build());
		}

		return object.get();
	}

	public static <T> List<T> lanca404SeNulo(List<T> listObjects) {

		if (listObjects.isEmpty()) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND).build());
		}

		return listObjects;
	}

	public static List<Link> constroiLinksNavegacao(UriInfo uriInfo, UriBuilder urlBase, long totalResultados, int pg, int total, Object... parametrosPath) {
		
		List<Link> links = new ArrayList<>();
		
		Link esse = Link.fromUri(uriInfo.getAbsolutePathBuilder().queryParam(PARAMETRO_TOTAL, total).queryParam(PARAMETRO_PAGINA, pg).build())
				        .rel("self")
				        .title("Essa página")
				        .build();
		
		links.add(esse);
		
		if (pg < totalResultados / total) {
			URI proximo = urlBase.clone().queryParam(PARAMETRO_TOTAL, total).queryParam(PARAMETRO_PAGINA, pg + 1).build(parametrosPath);
			links.add(Link.fromUri(proximo).rel("next").title("Próxima Página").build());
		}
		
		if (pg > 0) {
			URI anterior = urlBase.clone().queryParam(PARAMETRO_TOTAL, total).queryParam(PARAMETRO_PAGINA, pg - 1).build(parametrosPath);
			links.add(Link.fromUri(anterior).rel("prev").title("Página Anterior").build());
		}
		
		return links;
	}

}
