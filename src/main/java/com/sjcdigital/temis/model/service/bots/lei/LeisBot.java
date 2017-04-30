package com.sjcdigital.temis.model.service.bots.lei;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.sjcdigital.temis.annotations.Property;
import com.sjcdigital.temis.model.entities.impl.Autor;
import com.sjcdigital.temis.model.entities.impl.Lei;
import com.sjcdigital.temis.model.repositories.impl.Autores;
import com.sjcdigital.temis.model.repositories.impl.Leis;
import com.sjcdigital.temis.model.repositories.impl.PartidosPolitico;
import com.sjcdigital.temis.model.repositories.impl.SituacoesSimplificada;
import com.sjcdigital.temis.model.repositories.impl.Tipos;
import com.sjcdigital.temis.model.service.bots.AbstractBot;
import com.sjcdigital.temis.model.service.bots.exceptions.BotException;
import com.sjcdigital.temis.model.service.bots.lei.dtos.ArrayOfRetornoPesquisa;
import com.sjcdigital.temis.model.service.bots.lei.dtos.RetornoPesquisa;
import com.sjcdigital.temis.utils.RegexUtils;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class LeisBot extends AbstractBot {

	@Inject
	private Logger logger;

	@Inject
	private Leis leis;
	
	@Inject
	private Autores vereadores;
	
	@Inject
	private SituacoesSimplificada situacoes;
	
	@Inject
	private PartidosPolitico partidos;
	
	@Inject
	private Tipos tipos;
	
	@Inject @Property("url.context") private String urlContext;
	
	@Inject @Property("url.pdf.leis") private String urlPDFLeis;
	
	@Inject @Property("path.leis.pdf") private String pathLeis;
	
	@Inject @Property("url.leis") private String url;
	
	@Inject @Property("pagina.atual") private String paginaAtual;
	@Inject @Property("pagina.atual.value") private Integer paginaAtualValue;
	
	@Inject @Property("quantidade.por.pagina") private String quantidadePorPagina;
	@Inject @Property("quantidade.por.pagina.value") private Integer quantidadePorPaginaValue;
	
	@Inject @Property("ordenacao") private String ordenacao;
	@Inject @Property("ordenacao.value") private String ordenacaoValue;
	
	@Inject @Property("termo") private String termo;
	@Inject @Property("termo.value") private String termoValue; 
	
	@Inject @Property("ano.protocolo") private String anoProtocolo; 
	@Inject @Property("ano.protocolo.value") private String anoProtocoloValue; 
	
	@Inject @Property("nProc") private String nProc; 
	@Inject @Property("nProc.value") private String nProcValue;
	
	@Inject @Property("nProp") private String nProp;
	@Inject @Property("nProp.value") private String nPropValue; 
	
	@Inject @Property("autor") private String autor;
	@Inject @Property("autor.value") private String autorValue;
	
	@Inject @Property("tipoDoc") private String tipoDoc;
	@Inject @Property("tipoDoc.value") private String tipoDocValue;
	
	
	@Override
	public void saveData() throws BotException {
		
		Integer paginaAtual = this.paginaAtualValue;
		
		Optional<ArrayOfRetornoPesquisa> arrayOfRetornoPesquisa = getDocuments(paginaAtual);
		
		while(arrayOfRetornoPesquisa.isPresent()) {
			
			logger.info("Realizando extração de leis");
			logger.info("[Página atual] " + this.paginaAtualValue);
			
			for(RetornoPesquisa retornoPesquisa : arrayOfRetornoPesquisa.get().getRetornoPesquisa()) {
				salva(converteParaLei(retornoPesquisa));
			}
			
			arrayOfRetornoPesquisa = getDocuments(++paginaAtual);
		}
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	protected void salva(Lei lei) {
		leis.salvar(lei);
	}
	
	protected Lei converteParaLei(final RetornoPesquisa retornoPesquisa) {
		
		Lei lei = new Lei();
		
		lei.setAutor(getVereador(retornoPesquisa.getAutor()));
		lei.setDcmId(retornoPesquisa.getDcmId());
		lei.setDctId(retornoPesquisa.getDctId());
		lei.setEmenta(retornoPesquisa.getEmenta());
		lei.setNumeroProcesso(retornoPesquisa.getNumeroProcesso());
		lei.setNumeroPropositura(retornoPesquisa.getNumeroPropositura());
		lei.setQueryStringCriptografada(retornoPesquisa.getQueryStringCriptografada());
		lei.setSituacao(retornoPesquisa.getSituacao());
		lei.setPdfLei(MessageFormat.format(urlPDFLeis, retornoPesquisa.getDcmId(), retornoPesquisa.getQueryStringCriptografada()));
		lei.setSituacaoSimplificada(situacoes.comNome(retornoPesquisa.getSituacaoSimplificada()).orElse(null));
		lei.setTipo(tipos.comNome(retornoPesquisa.getSituacaoSimplificada()).orElse(null));
		
		return lei;
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	private Autor getVereador(String autorEPartido) {
		
		logger.info("Tratando autor: " + autorEPartido);
		
		//Começou a patifaria ....
		autorEPartido = autorEPartido.toLowerCase()
									 .trim()
									 .replaceAll("ver.", "")
									 .replaceAll("professor calasans camargo - prp", "prof. calasans camargo - prp")
									 .trim();
		
		Matcher matcher = RegexUtils.getMatcher("(.+) - (\\w+)", autorEPartido);
		
		String autor = autorEPartido;
		String siglaPartido = "";
		
		if(matcher.find()) {
			autor = matcher.group(1);
			siglaPartido = matcher.group(2).toUpperCase();
		}
		
		Optional<Autor> vereador = vereadores.comName(autor);
		return vereador.orElse(new Autor(WordUtils.capitalize(autor), partidos.comSigla(siglaPartido).orElse(null)));
	}

	protected Optional<ArrayOfRetornoPesquisa> getDocuments(final Integer paginaAtualValue) {

		Client client = ClientBuilder.newClient();
		
		WebTarget target = client.target(url).queryParam(paginaAtual, paginaAtualValue)
											 .queryParam(quantidadePorPagina, quantidadePorPaginaValue)
											 .queryParam(ordenacao, ordenacaoValue)
											 .queryParam(termo, termoValue)
											 .queryParam(anoProtocolo, anoProtocoloValue)
											 .queryParam(nProc, nProcValue)
											 .queryParam(nProp, nPropValue)
											 .queryParam(autor, autorValue)
											 .queryParam(tipoDoc, tipoDocValue);
		
		Response response = null;

		try {
			
			response = target.request().get();
			logger.info("Status: " + String.valueOf(response.getStatus()));
			
			return Optional.ofNullable(response.readEntity(ArrayOfRetornoPesquisa.class));
			
		} catch(Exception e){
			logger.severe(ExceptionUtils.getStackTrace(e));
			return Optional.empty();
			
		} finally {
			
			if(Objects.nonNull(response)) {
				response.close();
				client.close();
			}
		}
	}
}
