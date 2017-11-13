package com.sjcdigital.temis.resources.impl;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.sjcdigital.temis.model.repositories.impl.Classes;
import com.sjcdigital.temis.resources.ClasseResource;

@Stateless
public class ClasseResourceImpl implements ClasseResource {
	
	@Inject
	private Classes classes;

	@Override
	public Response todos() {
		return Response.ok(classes.todos()).build();
	}

}
