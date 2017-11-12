package com.sjcdigital.temis.model.service.chart;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.sjcdigital.temis.model.dto.chart.AutorChart;

/**
 * @author pesilva
 *
 */

@Stateless
public class AutorChartService {
	
	@Inject
	private LeisChartService leisChartService;
	
	// futuramente terão outros dados em autor, como grafico de presença etc...
	
	public AutorChart montaAutorChart(final Long id) {
		 return new AutorChart(leisChartService.montaChartPorAutor(id));
	}
}
