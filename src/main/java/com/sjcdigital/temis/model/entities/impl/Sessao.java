package com.sjcdigital.temis.model.entities.impl;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.sjcdigital.temis.model.entities.DefaultEntity;

@Entity
@Table(name = "sessao")
@NamedQueries(@NamedQuery(name = "Sessao.porNome", query = "select s from Sessao s where s.nome = :nome"))
public class Sessao extends DefaultEntity {

	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String nome;
	private Date data;
	private String dataTexto;
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

	public String getDataTexto() {
		return dataTexto;
	}

	public void setDataTexto(String dataTexto) {
		this.dataTexto = dataTexto;
	}

	public String resumo() {
		return legislatura.getNome() + " - " + nome + " - " + dataTexto;
	}

	@Override
	public String toString() {
		return "Sessao [nome=" + nome + ", data=" + data + ", dataTexto=" + dataTexto + ", legislatura=" + legislatura
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((dataTexto == null) ? 0 : dataTexto.hashCode());
		result = prime * result + ((legislatura == null) ? 0 : legislatura.hashCode());
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
		Sessao other = (Sessao) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (dataTexto == null) {
			if (other.dataTexto != null)
				return false;
		} else if (!dataTexto.equals(other.dataTexto))
			return false;
		if (legislatura == null) {
			if (other.legislatura != null)
				return false;
		} else if (!legislatura.equals(other.legislatura))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}