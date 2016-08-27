/**
 * 
 */
package com.sjcdigital.temis.controller;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

/**
 * @author pedro-hos
 */
public abstract class AbstractController<T> {
	
	@Autowired
	private EntityLinks links;
	
	public Resources<T> createResources(Page<T> page) {
		
		Resources<T> resources = new Resources<>(page);
		
		Optional<Link> self = createLink(page.getNumber(), Link.REL_SELF);
		self.ifPresent(resources :: add);
		
		Optional<Link> first = createLink(0, Link.REL_FIRST);
		first.ifPresent(resources :: add);
		
		Optional<Link> next = createLink(page.nextPageable(), Link.REL_NEXT);
		next.ifPresent(resources :: add);
		
		Optional<Link> privious = createLink(page.previousPageable(), Link.REL_PREVIOUS);
		privious.ifPresent(resources :: add);
		
		Optional<Link> last = createLink(page.getTotalPages(), Link.REL_LAST);
		last.ifPresent(resources :: add);
		
		return resources;
		
	}

	private Optional<Link> createLink(int page, String rel) {
		
		if(Objects.nonNull(page)) {
			return Optional.of(links.linkFor(getClazz()).slash("/?page=" + page).withRel(rel));
		}
		
		return Optional.empty();
		
	}

	private Optional<Link> createLink(Pageable pageable, String rel) {
		
		if(Objects.nonNull(pageable)) {
			return Optional.of(links.linkFor(getClazz()).slash("?page=" + pageable.getPageNumber()).withRel(rel));
		}
		
		return Optional.empty();
		
	}
	
	@SuppressWarnings("unchecked")
	private Class<T> getClazz() {
		return (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	

}
