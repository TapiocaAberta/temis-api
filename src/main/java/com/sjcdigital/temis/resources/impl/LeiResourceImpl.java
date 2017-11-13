package com.sjcdigital.temis.resources.impl;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sjcdigital.temis.model.dto.Mensagem;
import com.sjcdigital.temis.model.entities.impl.Lei;
import com.sjcdigital.temis.model.repositories.impl.Leis;
import com.sjcdigital.temis.model.repositories.impl.Votos;
import com.sjcdigital.temis.model.service.chart.LeisChartService;
import com.sjcdigital.temis.resources.LeiResource;
import com.sjcdigital.temis.utils.RESTUtils;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class LeiResourceImpl implements LeiResource {
	
	@Inject
	private Leis leis;
	
	@Inject
	private Votos votos;
	
	@Inject
	private LeisChartService leisChartService;

	@Override
	public Response buscaTodosPaginados(int total, int pg) {
		List<Lei> leisPaginados = RESTUtils.lanca404SeNulo(leis.todosPaginado(total, pg));
		return Response.ok().entity(leisPaginados).build();
	}

	@Override
	public Response buscaPorSituacaoSimplificada(Long id) {
		List<Lei> leisPaginados = RESTUtils.lanca404SeNulo(leis.comSituacaoSimplificada(id));
		return Response.ok().entity(leisPaginados).build();
	}

	@Override
	public Response buscaPorTipo(Long id, int total, int pg) {
		List<Lei> leisPaginados = RESTUtils.lanca404SeNulo(leis.comTipo(id, total, pg));
		return Response.ok().entity(leisPaginados).build();
	}

	@Override
	public Response buscaPorClasse(Long id) {
		List<Lei> leisPaginados = RESTUtils.lanca404SeNulo(leis.comClasse(id));
		return Response.ok().entity(leisPaginados).build();
	}

	@Override
	public Response buscaPorId(Long id) {
		Lei lei = RESTUtils.lanca404SeNulo(leis.buscarPorId(id));
		return Response.ok().entity(lei).build();
	}

	@Override
	public Response votar(Long id, Integer rating, HttpServletRequest request) {
		
		if(rating > 5) {
			return Response.status(Status.FORBIDDEN).entity(new Mensagem("Rating máximo é 5")).build();
		}
		
		Lei lei = RESTUtils.lanca404SeNulo(leis.buscarPorId(id));
		
		if(votos.podeVotar(request.getRemoteAddr(), lei)) {
			
			lei.setRatingTotal(lei.getRatingTotal().add(new BigInteger(rating.toString())));
			lei.setQuantidadeDeVotos(lei.getQuantidadeDeVotos().add(BigInteger.ONE));
			lei.calculaRelevancia();
			
			leis.atualizar(lei);
			
			return Response.ok().entity(lei).build();
			
		} else {
			
			return Response.status(Status.FORBIDDEN).entity(new Mensagem("Você já votou nesta lei")).build();
		}
	}

	@Override
	public Response graficos() {
		return Response.ok(leisChartService.montaLeisChart()).build();
	}

	@Override
	public Response filtraPaginado(Long idSituacao, Long idClasse, Long idTipo, Integer ano, int total, int pg) {
		List<Lei> leisFiltrada = RESTUtils.lanca404SeNulo(leis.filtraPaginado(idSituacao, idClasse, idTipo, ano, total, pg));
		return Response.ok().entity(leisFiltrada).build(); 
	}

}
