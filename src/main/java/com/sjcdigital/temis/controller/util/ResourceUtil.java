package com.sjcdigital.temis.controller.util;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

/**
 * @author pedro-hos
 *         
 */
public class ResourceUtil {
	
	private static final String SIZE = "&size=";
	private static final String PAGE = "?page=";
	
	public static <T> Resources<T> createResources(Page<T> page, String endopoint, EntityLinks links, Class<?> clazz) {
		
		Resources<T> resources = new Resources<>(page);
		
		Optional<Link> self = createLink(endopoint, page.getNumber(), page.getSize(), Link.REL_SELF, links, clazz);
		self.ifPresent(resources::add);
		
		Optional<Link> first = createLink(endopoint, 0, page.getSize(), Link.REL_FIRST, links, clazz);
		first.ifPresent(resources::add);
		
		Optional<Link> next = createLink(endopoint, page.nextPageable(), Link.REL_NEXT, links, clazz);
		next.ifPresent(resources::add);
		
		Optional<Link> privious = createLink(endopoint, page.previousPageable(), Link.REL_PREVIOUS, links, clazz);
		privious.ifPresent(resources::add);
		
		Optional<Link> last = createLink(endopoint, page.getTotalPages(), page.getSize(), Link.REL_LAST, links, clazz);
		last.ifPresent(resources::add);
		
		return resources;
		
	}
	
	protected static Optional<Link> createLink(String endpoint, int page, int size, String rel, EntityLinks links, Class<?> clazz) {
			
		if (Objects.nonNull(page)) {
			String pageAndSize = PAGE + page + SIZE + size;
			return Optional.of(links.linkFor(clazz).slash(endpoint + pageAndSize).withRel(rel));
		}
		
		return Optional.empty();
		
	}
	
	protected static Optional<Link> createLink(String endpoint, Pageable pageable, String rel, EntityLinks links, Class<?> clazz) {
			
		if (Objects.nonNull(pageable)) {
			String pageAndSize = PAGE + pageable.getPageNumber() + SIZE + pageable.getPageSize();
			return Optional.of(links.linkFor(clazz).slash(endpoint + pageAndSize).withRel(rel));
		}
		
		return Optional.empty();
		
	}
	
}
