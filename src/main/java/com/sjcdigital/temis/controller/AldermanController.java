/**
 * 
 */
package com.sjcdigital.temis.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sjcdigital.temis.model.document.Alderman;
import com.sjcdigital.temis.model.repositories.AldermanRepository;

/**
 * @author pedro-hos
 */

@Controller
@RequestMapping("/api/alderman")
public class AldermanController {
	
	@Autowired
	private AldermanRepository aldermanRepository;
	
	@GetMapping(value = "/")
	public Collection<Alderman> getCurrentAldermen(Pageable page) {
		return aldermanRepository.findAll(page).getContent();
	}
	
	@GetMapping(value = "/{name}")
	public Alderman getAlderman(@RequestParam String name) {
		Optional<Alderman> alderman = aldermanRepository.findByName(name);
		return alderman.get();
	}
	
}
