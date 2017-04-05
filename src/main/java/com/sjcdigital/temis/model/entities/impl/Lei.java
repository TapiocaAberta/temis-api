package com.sjcdigital.temis.model.entities.impl;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.sjcdigital.temis.model.entities.DefaultEntity;

/**
 * @author pedro-hos
 *
 */
@Entity
public class Lei extends DefaultEntity {

	private static final long serialVersionUID = 1L;

	private String ementa;
	private String numeroProcesso;
	private String numeroPropositura;
	private String situacao;
	private Tipo tipo;
	private String situacaoSimplificada;
	private String queryStringCriptografada;
	private Integer dcmId;
	private Integer dctId;

	@ManyToOne
	private Vereador autor;

	public String getEmenta() {
		return ementa;
	}

	public void setEmenta(String ementa) {
		this.ementa = ementa;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public String getNumeroPropositura() {
		return numeroPropositura;
	}

	public void setNumeroPropositura(String numeroPropositura) {
		this.numeroPropositura = numeroPropositura;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getSituacaoSimplificada() {
		return situacaoSimplificada;
	}

	public void setSituacaoSimplificada(String situacaoSimplificada) {
		this.situacaoSimplificada = situacaoSimplificada;
	}

	public String getQueryStringCriptografada() {
		return queryStringCriptografada;
	}

	public void setQueryStringCriptografada(String queryStringCriptografada) {
		this.queryStringCriptografada = queryStringCriptografada;
	}

	public Integer getDcmId() {
		return dcmId;
	}

	public void setDcmId(Integer dcmId) {
		this.dcmId = dcmId;
	}

	public Integer getDctId() {
		return dctId;
	}

	public void setDctId(Integer dctId) {
		this.dctId = dctId;
	}

	public Vereador getAutor() {
		return autor;
	}

	public void setAutor(Vereador autor) {
		this.autor = autor;
	}

}
