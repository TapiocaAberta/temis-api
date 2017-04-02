package com.sjcdigital.temis.model.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.sjcdigital.temis.annotations.Property;
import com.sjcdigital.temis.model.dto.ArrayOfRetornoPesquisa;

/**
 * @author pedro-hos
 */

@Stateless
public class ExtractLawWS {
	
	@Inject @Property("url.laws") private String url;
	
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

	public ArrayOfRetornoPesquisa extract() {

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
		
		Response response = target.request().get();

		try {
			
			System.out.println(response.getStatus());
			ArrayOfRetornoPesquisa entity = response.readEntity(ArrayOfRetornoPesquisa.class);
			
			return entity;
			
		} finally {
			response.close();
			client.close();
		}

	}

}
