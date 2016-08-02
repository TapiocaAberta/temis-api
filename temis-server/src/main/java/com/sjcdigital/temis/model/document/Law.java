package com.sjcdigital.temis.model.document;

import java.time.LocalDate;

public class Law {

	private String lawCode;
	private String author;
	private String description;
	private LocalDate date;
	private String title;
	private String projectLawNumber;

	public String getLawCode() {
		return lawCode;
	}

	public void setLawCode(final String lawId) {
		this.lawCode = lawId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(final String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
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

}
