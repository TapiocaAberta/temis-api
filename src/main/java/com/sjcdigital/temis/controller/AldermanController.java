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
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Resource<Alderman>>> getCurrentAldermen() {
		
		Collection<Resource<Alderman>> aldermanResources = new ArrayList<Resource<Alderman>>();
		Collection<Alderman> alderman = aldermanRepository.findAll();
		
		alderman.forEach(a -> {
			Resource<Alderman> resource = createLeiResource(a);
			aldermanResources.add(resource);
		});
		
		return ResponseEntity.ok(aldermanResources);
	}

	
	@GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Resource<Alderman>> getAlderman(@RequestParam final String name) {
		Optional<Alderman> alderman = aldermanRepository.findByName(name);
		return ResponseEntity.ok(createLeiResource(alderman.get()));
	}
	
	private Resource<Alderman> createLeiResource(Alderman a) {
		Resource<Alderman> resource = new Resource<>(a);
		resource.add(links.linkFor(Law.class).slash("/alderman/").slash(a.getName()).withRel("leis"));
		return resource;
	}
	
	
}
