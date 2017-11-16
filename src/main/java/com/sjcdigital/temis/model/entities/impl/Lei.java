package com.sjcdigital.temis.model.entities.impl;

import java.math.BigInteger;
import java.util.Objects;

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
	
	private static final String MAX_RATING = "5";
	private static final String CEM_PORCENTO = "100";

	@Column(length = 5000)
	private String ementa;
	
	@Column(name="numero_processo")
	private String numeroProcesso;
	
	@Column(name="numero_propositura")
	private String numeroPropositura;
	
	private String situacao;
	
	@Column(length = 4, nullable = false)
	private Integer ano;
	
	@Column(name="rating_total")
	private BigInteger ratingTotal = BigInteger.ZERO;
	
	@Column(name="quantidade_de_votos")
	private BigInteger quantidadeDeVotos = BigInteger.ZERO;
	
	private BigInteger relevancia = BigInteger.ZERO;
	
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
	
	public void calculaRelevancia() {
		this.relevancia = this.ratingTotal.multiply(new BigInteger(CEM_PORCENTO))
									 .divide(this.quantidadeDeVotos.multiply(new BigInteger(MAX_RATING)));
	}

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

	public BigInteger getRelevancia() {
		
		if(Objects.isNull(this.relevancia)) {
			return BigInteger.ZERO;
		}
		
		return relevancia;
	}

	public void setRelevancia(BigInteger relevancia) {
		this.relevancia = relevancia;
	}

	public BigInteger getQuantidadeDeVotos() {
		
		if(Objects.isNull(this.quantidadeDeVotos)) {
			return BigInteger.ZERO;
		}
		
		return quantidadeDeVotos;
	}

	public void setQuantidadeDeVotos(BigInteger quantidadeDeVotos) {
		this.quantidadeDeVotos = quantidadeDeVotos;
	}

	public BigInteger getRatingTotal() {
		
		if(Objects.isNull(this.ratingTotal)) {
			return BigInteger.ZERO;
		}
		
		return ratingTotal;
	}

	public void setRatingTotal(BigInteger rating) {
		this.ratingTotal = rating;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

}
