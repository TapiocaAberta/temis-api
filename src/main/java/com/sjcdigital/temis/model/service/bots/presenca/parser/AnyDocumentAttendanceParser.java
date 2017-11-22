package com.sjcdigital.temis.model.service.bots.presenca.parser;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import com.sjcdigital.temis.model.service.bots.presenca.dto.PresencaVereadores;

/**
 * Parser for any document using ApacheTika
 * @author wsiqueir
 *
 */
public class AnyDocumentAttendanceParser implements AttendanceParser {

	
	@Override
	public PresencaVereadores parse(InputStream is) {
		try {
			Tika tika = new Tika();
			String content = tika.parseToString(is);
			PresencaVereadores aldermanAttendance = ParserUtils.fromText(content);
			return aldermanAttendance;
		} catch (IOException | TikaException e) {
			e.printStackTrace();
			return null;
		}
	}

}