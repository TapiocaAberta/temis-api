package com.sjcdigital.temis.resources.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.sjcdigital.temis.model.repositories.impl.Tipos;
import com.sjcdigital.temis.resources.TipoResource;

@Stateless
public class TipoResourceImpl implements TipoResource {
	
	@Inject
	private Tipos tipos;

	@Override
	public Response todos() {
		return Response.ok(tipos.todos()).build();
	}

}
