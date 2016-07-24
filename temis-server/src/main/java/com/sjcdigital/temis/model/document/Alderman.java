package com.sjcdigital.temis.model.document;

public class Alderman {
	
	private String name;
	private String politicalParty;
	private String info;
	private String email;
	private String legislature;
	private String workplace;
	private String photo;
	private String phone;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPoliticalParty() {
		return politicalParty;
	}
	
	public void setPoliticalParty(String politicalParty) {
		this.politicalParty = politicalParty;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLegislature() {
		return legislature;
	}
	
	public void setLegislature(String legislature) {
		this.legislature = legislature;
	}
	
	public String getWorkplace() {
		return workplace;
	}
	
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@Override
	public String toString() {
		return "{ " + "name: " + name + ", politicalParty: " + politicalParty + ", info:" + info + ", email: " + email
		        + ", legislature: " + legislature + ", workplace: " + workplace + ", phone: " + phone + ", photo: "
		        + photo + "}";
				
	}
	
}
