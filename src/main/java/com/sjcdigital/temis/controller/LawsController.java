package com.sjcdigital.temis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjcdigital.temis.controller.exceptions.ResourceNotFoundException;
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
	
	/**
	 * Find All Laws
	 * @param pageable
	 * @return Laws
	 */
	@GetMapping
	public PagedResources<Resource<Law>> findAllPageable(final Pageable pageable, final PagedResourcesAssembler<Law> assembler) {
		return assembler.toResource(lawsRepository.findAll(pageable));
	}
	
	/**
	 * Find one Law by Code
	 * @param pageable
	 * @return Laws
	 */
	@GetMapping("/{code}")
	public Law findByCode(@PathVariable final String code) {
		return lawsRepository.findByCode(code).orElseThrow(ResourceNotFoundException::new);
	}
	
	/**
	 * Vote Positive
	 * @param code Law
	 * @return Status
	 */
	@PutMapping("/{code}/vote/yes")
	public Law votePositive(@PathVariable final String code) {
		Law law = lawsRepository.findByCode(code).orElseThrow(ResourceNotFoundException::new);
		law.votePositive();
		return lawsRepository.save(law);
	}
	
	/**
	 * Vote Negative
	 * @param code Law
	 * @return Status
	 */
	@PutMapping("/{code}/vote/no")
	public Law voteNegative(@PathVariable final String code) {
		Law law = lawsRepository.findByCode(code).orElseThrow(ResourceNotFoundException::new);
		law.voteNegative();
		return lawsRepository.save(law);
	}
	
	/**
	 * find by law by alderman name
	 * @param name alderman name
	 * @param page page
	 * @return Laws 
	 */
	@GetMapping("/alderman/{name}")
	public PagedResources<Resource<Law>> findByAutorName(@PathVariable final String name, final Pageable page, final PagedResourcesAssembler<Law> assembler) {
		Page<Law> laws = lawsRepository.findByAuthorNameLike(name, page);
		return assembler.toResource(laws);
	}
	
}
