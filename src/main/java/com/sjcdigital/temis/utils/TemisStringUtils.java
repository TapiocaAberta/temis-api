package com.sjcdigital.temis.utils;

import java.text.Normalizer;

/**
 * @author pedro-hos
 *
 */
public class TemisStringUtils {

	/**
	 * Remove toda a acentuação da string substituindo por caracteres simples sem acento.
	 */
	public static String unaccent(final String src) {
		return Normalizer.normalize(src, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
}
