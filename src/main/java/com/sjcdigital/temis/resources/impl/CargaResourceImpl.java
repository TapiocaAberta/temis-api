package com.sjcdigital.temis.resources.impl;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.sjcdigital.temis.model.service.bots.autor.AutorBot;
import com.sjcdigital.temis.model.service.bots.exceptions.BotException;
import com.sjcdigital.temis.model.service.bots.lei.LeisBot;
import com.sjcdigital.temis.resources.CargaResource;

/**
 * @author pesilva
 *
 */
@Stateless
public class CargaResourceImpl implements CargaResource {

	@Inject
	private Logger logger;

	@Inject
	private AutorBot vereadorBot;

	@Inject
	private LeisBot leisBot;

	@Override
	public Response carregaVereadoresELeis() {

		try {

			logger.info("## Iniciando carga de dados ##\n");

			vereadorBot.saveData();
			leisBot.saveData();

		} catch (BotException e) {
			logger.severe(ExceptionUtils.getStackTrace(e));
			Response.serverError().entity(ExceptionUtils.getMessage(e)).build();
		}

		return Response.ok().entity("Dados sendo carregados, veja o log da aplicação para mais detalhes").build();
	}
}
