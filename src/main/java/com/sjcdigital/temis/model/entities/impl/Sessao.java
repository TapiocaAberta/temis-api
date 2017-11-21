package com.sjcdigital.temis.model.entities.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sjcdigital.temis.model.entities.DefaultEntity;

@Entity
@Table(name = "sessao")
public class Sessao extends DefaultEntity {

	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String nome;
	private Date data;
	@ManyToOne
	@JoinColumn(name = "legislatura_id")
	private Legislatura legislatura;

	public Sessao() {
		super();
	}

	public Sessao(String nome, Date data, Legislatura legislatura) {
		super();
		this.nome = nome;
		this.data = data;
		this.legislatura = legislatura;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Legislatura getLegislatura() {
		return legislatura;
	}

	public void setLegislatura(Legislatura legislatura) {
		this.legislatura = legislatura;
	}

}