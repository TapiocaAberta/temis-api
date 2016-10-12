package com.sjcdigital.temis.model.service.camel.processor.impl;

import com.sjcdigital.temis.model.service.camel.processor.AbstractProcessor;
import com.sjcdigital.temis.model.service.parsers.AbstractParser;
import com.sjcdigital.temis.model.service.parsers.impl.AldermanPresenceParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fabiohbarbosa
 */
@Component
public class AldermanPresenceProcessor extends AbstractProcessor {

    @Autowired
    private AldermanPresenceParser parser;

    @Override
    public AbstractParser getParser() {
        return parser;
    }
}
