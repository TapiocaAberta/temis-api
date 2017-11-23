package com.sjcdigital.temis.resources.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.sjcdigital.temis.model.entities.impl.Autor;
import com.sjcdigital.temis.model.entities.impl.PartidoPolitico;
import com.sjcdigital.temis.model.repositories.impl.Autores;
import com.sjcdigital.temis.model.repositories.impl.PartidosPolitico;
import com.sjcdigital.temis.resources.PartidoResource;
import com.sjcdigital.temis.utils.JaxrsUtils;

/**
 * @author pesilva
 *
 */
@Stateless
public class PartidoResourceImpl implements PartidoResource {
	
	@Inject
	private PartidosPolitico partidos;
	
	@Inject
	private Autores autores;

	@Override
	public Response todos() {
		List<PartidoPolitico> todosPartidos = JaxrsUtils.lanca404SeNulo(partidos.todos());
		return Response.ok(todosPartidos).build();
	}

	@Override
	public Response porSigla(String sigla) {
		PartidoPolitico partido = JaxrsUtils.lanca404SeNulo(partidos.comSigla(sigla));
		return Response.ok(partido).build();
	}

	@Override
	public Response vereadoresDoPartido(Long id) {
		List<Autor> vereadoresDoPartido = JaxrsUtils.lanca404SeNulo(autores.doPartido(id));
		return Response.ok(vereadoresDoPartido).build();
	}

}
