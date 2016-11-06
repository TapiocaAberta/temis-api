package com.sjcdigital.temis.model.document;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
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
	private BigInteger lawsCount = BigInteger.ZERO;
	
	public Alderman() {}
	
	public Alderman(final String name, final Boolean notFound, final String photo) {
		this.photo = photo;
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
	
	public void plusLaw() {
		this.setLawsCount(getLawsCount().add(BigInteger.ONE));
	}
	
	public BigInteger getLawsCount() {
		return lawsCount;
	}
	
	public void setLawsCount(BigInteger lawsCount) {
		this.lawsCount = lawsCount;
	}
	
	public static String normalizeName(final String name) {
		String newName = name.trim();
		newName = newName.toLowerCase();
		newName = StringUtils.normalizeSpace(newName);
		newName = WordUtils.capitalize(newName);
		newName = normalizeNameCharacters(newName);
		
		return newName;
	}
	
	private static String normalizeNameCharacters(final String name) {
		final Map<String, String> specialChars = new HashMap<>();
		specialChars.put("ª", "a");
		specialChars.put("-", "");
		
		String newName = name;
		for (Map.Entry<String, String> e : specialChars.entrySet()) {
			newName = newName.replace(e.getKey(), e.getValue());
		}
		
		return newName;
	}
	
	@Override
	public String toString() {
		return "{ " + "name: " + name + ", politicalParty: " + politicalParty + ", info:" + info + ", email: " + email
		        + ", legislature: " + legislature + ", workplace: " + workplace + ", phone: " + phone + ", photo: "
		        + photo + "}";
				
	}
	
}
