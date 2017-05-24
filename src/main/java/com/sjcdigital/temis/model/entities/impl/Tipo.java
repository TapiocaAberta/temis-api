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
@Table(name = "tipo")
public class Tipo extends DefaultEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Column(unique = true)
	private String nome;
	
	private String descricao;
	
	public Tipo() { }
	
	public Tipo(String nome) {
		this.nome = nome;
		this.descricao = "";
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String tipo) {
		this.nome = tipo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
