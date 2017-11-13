package com.sjcdigital.temis.resources.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.sjcdigital.temis.model.repositories.impl.SituacoesSimplificada;
import com.sjcdigital.temis.resources.SituacaoSimplificadaResource;

@Stateless
public class SituacaoSimplificadaResourceImpl implements SituacaoSimplificadaResource {
	
	@Inject
	private SituacoesSimplificada situacoes;

	@Override
	public Response todos() {
		return Response.ok(situacoes.todos()).build();
	}

}
