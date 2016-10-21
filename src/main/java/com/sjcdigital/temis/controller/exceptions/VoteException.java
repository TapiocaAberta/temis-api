/**
 * 
 */
package com.sjcdigital.temis.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author pedro-hos
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Você já votou nessa Lei!")
public class VoteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
