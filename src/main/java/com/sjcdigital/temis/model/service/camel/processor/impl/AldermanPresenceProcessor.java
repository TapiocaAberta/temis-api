package com.sjcdigital.temis.model.service.camel.processor.impl;

import com.sjcdigital.temis.model.service.camel.processor.AbstractProcessor;
import com.sjcdigital.temis.model.service.parsers.AbstractParser;
import com.sjcdigital.temis.model.service.parsers.impl.PresenceAldermanParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fabiohbarbosa
 */
@Component
public class PresenceAldermanProcessor extends AbstractProcessor {

    @Autowired
    private PresenceAldermanParser parser;

    @Override
    public AbstractParser getParser() {
        return parser;
    }
}
