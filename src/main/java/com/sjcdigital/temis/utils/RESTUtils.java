package com.sjcdigital.temis.utils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class RESTUtils {

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

}
