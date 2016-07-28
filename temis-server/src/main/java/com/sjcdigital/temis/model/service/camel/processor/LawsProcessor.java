/**
 *
 */
package com.sjcdigital.temis.model.service.camel.processor;

import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.model.service.parsers.impl.LawsParser;

/**
 * @author pedro-hos
 */

@Component
public class LawsProcessor implements Processor {

	@Autowired
	private LawsParser lawsParser;
	
	@Override
	public void process(final Exchange exchange) throws Exception {
		final Message in = exchange.getIn();
		final File file = in.getMandatoryBody(File.class);
		lawsParser.parse(file);
	}
	
}
