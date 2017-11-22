package com.sjcdigital.temis.model.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.sjcdigital.temis.model.entities.DefaultEntity;

@Entity
@Table(name = "legislatura")
@NamedQueries(@NamedQuery(name = "Legislatura.porNome", query = "select l from Legislatura l where l.nome = :nome"))
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

	@Override
	public String toString() {
		return "Legislatura [nome=" + nome + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Legislatura other = (Legislatura) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
