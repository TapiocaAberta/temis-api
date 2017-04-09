package com.sjcdigital.temis.model.service.extrator.vereador;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sjcdigital.temis.annotations.Property;
import com.sjcdigital.temis.model.entities.impl.PartidoPolitico;
import com.sjcdigital.temis.model.entities.impl.Vereador;
import com.sjcdigital.temis.model.repositories.impl.PartidosPolitico;
import com.sjcdigital.temis.model.repositories.impl.Vereadores;
import com.sjcdigital.temis.utils.RegexUtils;
import com.sjcdigital.temis.utils.TemisFileUtil;
import com.sjcdigital.temis.utils.TemisStringUtils;

/**
 * @author pedro-hos
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class VereadorExtrator {
	
	@Inject
	private Logger logger;
	
	@Inject
	private Vereadores vereadores;
	
	@Inject
	private PartidosPolitico partidos;

	@Inject
	private TemisFileUtil fileUtil;
	
	@Inject
	@Property("path.vereadores.images")
	private String vereadorImagePath;
	
	@Inject
	@Property("url.context")
	private String urlContext;

	
	public void parse(final Document document) throws IOException {
		
		logger.info("Criando vereadores!");
		
		final Vereador vereador = extractAldermenInfo(document.select("div.row.info")); // <div class="row info">
		saveOrUpdate(vereador);
	
	}

	protected Vereador extractAldermenInfo(final Elements elements) {

		final Vereador vereador = new Vereador();

		for (final Element element : elements) {

			final Elements elementsInfo = element.select("div.col-sm-7.texto");

			String politicianName = elementsInfo.select("h3").text().trim();
			vereador.setNome(politicianName);
			vereador.setEmail(getElementValue(elementsInfo, "E-mail").nextElementSibling().text().trim());
			vereador.setInfo(getElementValue(elementsInfo, "Dados Pessoais").nextElementSibling().text().trim());
			vereador.setLegislatura(getElementValue(elementsInfo, "Legislatura").nextSibling().toString().trim());
			vereador.setTelefone(getElementValue(elementsInfo, "Telefone").nextSibling().toString().trim());
			vereador.setPartidoPolitico(extractedPoliticalParty(elementsInfo));
			vereador.setLocalTrabalho(getElementValue(elementsInfo, "Local de Trabalho").nextSibling().toString().trim());
			vereador.setFoto(createPhoto(element.getElementsByClass("img-responsive").attr("src").trim(), politicianName));

		}

		return vereador;

	}

	protected String createPhoto(String url, String politicianName) {
		String path = vereadorImagePath + TemisStringUtils.unaccent(politicianName.toLowerCase().replaceAll(" ", "_")).concat(".jpg");
		fileUtil.saveFile(url, path);
		return urlContext.concat(path);
	}

	protected PartidoPolitico extractedPoliticalParty(final Elements elements) {
		
		Matcher matcher = RegexUtils.getMatcher("Partido: (\\w*) \\((.*)\\)", elements.select("h3").first().nextElementSibling().text().trim());
		
		String nome = "Partido Não Encontrado", sigla = "PNE";
		
		if(matcher.find()) {
			sigla = matcher.group(1);
			nome = matcher.group(2);
		}
		
		return partidos.comNome(nome).orElse(new PartidoPolitico(nome, sigla));
		
	}

	protected Element getElementValue(final Elements elementsInfo, final String key) {
		return elementsInfo.select("h4:contains(" + key + ")").first();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	protected void saveOrUpdate(final Vereador vereadorEncontrado) {

        final Optional<Vereador> vereador = vereadores.comName(vereadorEncontrado.getNome());

        if (vereador.isPresent()) {
        	
        	vereadorEncontrado.setId(vereador.get().getId());
        	vereadorEncontrado.setQuantidadeLeis(vereador.get().getQuantidadeLeis());
            vereadores.atualizar(vereadorEncontrado);

        } else {
            vereadores.salvar(vereadorEncontrado);
        }

    }
}
