package com.sjcdigital.temis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sjcdigital.temis.controller.exceptions.ResourceNotFoundException;
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
	
	/**
	 * Find All Laws
	 * @param pageable
	 * @return Laws
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Resources<Law>> findAllPageable(final Pageable pageable) {
		return ResponseEntity.ok(createResources(lawsRepository.findAll(pageable)));
	}
	
	/**
	 * Find one Law by Code
	 * @param pageable
	 * @return Laws
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public @ResponseBody Law findByCode(@PathVariable final String code) {
		return lawsRepository.findByCode(code).orElseThrow(ResourceNotFoundException::new);
	}
	
	/**
	 * Vote Positive
	 * @param code Law
	 * @return Status
	 */
	@RequestMapping(value = "/{code}/vote/positive", method = RequestMethod.PUT)
	public @ResponseBody Law votePositive(@PathVariable final String code) {
		Law law = lawsRepository.findByCode(code).orElseThrow(ResourceNotFoundException::new);
		law.votePositive();
		return lawsRepository.save(law);
	}
	
	/**
	 * Vote Negative
	 * @param code Law
	 * @return Status
	 */
	@RequestMapping(value = "/{code}/vote/negative", method = RequestMethod.PUT)
	public @ResponseBody Law voteNegative(@PathVariable final String code) {
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
	@RequestMapping(value = "/alderman/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Resources<Law>> findByAutorName(@PathVariable final String name, final Pageable page) {
		Page<Law> laws = lawsRepository.findByAuthorNameLike(name, page);
		return ResponseEntity.ok(createResources(laws));
	}
	
}
