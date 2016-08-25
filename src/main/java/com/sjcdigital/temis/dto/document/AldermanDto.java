package com.sjcdigital.temis.dto.document;

import org.springframework.hateoas.ResourceSupport;

public class AldermanDto extends ResourceSupport {

	private String aldermanId;
	private String name;
	private String politicalParty;
	private String info;
	private String email;
	private String legislature;
	private String workplace;
	private String photo;
	private String phone;
	private Boolean notFound = false;

	
	public String getAldermanId() {
		return aldermanId;
	}
	public void setAldermanId(String aldermanId) {
		this.aldermanId = aldermanId;
	}
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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean getNotFound() {
		return notFound;
	}
	public void setNotFound(Boolean notFound) {
		this.notFound = notFound;
	}
	
}
