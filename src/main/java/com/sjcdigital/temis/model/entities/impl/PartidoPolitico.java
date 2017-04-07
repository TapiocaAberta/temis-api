package com.sjcdigital.temis.model.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sjcdigital.temis.model.entities.DefaultEntity;

/**
 * @author pedro-hos
 *
 */
@Entity
@Table(name = "partido_politico")
public class PartidoPolitico extends DefaultEntity {

	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String nome;
	
	@Column(unique = true)
	private String sigla;

	public PartidoPolitico() { }

	public PartidoPolitico(final String nome, final String sigla) {
		this.nome = nome;
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

}
