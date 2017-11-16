package com.sjcdigital.temis.model.service.bots.lei;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.sjcdigital.temis.model.service.machine_learn.ClassificaLeis;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class LeiClassificacao {
	
	@Inject
	private ClassificaLeis classificaLeis;
	
	public String classifica(String text) {
		return classificaLeis.classifica(text);
	}

}
