package com.sjcdigital.temis.model.dto;

import java.io.Serializable;

public class Ano implements Serializable {

	private static final long serialVersionUID = 1L;

	@Deprecated
	public Ano() {}

	public Ano(final Integer ano) {
		this.ano = ano;
	}

	private Integer ano;

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
}
