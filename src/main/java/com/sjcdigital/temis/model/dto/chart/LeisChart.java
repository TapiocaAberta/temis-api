/**
 * 
 */
package com.sjcdigital.temis.model.dto.chart;

import java.util.List;

/**
 * @author pesilva
 *
 */
public class LeisChart {
	
	@Deprecated
	public LeisChart() {}
	
	public LeisChart(final List<DataChart> tipo, final List<DataChart> situacao, final List<DataChart> classe) {
		this.tipo = tipo;
		this.situacao = situacao;
		this.classe = classe;
	}
	
	private List<DataChart> tipo;
	private List<DataChart> situacao;
	private List<DataChart> classe;
	
	public List<DataChart> getTipo() {
		return tipo;
	}
	public void setTipo(List<DataChart> tipo) {
		this.tipo = tipo;
	}
	public List<DataChart> getSituacao() {
		return situacao;
	}
	public void setSituacao(List<DataChart> situacao) {
		this.situacao = situacao;
	}
	public List<DataChart> getClasse() {
		return classe;
	}
	public void setClasse(List<DataChart> classe) {
		this.classe = classe;
	}

}
