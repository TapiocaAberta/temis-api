package com.sjcdigital.temis.model.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sjcdigital.temis.model.entities.DefaultEntity;

/**
 * @author pedro-hos
 *
 */
@Entity
@Table(name = "leis")
public class Lei extends DefaultEntity {

	private static final long serialVersionUID = 1L;

	@Column(length = 5000)
	private String ementa;
	private String numeroProcesso;
	private String numeroPropositura;
	private String situacao;
	
	@ManyToOne
	@JoinColumn(name = "tipo_id")
	private Tipo tipo;
	
	@ManyToOne
	@JoinColumn(name = "situacao_simplificada_id")
	private SituacaoSimplificada situacaoSimplificada;
	
	@Column(name = "query_string_criptografada")
	private String queryStringCriptografada;
	
	private Integer dcmId;
	private Integer dctId;

	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Autor autor;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Classe classe;
	
	@Column(length = 1000)
	private String pdfLei;

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

	public SituacaoSimplificada getSituacaoSimplificada() {
		return situacaoSimplificada;
	}

	public void setSituacaoSimplificada(SituacaoSimplificada situacaoSimplificada) {
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

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public String getPdfLei() {
		return pdfLei;
	}

	public void setPdfLei(String pdfLei) {
		this.pdfLei = pdfLei;
	}

}
