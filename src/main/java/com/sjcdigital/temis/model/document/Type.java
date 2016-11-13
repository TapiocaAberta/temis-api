/**
 * 
 */
package com.sjcdigital.temis.model.document;

/**
 * @author pedro-hos
 *
 */
public enum Type {
	
	NOMEACAO("Nomeação"), 
	UTILIDADE_PUBLICA("Declaração de Utilidade Pública"), 
	SAUDE("Saúde"), 
	ACESSIBILIDADE("Acessibilidade"), 
	DATA_COMEMORATIVA("Data Comemorativa"), 
	CAUSAS_ANIMAIS("Causas Animais"), 
	REGISTRO_CANCELADO("Registro Cancelado"), 
	OUTRO("Outro"), 
	SEM_CLASSIFICACAO("Sem Classificação");
	
	private String type;

	Type(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}

}
