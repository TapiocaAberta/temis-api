package com.sjcdigital.temis.model.document;

/**
 * @author fabiohbarbosa
 */
public class AldermanSurrogate {
    private String justification;
    private String surrogate;
    private boolean surrogatePresent;

    public AldermanSurrogate() {
    }

    public AldermanSurrogate(final String justification, final String surrogate, final boolean surrogatePresent) {
        this.justification = justification;
        this.surrogate = surrogate;
        this.surrogatePresent = surrogatePresent;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(final String justification) {
        this.justification = justification;
    }

    public String getSurrogate() {
        return surrogate;
    }

    public void setSurrogate(final String surrogate) {
        this.surrogate = surrogate;
    }

    public boolean isSurrogatePresent() {
        return surrogatePresent;
    }

    public void setSurrogatePresent(final boolean surrogatePresent) {
        this.surrogatePresent = surrogatePresent;
    }
}
