package com.sjcdigital.temis.model.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sjcdigital.temis.model.entities.DefaultEntity;

@Entity
@Table(name = "legislatura")
public class Legislatura extends DefaultEntity {

	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String nome;

	public Legislatura() {
		super();
	}

	public Legislatura(String nome) {
		super();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
