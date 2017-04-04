package com.sjcdigital.temis.model.entities.impl;

import javax.persistence.Entity;

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
	
	public Vereador getAutor() {
		return autor;
	}
	
	public void setAutor(Vereador autor) {
		this.autor = autor;
	}

}
