package com.sjcdigital.temis.resources.impl;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.sjcdigital.temis.model.entities.impl.Autor;
import com.sjcdigital.temis.model.repositories.impl.Autores;
import com.sjcdigital.temis.model.repositories.impl.Presencas;
import com.sjcdigital.temis.resources.PresencaResource;
import com.sjcdigital.temis.utils.RESTUtils;

@Stateless
public class PresencaResourceImpl implements PresencaResource {

	@Inject
	private Autores autores;

	@Inject
	private Presencas presencas;

	@Override
	public Response porVereador(long id) {
		Autor autor = RESTUtils.lanca404SeNulo(autores.buscarPorId(id));
		Map<String, Boolean> mapaPresencas = new HashMap<>();
		// for some reason this is NOT working - sounds like a bug
//		mapaPresencas = presencasPorAutor.stream().collect(Collectors.toMap(p -> p.getSessao().resumo(), Presenca::isPresenca));
		presencas.porAutor(autor).stream().forEach(p -> mapaPresencas.put(p.getSessao().resumo(), p.isPresenca()));
		return Response.ok().entity(mapaPresencas).build();
	}

}
