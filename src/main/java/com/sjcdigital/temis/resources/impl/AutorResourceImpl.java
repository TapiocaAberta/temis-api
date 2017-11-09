package com.sjcdigital.temis.resources.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.sjcdigital.temis.model.entities.impl.Autor;
import com.sjcdigital.temis.model.entities.impl.Lei;
import com.sjcdigital.temis.model.repositories.impl.Autores;
import com.sjcdigital.temis.model.repositories.impl.Leis;
import com.sjcdigital.temis.resources.AutorResource;
import com.sjcdigital.temis.utils.RESTUtils;

/**
 * 
 * @author pedro-hos
 *
 */
@Stateless
public class AutorResourceImpl implements AutorResource {
	
	@Inject
	private Autores autores;
	
	@Inject
	private Leis leis;

	@Override
	public Response buscaTodosPaginados(int total, int pg) {
		List<Autor> autoresPaginados = RESTUtils.lanca404SeNulo(autores.todosPaginado(total, pg));
		return Response.ok().entity(autoresPaginados).build();
	}

	@Override
	public Response buscaPorNome(String nome) {
		Autor autor = RESTUtils.lanca404SeNulo(autores.comName(nome));
		return Response.ok().entity(autor).build();
	}

	@Override
	public Response buscaId(Long id) {
		Autor autor = RESTUtils.lanca404SeNulo(autores.buscarPorId(id));
		return Response.ok().entity(autor).build();
	}

	@Override
	public Response buscaLeisPorAutor(Long id) {
		List<Lei> leisDoAutor = RESTUtils.lanca404SeNulo(leis.doAutor(id));
		return Response.ok().entity(leisDoAutor).build();
	}

}
