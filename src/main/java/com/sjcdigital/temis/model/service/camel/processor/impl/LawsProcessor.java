/**
 *
 */
package com.sjcdigital.temis.model.service.camel.processor.impl;

import com.sjcdigital.temis.model.service.camel.processor.AbstractProcessor;
import com.sjcdigital.temis.model.service.parsers.AbstractParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sjcdigital.temis.model.service.parsers.impl.LawsParser;

/**
 * @author pedro-hos
 */

@Component
public class LawsProcessor extends AbstractProcessor {

	@Autowired
	private LawsParser parser;


	@Override
	public AbstractParser getParser() {
		return parser;
	}
}
