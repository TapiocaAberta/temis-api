package com.sjcdigital.temis.model.service.bots.presenca.dto;

import java.util.Map;

public class PresencaVereadores {

	private String sessao;
	private String data;
	private String legislatura;
	private Map<String, String> presencas;
	
	public PresencaVereadores() {
	}


	public PresencaVereadores(String session, String date, String legislature, Map<String, String> attendance) {
		super();
		this.sessao = session;
		this.data = date;
		this.legislatura = legislature;
		this.presencas = attendance;
	}



	public String getSessao() {
		return sessao;
	}

	public void setSession(String session) {
		this.sessao = session;
	}

	public String getDate() {
		return data;
	}

	public void setDate(String date) {
		this.data = date;
	}

	public Map<String, String> getAttendance() {
		return presencas;
	}

	public void setAttendance(Map<String, String> attendance) {
		this.presencas = attendance;
	}

	public String getLegislatura() {
		return legislatura;
	}

	public void setLegislature(String legislature) {
		this.legislatura = legislature;
	}

	@Override
	public String toString() {
		return "AldermanAttendance [session=" + sessao + ", date=" + data + ", legislature=" + legislatura
				+ ", attendance=" + presencas + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((presencas == null) ? 0 : presencas.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((legislatura == null) ? 0 : legislatura.hashCode());
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
		PresencaVereadores other = (PresencaVereadores) obj;
		if (presencas == null) {
			if (other.presencas != null)
				return false;
		} else if (!presencas.equals(other.presencas))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (legislatura == null) {
			if (other.legislatura != null)
				return false;
		} else if (!legislatura.equals(other.legislatura))
			return false;
		if (sessao == null) {
			if (other.sessao != null)
				return false;
		} else if (!sessao.equals(other.sessao))
			return false;
		return true;
	}

}
