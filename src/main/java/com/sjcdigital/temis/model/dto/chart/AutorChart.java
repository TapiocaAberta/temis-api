/**
 * 
 */
package com.sjcdigital.temis.model.dto.chart;

/**
 * @author pesilva
 *
 */
public class AutorChart {
	
	@Deprecated
	public AutorChart() {}
	
	public AutorChart(final LeisChart leisChart) {
		this.leisChart = leisChart;
	}
	
	private LeisChart leisChart;

	public LeisChart getLeisChart() {
		return leisChart;
	}

	public void setLeisChart(LeisChart leisChart) {
		this.leisChart = leisChart;
	}

}
