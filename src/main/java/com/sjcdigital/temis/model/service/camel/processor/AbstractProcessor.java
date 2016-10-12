package com.sjcdigital.temis.model.service.camel.processor;

import com.sjcdigital.temis.model.service.parsers.AbstractParser;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.File;

/**
 * @author fabiohbarbosa
 */
public abstract class AbstractProcessor implements Processor {

    @Override
    public void process(final Exchange exchange) throws Exception {
        final File file = exchange.getIn().getMandatoryBody(File.class);
        getParser().parse(file);
    }

    public abstract AbstractParser getParser();

}
