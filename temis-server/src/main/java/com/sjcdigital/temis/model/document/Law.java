package com.sjcdigital.temis.model.document;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

public class Law {

	@Id
	private String id;

	@Indexed(unique = true)
	private String code;

	private Collection<Alderman> author;

	private String desc;
	private LocalDate date;
	private String title;

	@Field(value = "PLNumber")
	private String projectLawNumber;

	public String getCode() {
		return code;
	}

	public void setCode(final String lawId) {
		code = lawId;
	}

	public Collection<Alderman> getAuthor() {
		return author;
	}

	public void setAuthor(final Collection<Alderman> author) {
		this.author = author;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(final String description) {
		desc = description;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getProjectLawNumber() {
		return projectLawNumber;
	}

	public void setProjectLawNumber(final String projectLawNumber) {
		this.projectLawNumber = projectLawNumber;
	}

	@Override
	public String toString() {
		return "{ code: " + code + ", " + "author: " + author + ", " + "desc: " + desc + ", " + "date: " + date + ", "
		        + "title: " + title + ", " + "number: " + projectLawNumber + " }";
	}

}
