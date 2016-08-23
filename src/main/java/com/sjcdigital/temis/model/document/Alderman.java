package com.sjcdigital.temis.model.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Alderman {

	@Id
	private String id;

	@Indexed(unique = true)
	private String name;

	private String politicalParty;
	private String info;
	private String email;
	private String legislature;
	private String workplace;
	private String photo;
	private String phone;
	private Boolean notFound = false;

	public Alderman() {}

	public Alderman(final String name, final Boolean notFound) {
		this.name = name;
		this.notFound = notFound;
	}


	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPoliticalParty() {
		return politicalParty;
	}

	public void setPoliticalParty(final String politicalParty) {
		this.politicalParty = politicalParty;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(final String info) {
		this.info = info;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getLegislature() {
		return legislature;
	}

	public void setLegislature(final String legislature) {
		this.legislature = legislature;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(final String workplace) {
		this.workplace = workplace;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "{ " + "name: " + name + ", politicalParty: " + politicalParty + ", info:" + info + ", email: " + email
		        + ", legislature: " + legislature + ", workplace: " + workplace + ", phone: " + phone + ", photo: "
		        + photo + "}";

	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public Boolean getNotFound() {
		return notFound;
	}

	public void setNotFound(final Boolean notFound) {
		this.notFound = notFound;
	}

}
