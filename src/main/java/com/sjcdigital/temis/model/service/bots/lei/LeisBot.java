package com.sjcdigital.temis.model.service.bots.lei;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.text.WordUtils;

import com.sjcdigital.temis.annotations.Property;
import com.sjcdigital.temis.model.entities.impl.Autor;
import com.sjcdigital.temis.model.entities.impl.Lei;
import com.sjcdigital.temis.model.entities.impl.PartidoPolitico;
import com.sjcdigital.temis.model.entities.impl.SituacaoSimplificada;
import com.sjcdigital.temis.model.entities.impl.Tipo;
import com.sjcdigital.temis.model.repositories.impl.Autores;
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
	private Autores autores;

	@Inject
	private SituacoesSimplificada situacoes;

	@Inject
	private PartidosPolitico partidos;

	@Inject
	private Tipos tipos;

	@Inject
	@Property("url.context")
	private String urlContext;

	@Inject
	@Property("url.pdf.leis")
	private String urlPDFLeis;

	@Inject
	@Property("path.leis.pdf")
	private String pathLeis;

	@Inject
	@Property("url.leis")
	private String url;

	@Inject
	@Property("pagina.atual")
	private String paginaAtual;
	@Inject
	@Property("pagina.atual.value")
	private Integer paginaAtualValue;

	@Inject
	@Property("quantidade.por.pagina")
	private String quantidadePorPagina;
	@Inject
	@Property("quantidade.por.pagina.value")
	private Integer quantidadePorPaginaValue;

	@Inject
	@Property("ordenacao")
	private String ordenacao;
	@Inject
	@Property("ordenacao.value")
	private String ordenacaoValue;

	@Inject
	@Property("termo")
	private String termo;
	@Inject
	@Property("termo.value")
	private String termoValue;

	@Inject
	@Property("ano.protocolo")
	private String anoProtocolo;
	@Inject
	@Property("ano.protocolo.value")
	private String anoProtocoloValue;

	@Inject
	@Property("nProc")
	private String nProc;
	@Inject
	@Property("nProc.value")
	private String nProcValue;

	@Inject
	@Property("nProp")
	private String nProp;
	@Inject
	@Property("nProp.value")
	private String nPropValue;

	@Inject
	@Property("autor")
	private String autor;
	@Inject
	@Property("autor.value")
	private String autorValue;

	@Inject
	@Property("tipoDoc")
	private String tipoDoc;
	@Inject
	@Property("tipoDoc.value")
	private String tipoDocValue;

	@Inject
	LeiClassficacaoQueueSender sender;

	@Override
	@Asynchronous
	public void saveData() throws BotException {
		
		logger.info("\n## Iniciando Captura de leis ##\n");

		Integer paginaAtual = this.paginaAtualValue;

		Optional<ArrayOfRetornoPesquisa> arrayOfRetornoPesquisa = getDocuments(paginaAtual);

		while (!arrayOfRetornoPesquisa.get().getRetornoPesquisa().isEmpty()) {

			logger.info("Realizando extração de leis");
			logger.info("[Página atual] " + paginaAtual);

			arrayOfRetornoPesquisa.get().getRetornoPesquisa().forEach(this::converteParaLei);
			arrayOfRetornoPesquisa = getDocuments(++paginaAtual);
		}

	}

	protected void converteParaLei(final RetornoPesquisa retornoPesquisa) {

		Lei lei = new Lei();

		lei.setAutor(buildAutor(retornoPesquisa.getAutor()));
		lei.setDcmId(retornoPesquisa.getDcmId());
		lei.setDctId(retornoPesquisa.getDctId());
		lei.setEmenta(retornoPesquisa.getEmenta().trim());
		lei.setNumeroProcesso(retornoPesquisa.getNumeroProcesso());
		lei.setNumeroPropositura(retornoPesquisa.getNumeroPropositura());
		lei.setQueryStringCriptografada(retornoPesquisa.getQueryStringCriptografada());
		lei.setSituacao(retornoPesquisa.getSituacao());
		lei.setPdfLei(MessageFormat.format(urlPDFLeis, retornoPesquisa.getDcmId(), retornoPesquisa.getQueryStringCriptografada()));
		lei.setSituacaoSimplificada(buildSituacaoSimplicada(retornoPesquisa.getSituacaoSimplificada()));
		lei.setTipo(novoTipo(retornoPesquisa.getTipo()));

		sender.send(lei);

	}

	private Tipo novoTipo(String nome) {

		nome = nome.replaceAll(" - \\(\\w+\\)", "");

		Optional<Tipo> tipo = tipos.comNome(nome);

		if (tipo.isPresent()) {
			return tipo.get();
		}

		logger.info("tipo " + nome);

		Tipo novoTipo = new Tipo(WordUtils.capitalizeFully(nome));
		tipos.salvar(novoTipo);

		return novoTipo;
	}

	private SituacaoSimplificada buildSituacaoSimplicada(String nome) {

		Optional<SituacaoSimplificada> situacaoSimplificada = situacoes.comNome(nome);

		if (situacaoSimplificada.isPresent()) {
			return situacaoSimplificada.get();
		}

		logger.info("Situação Simplificada: " + nome);

		SituacaoSimplificada novaSituacaoSimplificada = new SituacaoSimplificada(WordUtils.capitalizeFully(nome));
		situacoes.salvar(novaSituacaoSimplificada);

		return novaSituacaoSimplificada;
	}

	private Autor buildAutor(String autorEPartido) {

		// Começou a patifaria ....
		autorEPartido = autorEPartido.toLowerCase().trim()
												   .replaceAll("\\w*\\s*\\[", "")
												   .replaceAll("\\].*", "")
												   .replaceAll("verª", "")
												   .replaceAll("ver ", "")
												   .replaceAll("((v?)e(r?)e(r?)ador(es|as|a){0,1}|ver\\.|vers\\.)\\s*", "")
												   .replaceAll("(s\\/a|ltda|ltd|ldta|s\\.a).*", "")
												   .replaceAll("e outro(s{0,1})", "")
												   .trim();

		Matcher matcher = RegexUtils.getMatcher("(.+)\\s*(-|–)\\s*(\\w+)", autorEPartido);

		String autor = autorEPartido;
		String siglaPartido = "";

		if (matcher.find()) {
			autor = matcher.group(1).trim();
			siglaPartido = matcher.group(3).toUpperCase().trim();
		}
		
		Autor vereador = pegaUsuarioJaroWinklerDistance(trataAutor(autor), siglaPartido);
		vereador.setQuantidadeDeLeis(vereador.getQuantidadeDeLeis().add(BigInteger.ONE));
		
		return vereador;

	}
	
	private static String trataAutor(final String autor) {
		
		String autorClean = autor;
		
		autorClean = autorClean.replaceAll("^ana paula s\\. melo$", "ana paula de souza melo");
		
		autorClean = autorClean.replaceAll("^professor calasans camargo - prp$", "prof. calasans camargo - prp");
		
		autorClean = autorClean.replaceAll("^carlinhos tiaca$|"
										 + "^carlinhos tiaca \\(pmdb\\)$|"
										 + "^carlinhos tiaca pmdb$", "carlinhos tiaca");
				
		autorClean = autorClean.replaceAll("^zé luís – psd$", "zé luís");
		
		autorClean = autorClean.replaceAll("^willis$|"
										 + "^willis goulart$", "willis goulart");
		
		autorClean = autorClean.replaceAll("^arcante engenharia e topografia$|"
										 + "^arcanteengenharia e topografia$", "arcante engenharia e topografia");

		autorClean = autorClean.replaceAll("^cerqueira torres$|"
										 + "^cerqueira torres construção terraplanagem e pavimentação$|"
										 + "^cerqueira torres construção terraplenagem e pavimentação$|"
										 + "^cerqueira torres construções terraplanagem e pavimentação$|"
										 + "^cerqueira torres construções terraplenagem e pavimentação$|"
										 + "^cerqueira torres construções terraplenagem pavimentação$|"
										 + "^cerqueira torres construções, terraplenagem e pavimentação$", "cerqueira torres construções terraplanagem e pavimentação");

		autorClean = autorClean.replaceAll("^ckr engenharia e construção$|"
										 + "^ckr engenharia$|"
										 + "^ckr engenharia e construções$", "ckr - engenharia e construções");

		autorClean = autorClean.replaceAll("^consórcio cedin$|"
										 + "^consórcio cedin sjc$|"
										 + "^consórcio sjc cedin$", "consorcio sjc cedin");

		autorClean = autorClean.replaceAll("^construção, engenharia e pavimentação enpavi$|"
										 + "^construções engenharia e pavimentação enpavi$|"
										 + "^construções, engenharia e pavimentação enpavi$|"
										 + "^construções, engenharia e pavimentações enpavi$|"
										 + "^construções, engenharias e pavimentação enpavi$|"
										 + "^construções,engenharia e pavimentação enpavi$|"
										 + "^enpavi construção engenharia e pavimentação$|"
										 + "^enpavi construções engenharia e pavimentação$|"
										 + "^enpavi construções, engenharia e pavimentação$|"
										 + "^enpavi construções, engenharia e pavimentação$", "construções engenharia e pavimentação enpavi");

		autorClean = autorClean.replaceAll("^construjac$|"
										 + "^construjac construtora e incorporadora$|"
										 + "^construjac martins$", "construjac construtora e incorporadora");
		
		autorClean = autorClean.replaceAll("^construtora & incorporadora zanini sjcampos$|"
										 + "^construtora e incorporadora zanini sjcampos$|"
										 + "^zanini construtora e incorporadora sjcampos$|"
										 + "^zanini engenharia$|"
										 + "^zanini construtora e incorporadora$|"
										 + "^construtora & incorporadora zanini$|"
										 + "^zanini engenharia construtora e incorporadora$", "construtora e incorporadora zanini sjcampos");
		
		autorClean = autorClean.replaceAll("^construtora marais correa$|"
										 + "^construtora moraes correa$|"
										 + "^construtora morais correa$|"
										 + "^construtura morais correa$", "construtora morais correa");
		
		autorClean = autorClean.replaceAll("^construtra bugre$|"
										 + "^construtura bugre$|"
										 + "^contrutora bugre$", "construtora bugre");
		
		autorClean = autorClean.replaceAll("^crisitiano ferreira$|"
										 + "^cristano ferreira$|"
										 + "^cristiano ferreira$|"
										 + "^cristiano ferreira -$|"
										 + "^cristiano ferreira pv$|"
										 + "^cristiano ferreiro$|"
										 + "^cristiano ferreria$|"
										 + "^cristino ferreira$", "cristiano ferreira");
		
		autorClean = autorClean.replaceAll("^cristóvão gonçalves$|"
										 + "^critóvão gonçalves$", "cristóvão gonçalves");
		
		autorClean = autorClean.replaceAll("^crk engenharia e construção$|"
										 + "^crk engenharia e construções$","crk engenharia e construção");

		autorClean = autorClean.replaceAll("^dr. roniel$|"
										 + "^afrâdr. roniel$", "dr roniel");

		autorClean = autorClean.replaceAll("^ecg engenharia construções e geotecnia$|"
										 + "^ecg engenharia construção e geotecnia$|"
										 + "^ecg engenharia construções e geotecnia$|"
										 + "^ecg engenharia constuções e geotecnia$|"
										 + "^egc engenharia construções e geotecnia$|"
										 + "^ecg  engenharia construções e geotecnia$|"
										 + "^egc engenharia e geotecnia$","ecg engenharia construções e geotecnia");

		autorClean = autorClean.replaceAll("^elefe const. e incorporadora$|"
										 + "^elefe construtora e incorporadora$|"
										 + "^elefe construtora incorporadora$|"
										 + "^elefe engenharia, construtora e incorporadora$", "elefe construtora incorporadora, construção civil");

		autorClean = autorClean.replaceAll("^eliel alves rodirgues$", "eliel alves rodrigues");

		autorClean = autorClean.replaceAll("^empavi construções , engenharia e pavimentação$|"
										 + "^empavi construções engenharia e pavimentação$|"
									 	 + "^construções, engenharia e pavimentação empavi$", "construções engenharia e pavimentação empavi");

		autorClean = autorClean.replaceAll("^eneida dilermando dié$", "dilermando dié");

		autorClean = autorClean.replaceAll("^engcret serviçõs de engenharia$|"
										 + "^engecret serviços de engenharia$", "engcret servicos de engenharia");

		autorClean = autorClean.replaceAll("^engeseemi engenharia$|"
										 + "^engseemi engenharia$", "engseemi engenharia");

		autorClean = autorClean.replaceAll("^fundação cassiano ricardo$|"
										 + "^fundação cultura cassiano ricardo$|"
										 + "^fundação cultural cassiano ricardo$", "fundação cultural cassiano ricardo");

		autorClean = autorClean.replaceAll("^g.b.v.t engenharia e construções$|"
										 + "^g.b.v.t. engenharia e construções$|"
										 + "^g.b.v.t$|"
										 + "^gbvt engenharia e construções$", "gbvt engenharia e construções");

		autorClean = autorClean.replaceAll("^gabriel josé andrade nogueira$|"
										 + "^gabriel josé de andrade nogueira$", "gabriel josé de andrade nogueira");
		
		autorClean = autorClean.replaceAll("^gestão de contratos$|"
										 + "^gestor (a) de contratos$|"
										 + "^gestor(a) de contratos$|"
										 + "^gestor(a) de coontratos$|"
										 + "^gestora de contratos$", "gestão de contratos");

		autorClean = autorClean.replaceAll("^guimarães & marques suprimentos para informática$|"
										 + "^guimarães e marques suprimentos para informática$", "guimarães e marques suprimentos para informática");

		autorClean = autorClean.replaceAll("^jairo de freitas$|"
										 + "^jairo freitas$", "jairo freitas");

		autorClean = autorClean.replaceAll("^joão d merces tampao (ptb)$|"
										 + "^joão das mercês tampão$|"
										 + "^joão das mercês tampão$|"
										 + "^joão das mercês tampão (ptb)$", "joão das mercês tampão");

		autorClean = autorClean.replaceAll("^kraoh engenharia ambiental$|"
										 + "^kraoh engenharia florestal$|"
										 + "^kraoh engenharia florestal s/s$|"
										 + "^kroah engenharia florestal s/s$", "kraoh engenharia florestal s/s");

		autorClean = autorClean.replaceAll("^luiz henrique santos dir. expediente$|"
				 						 + "^luiz henrique santos silva$", "luiz henrique santos silva");

		autorClean = autorClean.replaceAll("^mam$|"
										 + "^mam construtora e terraplanagem$|"
										 + "^mam construtora e terraplenagem$|"
										 + "^man construtora e terraplanagem$", "mam construtora e terraplanagem");

		autorClean = autorClean.replaceAll("^marprado construção civil$|"
										 + "^marprado cosntrucao civil$|"
										 + "^marprado engenharia$", "marprado construção civil");

		autorClean = autorClean.replaceAll("^metaflora$|"
										 + "^metaflora sistemas ambientais e limpeza industrial$", "metaflora sistemas ambientais e limpeza industrial");

		autorClean = autorClean.replaceAll("^mirada ueb$|"
										 + "^miramda ueb$|"
										 + "^miranda da ueb$|"
										 + "^miranda ueb$", "miranda ueb");

		autorClean = autorClean.replaceAll("^objetivo construção civil e pavimentação$|"
										 + "^objetivo construção e pavimentação$|"
										 + "^objetivo paviemntação$|"
										 + "^objetivo pavimentação$|"
										 + "^objetivo pavimentações$", "objetivo construção civil e pavimentação");

		autorClean = autorClean.replaceAll("^petiti da farmácia comunitária$|"
									     + "^petiti da farmacia comunitária -$", "petiti da farmácia comunitária");

		autorClean = autorClean.replaceAll("^plantec$|"
										 + "^plantec planejamento e engenharia agrônomica$", "plantec planejamento e engenharia agrônomica");

		autorClean = autorClean.replaceAll("^poder execuivo$", "poder executivo");

		autorClean = autorClean.replaceAll("^pregoeiro (a)$", "pregoeiro(a)");

		autorClean = autorClean.replaceAll("^recoma|recoma construções comercio e industria$|"
										 + "^recoma construções, comércio e indústria$|"
										 + "^recoma contruções, comércio e indústria$", "recoma - construções, comércio e indústria");

		autorClean = autorClean.replaceAll("^santos neves$|"
										 + "^santos neves pan$", "santos neves");

		autorClean = autorClean.replaceAll("^secretaria de esporte e lazer$|"
										 + "^secretaria de esportes e lazer$", "secretaria de esporte e lazer");

		autorClean = autorClean.replaceAll("^sol r. a. urbanizadora$|"
										 + "^sol r.a urbanizadora$|"
										 + "^sol r.a. urbanizadora$|"
										 + "^sol urbanizadora$", "sol r. a. urbanizadora");

		autorClean = autorClean.replaceAll("^spalla engenharia eireli$", "spalla engenharia e construções");

		autorClean = autorClean.replaceAll("^stemmi engenharia construções$|"
										 + "^stemmi engenharia e construções$", "stemmi engenharia e construções");

		autorClean = autorClean.replaceAll("^suely lobato$|"
										 + "^suely lobato de oliveira$", "suely lobato de oliveira");

		autorClean = autorClean.replaceAll("^supervisão de transportes$|"
										 + "^supervisão de trnsportes$", "supervisão de transportes");

		autorClean = autorClean.replaceAll("^teixeira de freitas engenharia e comercio$|"
										 + "^teixeira de freitas eng. e com.$|"
										 + "^teixeira de freitas engenharia e comercio$", "teixeira de freitas engenharia e comércio");

		autorClean = autorClean.replaceAll("^teorema engenharia e construção$|"
										 + "^teorema engenharia e construções$|"
										 + "^teorema engenharia e contruções$", "teorema engenharia e construções");

		autorClean = autorClean.replaceAll("^valdir alvarenga$|"
										+ "^valdir alvarenga - solidariedade$", "valdir alvarenga");

		autorClean = autorClean.replaceAll("^vieira lima engenharia e construtora$|"
										 + "^vieira lima eng. e construção$|"
										 + "^vieira lima engenharia$|"
										 + "^vieira lima engenharia e construção$|"
										 + "^vieiralima engenharia e construção$|"
										 + "^viera lima engenhara$|"
										 + "^viera lima engenharia$|"
										 + "^vieralima engenharia e construção$", "vieira lima engenharia");
		 
		return autorClean;
	}

	private PartidoPolitico buildPartidoPolitico(String siglaPartido) {

		Optional<PartidoPolitico> partido = partidos.comSigla(siglaPartido);

		if (partido.isPresent()) {
			return partido.get();
		}

		return null;
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
			
			ArrayOfRetornoPesquisa readEntity = response.readEntity(ArrayOfRetornoPesquisa.class);

			if (Objects.isNull(readEntity) || readEntity.getRetornoPesquisa().isEmpty()) {
				return Optional.empty(); 
			}

			return Optional.ofNullable(readEntity);

		} catch (Exception e) {
			logger.severe(ExceptionUtils.getStackTrace(e));
			return Optional.empty();

		} finally {

			if (Objects.nonNull(response)) {
				response.close();
				client.close();
			}
		}
	}

	private Autor pegaUsuarioJaroWinklerDistance(final String nome, final String siglaPartido) {

		Map<Double, Autor> names = new HashMap<>();
		Optional<List<Autor>> autorList = autores.comNameList(nome);

		if (autorList.get().isEmpty()) {
			Autor autorNovo = new Autor(WordUtils.capitalizeFully(nome), buildPartidoPolitico(siglaPartido));
			autores.salvarNovaTransacao(autorNovo);
			return autorNovo;
		}

		if (autorList.get().size() == 1) {
			return autorList.get().get(0);
		}

		for (Autor autor : autorList.get()) {
			double distance = StringUtils.getJaroWinklerDistance(nome, autor.getNome().toLowerCase());
			logger.info("Jaro Winkler Distance: [" + distance + "] " + nome + " e " + autor.getNome());
			names.put(distance, autor);
		}

		return names.entrySet().stream().sorted(Map.Entry.<Double, Autor>comparingByKey().reversed()).findFirst().get()
				.getValue();
	}
}


