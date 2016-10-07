package com.sjcdigital.temis.model.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * @author fabiohbarbosa
 */
@Document
public class OrdinarySession {
    @Id
    private String id;

    private Integer session;
    private LocalDate date;

    @DBRef
    private Alderman alderman;
    private Boolean isPresent;
    private AldermanSurrogate surrogate;

    public OrdinarySession() {
    }

    public OrdinarySession(final Integer session, final LocalDate date, final Alderman alderman, final Boolean isPresent, final AldermanSurrogate surrogate) {
        this.session = session;
        this.date = date;
        this.alderman = alderman;
        this.isPresent = isPresent;
        this.surrogate = surrogate;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(final Integer session) {
        this.session = session;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public Alderman getAlderman() {
        return alderman;
    }

    public void setAlderman(final Alderman alderman) {
        this.alderman = alderman;
    }

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(final Boolean present) {
        isPresent = present;
    }

    public AldermanSurrogate getSurrogate() {
        return surrogate;
    }

    public void setSurrogate(final AldermanSurrogate surrogate) {
        this.surrogate = surrogate;
    }
}
