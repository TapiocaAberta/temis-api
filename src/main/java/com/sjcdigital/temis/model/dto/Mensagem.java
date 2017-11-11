package com.sjcdigital.temis.model.dto;

public class Mensagem {
	
	@Deprecated
	public Mensagem() {}
	
	public Mensagem(final String mensgem) {
		this.mensagem = mensgem;
	}
	
	private String mensagem;

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
