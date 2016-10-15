package com.sjcdigital.temis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
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
@ExposesResourceFor(Law.class)
public class LawsController {
	
	@Autowired
	private LawsRepository lawsRepository;
	
	@Autowired
	private EntityLinks entityLinks;
	
	/**
	 * Find All Laws
	 * @param pageable
	 * @return Laws
	 */
	@GetMapping
	public PagedResources<Resource<Law>> findAllPageable(final Pageable pageable, final PagedResourcesAssembler<Law> assembler) {
		PagedResources<Resource<Law>> pagedResources = assembler.toResource(lawsRepository.findAllByOrderByDateDesc(pageable)
																						  .orElseThrow(ResourceNotFoundException :: new));
		pagedResources.forEach(this :: createVoteResource);
		return pagedResources;
	}
	
	/**
	 * Find one Law by Code
	 * @param pageable
	 * @return Laws
	 */
	@GetMapping("/{code}")
	public Resource<Law> findByCode(@PathVariable final String code) {
		Law law = lawsRepository.findByCode(code).orElseThrow(ResourceNotFoundException::new);
		Resource<Law> resource = new Resource<Law>(law);
		createVoteResource(resource);
		return resource;
	}
	
	/**
	 * Vote Positive
	 * @param code Law
	 * @return Status
	 */
	@PutMapping("/{code}/vote/yes")
	public Resource<Law> voteYes(@PathVariable final String code) {
		
		Law law = lawsRepository.findByCode(code).orElseThrow(ResourceNotFoundException::new);
		law.votePositive();
		
		Resource<Law> resource = new Resource<Law>(lawsRepository.save(law));
		createVoteResource(resource);
		
		return resource;
	}
	
	/**
	 * Vote Negative
	 * @param code Law
	 * @return Status
	 */
	@PutMapping("/{code}/vote/no")
	public Resource<Law> voteNo(@PathVariable final String code) {
		Law law = lawsRepository.findByCode(code).orElseThrow(ResourceNotFoundException::new);
		law.voteNegative();
		
		Resource<Law> resource = new Resource<Law>(lawsRepository.save(law));
		createVoteResource(resource);
		
		return resource;
	}
	
	private void createVoteResource(Resource<Law> resource) {
		resource.add(entityLinks.linkFor(Law.class).slash(resource.getContent().getCode()).withSelfRel());
		resource.add(entityLinks.linkFor(Law.class).slash(resource.getContent().getCode()).slash("/vote/yes").withRel("voteYes"));
		resource.add(entityLinks.linkFor(Law.class).slash(resource.getContent().getCode()).slash("/vote/no").withRel("voteNo"));
	}
	
}
