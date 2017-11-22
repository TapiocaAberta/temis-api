package com.sjcdigital.temis.model.service.bots.presenca;

import java.sql.Date;
import java.util.Optional;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.sjcdigital.temis.model.entities.impl.Autor;
import com.sjcdigital.temis.model.entities.impl.Legislatura;
import com.sjcdigital.temis.model.entities.impl.Presenca;
import com.sjcdigital.temis.model.entities.impl.Sessao;
import com.sjcdigital.temis.model.repositories.impl.Autores;
import com.sjcdigital.temis.model.repositories.impl.Legislaturas;
import com.sjcdigital.temis.model.repositories.impl.Presencas;
import com.sjcdigital.temis.model.repositories.impl.Sessoes;
import com.sjcdigital.temis.model.service.bots.presenca.dto.PresencaVereadores;

@Stateless
public class PresencaExtractor {
	
	@Inject
	private Logger logger;
	
	@Inject
	private Presencas presencas;
	
	@Inject
	private Sessoes sessoes;
	
	@Inject
	private Legislaturas legislaturas;
	
	@Inject
	private Autores autores;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void salvaPresencas(PresencaVereadores dadosPresencas) {
		String nomeLegistatura = dadosPresencas.getLegislatura();
		Legislatura legislatura = legislaturas.comNome(nomeLegistatura).orElseGet(() -> {
			logger.info("Legislatura não encontrada... Criando:" + nomeLegistatura);
			Legislatura novaLegislatura = new Legislatura(nomeLegistatura);
			legislaturas.salvar(novaLegislatura);
			return novaLegislatura;
		});
		String nomeSessao = dadosPresencas.getSessao();
		Sessao sessao = sessoes.comNome(nomeSessao).orElseGet(() -> {
			logger.info("Sessão não encontrada... Criando:" + nomeSessao);
			// TODO: retrieve date from the String to create the actual date (which is not important right now)
			Date data = new Date(new java.util.Date().getTime());
			Sessao novaSessao = new Sessao(nomeSessao, data, legislatura);
			novaSessao.setDataTexto(dadosPresencas.getData());
			sessoes.salvar(novaSessao);
			return novaSessao;
		});
		dadosPresencas.getAttendance().forEach((nomeAutor, presencaStr) -> {
			Autor autor = null;
			Optional<Autor> resBuscaAutor = autores.comName(nomeAutor);
			if(!resBuscaAutor.isPresent()) {
				logger.info("Autor não encontrado: " + nomeAutor + ". Registro não será criado.");
				return;
			}
			autor = resBuscaAutor.get();
			boolean comparecimento = presencaStr.toLowerCase().equals("sim");
			Presenca presenca = new Presenca(autor, sessao, comparecimento);
			logger.info("Salvando presença: " + presenca);
			presencas.salvar(presenca);
		});
		logger.info("Carga de presenças finalizada");
	}
	
}
