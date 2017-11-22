package com.sjcdigital.temis.model.service.bots.presenca;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sjcdigital.temis.annotations.Property;
import com.sjcdigital.temis.model.service.bots.AbstractBot;
import com.sjcdigital.temis.model.service.bots.exceptions.BotException;
import com.sjcdigital.temis.model.service.bots.presenca.dto.PresencaVereadores;
import com.sjcdigital.temis.model.service.bots.presenca.parser.AttendanceParser;

@Stateless
public class PresencasBot extends AbstractBot {

	@Inject
	private Logger logger;

	@Inject
	@Property("presenca.url.template")
	private String presencaPaginaUrl;

	@Inject
	@Property("presenca.urlarquivo.template")
	private String arquivoPresencaUrlTemplate;
	
	@Inject
	private AttendanceParser parser;
	
	@Inject 
	private PresencaExtractor presencaExtractor;

	@Override
	public void saveData() throws BotException {
		List<String> arquivosParaProcessar = new ArrayList<>();
		// passamos por todas as páginas e só paramos quando começar a repetir os arquivos de sessão
		logger.info("Iniciando carga de presença de vereadores");
		for (int i = 0; i < 1000; i++) {
			try {
				String paginaAtual = presencaPaginaUrl + i;
				logger.fine("Pegando links da página " + paginaAtual);
				Document pagina = getPage(paginaAtual);
				List<String> novosLinks = pegaLinksDePagina(pagina);
				boolean arquivoAdicionado = novosLinks.stream()
						.filter(arquivosParaProcessar::contains).findAny().isPresent();
				if(arquivoAdicionado) {
					break;
				}
				arquivosParaProcessar.addAll(novosLinks);
			} catch (IOException e) {
				// logger não aceita exceção..
				e.printStackTrace();
				logger.severe("Erro baixando abrindo página de busca: " + e.getMessage());
			}
		}
		logger.info("Coleta de linkas terminada. Quantidade de arquivos de presença a serem processados: " + arquivosParaProcessar.size());
		for (String urlArquivo : arquivosParaProcessar) {
			try {
				logger.fine("Processando arquivo: " + urlArquivo);
				InputStream openStream = new URL(urlArquivo).openStream();
				PresencaVereadores dadosPresenca = parser.parse(openStream);
				presencaExtractor.salvaPresencas(dadosPresenca);
			} catch (Exception e) {
				logger.severe("Erro processando arquivo: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private List<String> pegaLinksDePagina(Document pagina) {
		Elements linksParaArquivos = pagina.getElementsByAttributeValueStarting("href", arquivoPresencaUrlTemplate);
		return linksParaArquivos.stream().map(el -> el.attr("href")).collect(Collectors.toList());
		
	}

}