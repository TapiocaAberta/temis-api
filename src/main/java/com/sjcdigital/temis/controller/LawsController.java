package com.sjcdigital.temis.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
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
@ExposesResourceFor(LawsController.class)
@RequestMapping("/api/laws/")
public class LawsController {
	
	@Autowired
	private LawsRepository lawsRepository;
	
	@GetMapping
	public Resources<Law> findAllPageable(Pageable page) {
		
		Resources<Law> resouce = new Resources<>(lawsRepository.findAll(page));
		resouce.add(linkTo(methodOn(LawsController.class).findAllPageable(page.next())).withRel(Link.REL_NEXT));
		
		return resouce;
	}
	
	@GetMapping(value = "alderman/{name}")
	public Collection<Law> findByAutorName(@PathVariable final String name, Pageable page) {
		return lawsRepository.findByAuthorNameLike(name, page).getContent();
	}
}
