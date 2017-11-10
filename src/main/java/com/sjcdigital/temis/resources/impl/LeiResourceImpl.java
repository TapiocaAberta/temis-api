package com.sjcdigital.temis.resources.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.sjcdigital.temis.model.entities.impl.Lei;
import com.sjcdigital.temis.model.repositories.impl.Leis;
import com.sjcdigital.temis.resources.LeiResource;
import com.sjcdigital.temis.utils.RESTUtils;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class LeiResourceImpl implements LeiResource {
	
	@Inject
	private Leis leis;

	@Override
	public Response buscaTodosPaginados(int total, int pg) {
		List<Lei> leisPaginados = RESTUtils.lanca404SeNulo(leis.todosPaginado(total, pg));
		return Response.ok().entity(leisPaginados).build();
	}

	@Override
	public Response buscaPorSituacaoSimplificada(Long id) {
		List<Lei> leisPaginados = RESTUtils.lanca404SeNulo(leis.comSituacaoSimplificada(id));
		return Response.ok().entity(leisPaginados).build();
	}

	@Override
	public Response buscaPorTipo(Long id, int total, int pg) {
		List<Lei> leisPaginados = RESTUtils.lanca404SeNulo(leis.comTipo(id, total, pg));
		return Response.ok().entity(leisPaginados).build();
	}

	@Override
	public Response buscaPorClasse(Long id) {
		List<Lei> leisPaginados = RESTUtils.lanca404SeNulo(leis.comClasse(id));
		return Response.ok().entity(leisPaginados).build();
	}

}
