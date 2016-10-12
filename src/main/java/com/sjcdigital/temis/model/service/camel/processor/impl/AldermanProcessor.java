/**
 *
 */
package com.sjcdigital.temis.model.service.camel.processor.impl;

import com.sjcdigital.temis.model.service.camel.processor.AbstractProcessor;
import com.sjcdigital.temis.model.service.parsers.AbstractParser;
import com.sjcdigital.temis.model.service.parsers.impl.AldermenParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author pedro-hos
 *
 */
@Component
public class AldermanProcessor extends AbstractProcessor {

	@Autowired
	private AldermenParser parser;


	@Override
	public AbstractParser getParser() {
		return parser;
	}
}
