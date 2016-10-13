package com.sjcdigital.temis.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
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
public class AldermanController {
	
	@Autowired
	private AldermanRepository aldermanRepository;
	
	@Autowired
	private LawsRepository lawRepository;
	
	/**
	 * Get all Alderman
	 * @return List Alderman
	 */
	@GetMapping
	public PagedResources<Resource<Alderman>> findAll(final Pageable pageable, final PagedResourcesAssembler<Alderman> assembler) {
		
		final PagedResources<Resource<Alderman>> pagedResources = assembler.toResource(aldermanRepository.findAll(pageable));
		
		pagedResources.forEach(resource -> {
			createAldermanResource(resource.getContent(), resource);
		});
		
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
		createAldermanResource(alderman, resource);
		
		return resource;
	}
	
	/**
	 * Get alderman law by alderman name
	 * @param name, alderman name
	 * @return Alderman
	 */
	@GetMapping("/{name}/law")
	public PagedResources<Resource<Law>> findLawByAlderman(@PathVariable final String name, final Pageable pageable, final PagedResourcesAssembler<Law> assembler) {
		return assembler.toResource(lawRepository.findByAuthorNameLike(name, pageable));
	}
	
	/**
	 * build Alderman Resources
	 * @param alderman
	 * @param resource
	 */
	private void createAldermanResource(Alderman alderman, Resource<Alderman> resource) {
		resource.add(linkTo(methodOn(AldermanController.class).findByName(alderman.getName())).withSelfRel());
		resource.add(linkTo(methodOn(AldermanController.class).findLawByAlderman(alderman.getName(), null, null)).withRel("leis"));
	}
	
	
}
