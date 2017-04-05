package com.sjcdigital.temis.model.service.bots.lei;

import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.sjcdigital.temis.annotations.Property;
import com.sjcdigital.temis.model.entities.impl.Lei;
import com.sjcdigital.temis.model.entities.impl.Vereador;
import com.sjcdigital.temis.model.repositories.impl.Leis;
import com.sjcdigital.temis.model.service.bots.AbstractBot;
import com.sjcdigital.temis.model.service.bots.exceptions.BotException;
import com.sjcdigital.temis.model.service.bots.lei.dtos.ArrayOfRetornoPesquisa;
import com.sjcdigital.temis.model.service.bots.lei.dtos.RetornoPesquisa;

/**
 * @author pedro-hos
 *
 */
public class LeisBot extends AbstractBot {

	@Inject
	private Logger logger;

	@Inject
	private Leis leis;
	
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
		
		getDocuments().ifPresent(r -> {
			r.getRetornoPesquisa().forEach(rp -> {
				Lei lei = converteParaLei(rp);
				leis.salvar(lei);
			});
		});
		
	}
	
	protected Lei converteParaLei(RetornoPesquisa retornoPesquisa) {
		
		Lei lei = new Lei();
		
		lei.setAutor(getVereador());
		lei.setDcmId(retornoPesquisa.getDcmId());
		lei.setDctId(retornoPesquisa.getDctId());
		lei.setEmenta(retornoPesquisa.getEmenta());
		lei.setNumeroProcesso(retornoPesquisa.getNumeroProcesso());
		lei.setNumeroPropositura(retornoPesquisa.getNumeroPropositura());
		lei.setQueryStringCriptografada(retornoPesquisa.getQueryStringCriptografada());
		lei.setSituacao(retornoPesquisa.getSituacao());
		
		return lei;
	}

	private Vereador getVereador() {
		return null;
	}

	protected Optional<ArrayOfRetornoPesquisa> getDocuments() {

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
			logger.info(String.valueOf(response.getStatus()));
			return Optional.ofNullable(response.readEntity(ArrayOfRetornoPesquisa.class));
			
			
		} catch(Exception e){
			return Optional.empty();
			
		} finally {
			
			if(Objects.nonNull(response)) {
				response.close();
				client.close();
			}
		}
	}
}
