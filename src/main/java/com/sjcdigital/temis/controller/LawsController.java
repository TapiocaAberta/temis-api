package com.sjcdigital.temis.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public Collection<Law> findAllPageable(Pageable page) {
		return lawsRepository.findAll(page).getContent();
	}
	
	@RequestMapping(value = "/alderman/{name}", method = RequestMethod.GET)
	public Collection<Law> findByAutorName(@PathVariable final String name, Pageable page) {
		return lawsRepository.findByAuthorNameLike(name, page).getContent();
	}
}
