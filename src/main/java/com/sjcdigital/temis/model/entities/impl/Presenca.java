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

	public Presenca() {
		super();
	}

	public Presenca(Autor autor, Sessao sessao, boolean presenca) {
		super();
		this.autor = autor;
		this.sessao = sessao;
		this.presenca = presenca;
	}

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

	@Override
	public String toString() {
		return "Presenca [autor=" + autor + ", sessao=" + sessao + ", presenca=" + presenca + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result + (presenca ? 1231 : 1237);
		result = prime * result + ((sessao == null) ? 0 : sessao.hashCode());
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
		Presenca other = (Presenca) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (presenca != other.presenca)
			return false;
		if (sessao == null) {
			if (other.sessao != null)
				return false;
		} else if (!sessao.equals(other.sessao))
			return false;
		return true;
	}
	
}
