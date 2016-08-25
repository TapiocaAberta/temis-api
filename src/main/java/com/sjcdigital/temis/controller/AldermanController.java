/**
 * 
 */
package com.sjcdigital.temis.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sjcdigital.temis.dto.document.AldermanDto;
import com.sjcdigital.temis.model.document.Alderman;
import com.sjcdigital.temis.model.repositories.AldermanRepository;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
/**
 * @author pedro-hos
 */

@RestController(value = "api/alderman/")
public class AldermanController {
	
	@Autowired
	private AldermanRepository aldermanRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(code = HttpStatus.OK)
	public Collection<AldermanDto> getCurrentAldermen() {
		List<AldermanDto> aldermanListDto = new ArrayList<AldermanDto>();
		
		List<Alderman> aldermanList = aldermanRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
		for (Alderman alderman : aldermanList) {
			AldermanDto aldermanDto = alderman.convert();
			aldermanDto.add(linkTo(methodOn(AldermanController.class).getCurrentAldermen()).withSelfRel());
			aldermanDto.add(linkTo(methodOn(AldermanController.class).getAlderman(alderman.getName())).withRel("alderman"));
			
			aldermanListDto.add(aldermanDto);
		}
		
		return aldermanListDto;
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public AldermanDto getAlderman(@RequestParam() String name) {
		
		Optional<Alderman> alderman = aldermanRepository.findByName(name);
		return alderman.get().convert();
		
	}
	
}
