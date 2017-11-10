package com.sjcdigital.temis.resources.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.sjcdigital.temis.model.entities.impl.Lei;
import com.sjcdigital.temis.model.entities.impl.Tipo;
import com.sjcdigital.temis.model.repositories.impl.Leis;
import com.sjcdigital.temis.model.repositories.impl.Tipos;
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
			datas.add(new Data(lei.getEmenta(), lei.getTipo().getNome()));
		}
		
		logger.info("Data size - " + datas.size());

		return Response.ok(datas).build();
	}

	public static class Data {

		public Data(String text, String tipo) {
			this.text = text;
			this.tipo = tipo;
			this.tag = "SEM_CLASSIFICACAO";
		}
		
		private String tag;
		private String tipo;
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

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

	}

}
