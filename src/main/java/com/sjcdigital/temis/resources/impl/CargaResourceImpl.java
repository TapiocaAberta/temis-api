package com.sjcdigital.temis.resources.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.sjcdigital.temis.model.entities.impl.Lei;
import com.sjcdigital.temis.model.repositories.impl.Leis;
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

	@Inject
	private Leis leis;

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

	@Override
	public Response geraDataParaML() {

		List<Long> ids = new ArrayList<>();
		int max = 3000;

		while (ids.size() != max) {
			Long randon = new Long(new Random().nextInt(80000));

			if (!ids.contains(randon)) {
				ids.add(randon);
			}
		}

		List<Lei> leisSorteadas = leis.comIds(ids);
		List<Data> datas = new ArrayList<>();
		
		for (Lei lei : leisSorteadas) {
			datas.add(new Data(lei.getEmenta()));
		}

		return Response.ok(datas).build();
	}

	public static class Data {

		public Data(String text) {
			this.text = text;
			this.tag = "SEM_CLASSIFICACAO";
		}
		
		private String tag;
		private String text;

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getTag() {
			return tag;
		}

		public void setTag(String tag) {
			this.tag = tag;
		}

	}

}
