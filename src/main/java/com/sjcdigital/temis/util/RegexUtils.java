package com.sjcdigital.temis.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pedro-hos
 *
 */
public class RegexUtils {
	
	public static Matcher getMatcher(final String regex, final String input) {
		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		return pattern.matcher(input);
	}

}
