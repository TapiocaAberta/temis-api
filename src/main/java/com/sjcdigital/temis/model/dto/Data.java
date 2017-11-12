package com.sjcdigital.temis.model.dto;

public class Data {
	
	private String tag;
	private String tipo;
	private String text;

	@Deprecated
	public Data() { }
	
	public Data(final String text, final String tipo) {
		this.text = text;
		this.tipo  = tipo; 
		this.tag  = "SEM_CLASSIFICACAO";
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}