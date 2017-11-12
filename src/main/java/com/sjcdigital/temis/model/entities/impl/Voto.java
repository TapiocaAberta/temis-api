package com.sjcdigital.temis.model.entities.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sjcdigital.temis.model.entities.DefaultEntity;

@Entity
@Table(name = "voto")
public class Voto extends DefaultEntity {

	private static final long serialVersionUID = 1L;
	
	public Voto() {}
	
	public Voto(final String ip, final Lei lei) {
		this.ip = ip;	
		this.lei = lei;
	}
	
	@Column(nullable = false)
	private String ip;
	
	@ManyToOne
	@JoinColumn(name = "lei_id", nullable = false)
	private Lei lei;
	
	

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Lei getLei() {
		return lei;
	}

	public void setLei(Lei lei) {
		this.lei = lei;
	}

}
