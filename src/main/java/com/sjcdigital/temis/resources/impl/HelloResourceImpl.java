package com.sjcdigital.temis.resources.impl;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.sjcdigital.temis.annotations.Property;
import com.sjcdigital.temis.model.service.bots.author.VereadorBot;
import com.sjcdigital.temis.model.service.bots.exceptions.BotException;
import com.sjcdigital.temis.model.service.extrator.lei.LeisExtrator;
import com.sjcdigital.temis.resources.HelloResource;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class HelloResourceImpl implements HelloResource {
	
	@Inject
	private LeisExtrator extract;
	
	@Inject
	private VereadorBot bot;
	
	@Inject
	@Property("hello.world")
	private String helloWorld;
	
	@Inject
	@Property("path.images")
	private String path;

	@Override
	public Response sayHello( String text ) {
		
		System.out.println(path);
		
		if (Optional.ofNullable(text).isPresent()) {
			return Response.ok("Hello ".concat(text)).build();
		}

		return Response.ok(helloWorld).build();
	}

	@Override
	public Response extract() {
		
		try {
			bot.saveData();
		} catch (BotException e) {
			e.printStackTrace();
		}
		
		return Response.ok().build();
		//return Response.ok(extract.extract()).build();
	}

}
