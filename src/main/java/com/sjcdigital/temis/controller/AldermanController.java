package com.sjcdigital.temis.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sjcdigital.temis.model.document.Alderman;
import com.sjcdigital.temis.model.document.Law;
import com.sjcdigital.temis.model.repositories.AldermanRepository;

/**
 * @author pedro-hos
 */

@Controller
@ExposesResourceFor(Alderman.class)
@RequestMapping("/api/alderman")
public class AldermanController extends AbstractController<Alderman> {
	
	@Autowired
	private AldermanRepository aldermanRepository;
	
	/**
	 * Get all Alderman
	 * @return List Alderman
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Resource<Alderman>>> getAlderman() {
		
		Collection<Resource<Alderman>> aldermanResources = new ArrayList<Resource<Alderman>>();
		Collection<Alderman> alderman = aldermanRepository.findAll();
		
		alderman.forEach(a -> {
			Resource<Alderman> resource = createLawResource(a);
			aldermanResources.add(resource);
		});
		
		return ResponseEntity.ok(aldermanResources);
	}

	
	/**
	 * Get alderman by Name
	 * @param name, alderman name
	 * @return Alderman
	 */
	@GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Resource<Alderman>> getAlderman(@RequestParam final String name) {
		Optional<Alderman> alderman = aldermanRepository.findByName(name);
		return ResponseEntity.ok(createLawResource(alderman.get()));
	}
	
	/**
	 * create Alderman Resource
	 * @param alderman
	 * @return Alderman
	 */
	private Resource<Alderman> createLawResource(Alderman alderman) {
		Resource<Alderman> resource = new Resource<>(alderman);
		resource.add(links.linkFor(Law.class).slash("/alderman/").slash(alderman.getName()).withRel("leis"));
		resource.add(links.linkFor(Alderman.class).slash(alderman.getName()).withSelfRel());
		return resource;
	}
	
	
}
