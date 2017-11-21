package com.sjcdigital.temis.model.entities.impl;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sjcdigital.temis.model.entities.DefaultEntity;

@Entity
@Table(name = "presenca")
public class Presenca extends DefaultEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Autor autor;
	@ManyToOne
	@JoinColumn(name = "sessao_id")
	private Sessao sessao;
	private boolean presenca;

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public boolean isPresenca() {
		return presenca;
	}

	public void setPresenca(boolean presenca) {
		this.presenca = presenca;
	}

}
