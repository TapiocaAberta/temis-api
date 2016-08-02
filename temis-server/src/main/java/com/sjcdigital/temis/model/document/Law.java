package com.sjcdigital.temis.model.document;

import java.time.LocalDate;

public class Law {

	private String code;
	private String author;
	private String desc;
	private LocalDate date;
	private String title;
	private String number;

	public String getCode() {
		return code;
	}

	public void setCode(final String lawId) {
		this.code = lawId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(final String author) {
		this.author = author;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(final String description) {
		this.desc = description;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(final String projectLawNumber) {
		this.number = projectLawNumber;
	}

}
