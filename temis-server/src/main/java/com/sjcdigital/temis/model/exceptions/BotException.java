package com.sjcdigital.temis.model.exceptions;

/**
 * 
 * @author pedro-hos
 *
 */
public class BotException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public BotException(final Throwable exec) {
		super("Error Unknown during to convert of the results.", exec);
	}
	
	public BotException() {
		super("Error Unknown during to convert of the results.");
	}
	
}
