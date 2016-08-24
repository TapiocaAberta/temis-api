package com.sjcdigital.temis.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjcdigital.temis.model.document.Law;
import com.sjcdigital.temis.model.repositories.LawsRepository;

/**
 * @author pedro-hos
 */
@RestController
@RequestMapping("/api/laws")
public class LawsController {

	@Autowired
	private LawsRepository lawsRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public Page<Law> findAllPageable(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10", required = false) Integer size) {
		return lawsRepository.findAll(new PageRequest(page, size));
	}
	
	@RequestMapping(value = "/alderman/{name}", method = RequestMethod.GET)
	public Collection<Law> findByAutorName( @PathVariable final String name, 
									  @RequestParam(defaultValue = "1") final Integer page,
									  @RequestParam(defaultValue = "10", required = false) final Integer size ) {
		
		return lawsRepository.findByAuthorNameLike(name);
	}
}
