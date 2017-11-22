package com.sjcdigital.temis.model.service.bots.presenca.parser;

import java.io.InputStream;

import com.sjcdigital.temis.model.service.bots.presenca.dto.PresencaVereadores;

/**
 * 
 * Parse session presence files coming from the city council website
 * 
 * @author william
 *
 */
public interface AttendanceParser {


	/**
	 * Receives an input stream and return the alderman absence/presence on the given
	 * stream session.
	 * 
	 * @param is
	 * The input stream with the data to be parsed
	 * @return
	 */
	PresencaVereadores parse(InputStream is);

}