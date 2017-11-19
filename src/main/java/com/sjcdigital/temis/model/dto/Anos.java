package com.sjcdigital.temis.model.dto;

import java.io.Serializable;
import java.util.List;

public class Anos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Deprecated
	public Anos() {}

	public Anos(final List<Ano> anos) {
		this.anos = anos;
	}

	private List<Ano> anos;

	public List<Ano> getAnos() {
		return anos;
	}

	public void setAnos(List<Ano> anos) {
		this.anos = anos;
	}

}
