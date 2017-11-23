package com.sjcdigital.temis.resources.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import com.sjcdigital.temis.model.entities.impl.Autor;
import com.sjcdigital.temis.model.entities.impl.Lei;
import com.sjcdigital.temis.model.repositories.impl.Autores;
import com.sjcdigital.temis.model.repositories.impl.Leis;
import com.sjcdigital.temis.model.service.chart.AutorChartService;
import com.sjcdigital.temis.resources.AutorResource;
import com.sjcdigital.temis.utils.JaxrsUtils;

/**
 * 
 * @author pedro-hos
 *
 */
@Stateless
public class AutorResourceImpl implements AutorResource {
	
	@Inject
	private Autores autores;
	
	@Inject
	private Leis leis;
	
	@Inject
	private AutorChartService autorChart;

	@Override
	public Response buscaTodosPaginados(int total, int pg) {
		return Response.ok().header(JaxrsUtils.HEADER_TOTAL_ITENS, autores.contaTodos())
							.entity(JaxrsUtils.lanca404SeNulo(autores.todosPaginado(total, pg)))
							.build();
	}

	@Override
	public Response buscaPorNome(String nome) {
		Autor autor = JaxrsUtils.lanca404SeNulo(autores.comName(nome));
		return Response.ok().entity(autor).build();
	}

	@Override
	public Response buscaId(Long id) {
		Autor autor = JaxrsUtils.lanca404SeNulo(autores.buscarPorId(id));
		return Response.ok().entity(autor).build();
	}

	@Override
	public Response buscaLeisPorAutor(Long id, int total, int pg) {
		List<Lei> leisDoAutor = JaxrsUtils.lanca404SeNulo(leis.doAutor(id, total, pg));
		return Response.ok().entity(leisDoAutor).build();
	}

	@Override
	public Response montaGrafico(Long id) {
		return Response.ok(autorChart.montaAutorChart(id)).build();
	}

}
