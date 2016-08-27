package com.sjcdigital.temis.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sjcdigital.temis.model.document.Law;
import com.sjcdigital.temis.model.repositories.LawsRepository;

/**
 * @author pedro-hos
 */
@Controller
@ExposesResourceFor(Law.class)
@RequestMapping("/api/laws")
public class LawsController extends AbstractController<Law> {
	
	@Autowired
	private LawsRepository lawsRepository;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Resources<Law>> findAllPageable(final Pageable pageable) {
		return ResponseEntity.ok(createResources(lawsRepository.findAll(pageable)));
	}
	
	@GetMapping(value = "/alderman/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Collection<Law> findByAutorName(@PathVariable final String name, final Pageable page) {
		return lawsRepository.findByAuthorNameLike(name, page).getContent();
	}
}
