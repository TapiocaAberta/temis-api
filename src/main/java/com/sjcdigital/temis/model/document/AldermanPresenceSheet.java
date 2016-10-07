package com.sjcdigital.temis.model.document;

/**
 * @author fabiohbarbosa
 */
public enum AldermanPresenceSheet {
    FIRST_ROW(14), LAST_ROW(35),
    SESSION_ROW(10), SESSION_COLUMN(0),
    NAME_COLUMN(0), PRESENT_COLUMN(1);

    public int NUM;

    AldermanPresenceSheet(int NUM) {
        this.NUM = NUM;
    }
}