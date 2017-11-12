package com.sjcdigital.temis.model.entities.impl;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sjcdigital.temis.model.entities.DefaultEntity;

@Entity
@Table(name = "usuario")
public class Usuario extends DefaultEntity {

	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	@Column(unique = true)
	private String email;
	
	@OneToMany
	@JoinColumn(name = "autor_seguido_id")
	private List<Autor> autoresSeguido;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
