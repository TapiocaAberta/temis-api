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

import com.sjcdigital.temis.model.service.parsers.impl.AldermenParser;

/**
 * @author pedro-hos
 *
 */
@Component
public class AldermanProcessor implements Processor {
	
	@Autowired
	private AldermenParser parser;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		final Message in = exchange.getIn();
		File file = in.getMandatoryBody(File.class);
		parser.parse(file);
	}
	
}
