/**
 * 
 */
package com.sjcdigital.temis.model.dto.chart;

/**
 * @author pesilva
 *
 */
public class DataChart {
	
	@Deprecated
	public DataChart() {}
	
	public DataChart(final String label, final long data) {
		this.label = label;
		this.valor = data;
	}
	
	private String label;
	private Long valor;
	
	public Long getValor() {
		return valor;
	}
	public void setValor(Long data) {
		this.valor = data;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

}
