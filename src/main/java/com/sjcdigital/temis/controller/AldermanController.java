package com.sjcdigital.temis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjcdigital.temis.controller.exceptions.ResourceNotFoundException;
import com.sjcdigital.temis.model.document.Alderman;
import com.sjcdigital.temis.model.document.Law;
import com.sjcdigital.temis.model.repositories.AldermanRepository;
import com.sjcdigital.temis.model.repositories.LawsRepository;

/**
 * @author pedro-hos
 */

@RestController
@RequestMapping("/api/alderman")
@ExposesResourceFor(Alderman.class)
public class AldermanController {
	
	@Autowired
	private AldermanRepository aldermanRepository;
	
	@Autowired
	private LawsRepository lawRepository;
	
	@Autowired
	private EntityLinks entityLinks;
	
	/**
	 * Get all Alderman
	 * @return List Alderman
	 */
	@GetMapping
	public PagedResources<Resource<Alderman>> findAll(final Pageable pageable, final PagedResourcesAssembler<Alderman> assembler) {
		final PagedResources<Resource<Alderman>> pagedResources = assembler.toResource(aldermanRepository.findAll(pageable));
		pagedResources.forEach(this :: createAldermanResource);
		return pagedResources;
	}

	
	/**
	 * Get alderman by Name
	 * @param name, alderman name
	 * @return Alderman
	 */
	@GetMapping("/{name}")
	public Resource<Alderman> findByName(@PathVariable final String name) {
		Alderman alderman = aldermanRepository.findByName(name).orElseThrow(ResourceNotFoundException :: new);
		Resource<Alderman> resource = new Resource<Alderman>(alderman);
		createAldermanResource(resource);
		return resource;
	}
	
	/**
	 * Get alderman law by alderman name
	 * @param name
	 * @return Alderman
	 */
	@GetMapping("/{name}/law")
	public PagedResources<Resource<Law>> findLawByAlderman(@PathVariable final String name, final Pageable pageable, final PagedResourcesAssembler<Law> assembler) {
		Alderman alderman = aldermanRepository.findByName(name).orElseThrow(ResourceNotFoundException ::  new);
		Page<Law> laws = lawRepository.findByAuthorId(alderman.getId(), pageable);
		return assembler.toResource(laws);
	}
	
	/**
	 * build Alderman Resources
	 * @param alderman
	 * @param resource
	 */
	private void createAldermanResource(Resource<Alderman> resource) {
		resource.add(entityLinks.linkFor(Alderman.class).slash(resource.getContent().getName()).withSelfRel());
		resource.add(entityLinks.linkFor(Alderman.class).slash(resource.getContent().getName()).slash("/law").withRel("leis"));
	}
	
}
