/**
 *
 */
package com.sjcdigital.temis.util;

import java.text.Normalizer;

/**
 * StringUtils
 *
 */
public class StringUtil { 

	/**
	 * Remove toda a acentuação da string substituindo por caracteres simples sem acento.
	 */
	public static String unaccent(final String src) {
		return Normalizer.normalize(src, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

}
