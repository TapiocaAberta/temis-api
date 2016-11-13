package com.sjcdigital.temis.model.document;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document
public class Law {

	@Id
	private String id;

	@Indexed(unique = true)
	private String code;

	@DBRef
	private Collection<Alderman> author;

	@TextIndexed
	private String desc;
	
	private String summary;
	
	private String type;
	
	@Indexed
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate date;
	
	private String title;

	@Field(value = "PLNumber")
	private String projectLawNumber;
	
	private BigInteger votesCount = BigInteger.ZERO;
	private BigInteger rating = BigInteger.ZERO;
	
	public void addVote() {
		this.votesCount = this.votesCount.add(BigInteger.ONE);
	}
	
	public void addRating(BigInteger rating) {
		this.rating = this.rating.add(rating).divide(votesCount);
	}

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

	public String getSummary() {
		return summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public BigInteger getVotesCount() {
		return votesCount;
	}

	public void setVotesCount(final BigInteger votesTotal) {
		this.votesCount = votesTotal;
	}

	public BigInteger getRating() {
		return rating;
	}

	public void setRating(final BigInteger rating) {
		this.rating = rating;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
