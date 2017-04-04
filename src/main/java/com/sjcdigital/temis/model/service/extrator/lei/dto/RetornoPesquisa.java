package com.sjcdigital.temis.model.service.extrator.lei.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java de RetornoPesquisa complex type.
 * 
 * <p>
 * O seguinte fragmento do esquema especifica o conteúdo esperado contido dentro
 * desta classe.
 * 
 * <pre>
 * &lt;complexType name="RetornoPesquisa">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="verId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dcmId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dctId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ementa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroProcesso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroPropositura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="autor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="situacao" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="situacaoSimplificada" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalDocumentosEncontrados" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pagId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="queryStringCriptografada" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetornoPesquisa", propOrder = { "verId", "dcmId", "dctId", "ementa", "numeroProcesso",
		"numeroPropositura", "autor", "situacao", "tipo", "situacaoSimplificada", "totalDocumentosEncontrados", "pagId",
		"queryStringCriptografada" })
public class RetornoPesquisa {

	protected int verId;
	protected int dcmId;
	protected int dctId;
	protected String ementa;
	protected String numeroProcesso;
	protected String numeroPropositura;
	protected String autor;
	protected String situacao;
	protected String tipo;
	protected String situacaoSimplificada;
	protected int totalDocumentosEncontrados;
	protected int pagId;
	protected String queryStringCriptografada;

	/**
	 * Obtém o valor da propriedade verId.
	 * 
	 */
	public int getVerId() {
		return verId;
	}

	/**
	 * Define o valor da propriedade verId.
	 * 
	 */
	public void setVerId(int value) {
		this.verId = value;
	}

	/**
	 * Obtém o valor da propriedade dcmId.
	 * 
	 */
	public int getDcmId() {
		return dcmId;
	}

	/**
	 * Define o valor da propriedade dcmId.
	 * 
	 */
	public void setDcmId(int value) {
		this.dcmId = value;
	}

	/**
	 * Obtém o valor da propriedade dctId.
	 * 
	 */
	public int getDctId() {
		return dctId;
	}

	/**
	 * Define o valor da propriedade dctId.
	 * 
	 */
	public void setDctId(int value) {
		this.dctId = value;
	}

	/**
	 * Obtém o valor da propriedade ementa.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEmenta() {
		return ementa;
	}

	/**
	 * Define o valor da propriedade ementa.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setEmenta(String value) {
		this.ementa = value;
	}

	/**
	 * Obtém o valor da propriedade numeroProcesso.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	/**
	 * Define o valor da propriedade numeroProcesso.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNumeroProcesso(String value) {
		this.numeroProcesso = value;
	}

	/**
	 * Obtém o valor da propriedade numeroPropositura.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNumeroPropositura() {
		return numeroPropositura;
	}

	/**
	 * Define o valor da propriedade numeroPropositura.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNumeroPropositura(String value) {
		this.numeroPropositura = value;
	}

	/**
	 * Obtém o valor da propriedade autor.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAutor() {
		return autor;
	}

	/**
	 * Define o valor da propriedade autor.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setAutor(String value) {
		this.autor = value;
	}

	/**
	 * Obtém o valor da propriedade situacao.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSituacao() {
		return situacao;
	}

	/**
	 * Define o valor da propriedade situacao.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSituacao(String value) {
		this.situacao = value;
	}

	/**
	 * Obtém o valor da propriedade tipo.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Define o valor da propriedade tipo.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTipo(String value) {
		this.tipo = value;
	}

	/**
	 * Obtém o valor da propriedade situacaoSimplificada.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSituacaoSimplificada() {
		return situacaoSimplificada;
	}

	/**
	 * Define o valor da propriedade situacaoSimplificada.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setSituacaoSimplificada(String value) {
		this.situacaoSimplificada = value;
	}

	/**
	 * Obtém o valor da propriedade totalDocumentosEncontrados.
	 * 
	 */
	public int getTotalDocumentosEncontrados() {
		return totalDocumentosEncontrados;
	}

	/**
	 * Define o valor da propriedade totalDocumentosEncontrados.
	 * 
	 */
	public void setTotalDocumentosEncontrados(int value) {
		this.totalDocumentosEncontrados = value;
	}

	/**
	 * Obtém o valor da propriedade pagId.
	 * 
	 */
	public int getPagId() {
		return pagId;
	}

	/**
	 * Define o valor da propriedade pagId.
	 * 
	 */
	public void setPagId(int value) {
		this.pagId = value;
	}

	/**
	 * Obtém o valor da propriedade queryStringCriptografada.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getQueryStringCriptografada() {
		return queryStringCriptografada;
	}

	/**
	 * Define o valor da propriedade queryStringCriptografada.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setQueryStringCriptografada(String value) {
		this.queryStringCriptografada = value;
	}

}
