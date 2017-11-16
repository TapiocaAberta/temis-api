package com.sjcdigital.temis.resources.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sjcdigital.temis.model.dto.Data;
import com.sjcdigital.temis.model.dto.Mensagem;
import com.sjcdigital.temis.model.entities.impl.Lei;
import com.sjcdigital.temis.model.entities.impl.Tipo;
import com.sjcdigital.temis.model.repositories.impl.Leis;
import com.sjcdigital.temis.model.repositories.impl.Tipos;
import com.sjcdigital.temis.model.service.machine_learn.TreinaClassificacao;
import com.sjcdigital.temis.model.service.machine_learn.exception.TreinoException;
import com.sjcdigital.temis.resources.MachineLernResource;

/**
 * @author pesilva
 *
 */
@Stateless
public class MachineLearnResourceImpl implements MachineLernResource {

	@Inject
	private Logger logger;

	@Inject
	private Leis leis;
	
	@Inject
	private Tipos tipos;
	
	@Inject
	private TreinaClassificacao treinar;

	@Override
	public Response geraDataParaML() {
		
		Map<String, Integer> qnt = new HashMap<>();
		List<Lei> leisSorteadas = new ArrayList<>();
		
		qnt.put("Projeto de Lei", 1000);
		qnt.put("Projeto de Decreto Legislativo", 200);
		qnt.put("Requerimento", 1000);
		qnt.put("Projeto de Lei Complementar", 160);
		qnt.put("Projeto de Resolução", 45);
		qnt.put("Indicação", 1000);
		qnt.put("Moção", 1000);
		qnt.put("Diversos", 1000);
		qnt.put("Projeto De Emenda À Lei Orgânica Municipal", 14);
		qnt.put("Emenda", 1);
		
		for (Tipo tipo : tipos.todos()) {
			leisSorteadas.addAll(leis.comTipo(tipo.getId(), qnt.get(tipo.getNome()), 0));
		}

		List<Data> datas = new ArrayList<>();
		
		
		for (Lei lei : leisSorteadas) {
			
			String ementa = lei.getEmenta().replace("\n", "").replace("\t", "").replace("\r", "");
			logger.info(ementa);
			
			datas.add(new Data(ementa, lei.getTipo().getNome()));
		}
		
		return Response.ok(datas).build();
	}

	@Override
	public Response treinaMaquina() {
		
		try {
			treinar.run();
			return Response.ok(new Mensagem("Classificação executada com sucesso!")).build();
		} catch (TreinoException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new Mensagem("Falha na classificação, leia o log!")).build();
		}
		
	}

}
