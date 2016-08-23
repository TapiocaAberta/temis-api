/**
 * 
 */
package com.sjcdigital.temis.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sjcdigital.temis.model.document.Alderman;
import com.sjcdigital.temis.model.repositories.AldermanRepository;

/**
 * @author pedro-hos
 */

@RestController
@RequestMapping("/api/alderman")
public class AldermanController {
	
	@Autowired
	private AldermanRepository aldermanRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public Collection<Alderman> getCurrentAldermen() {
		return aldermanRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	}
	
}
