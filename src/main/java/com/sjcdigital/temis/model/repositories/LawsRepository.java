package com.sjcdigital.temis.model.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sjcdigital.temis.model.document.Law;

/**
 * @author pedro-hos
 */
public interface LawsRepository extends PagingAndSortingRepository<Law, String> {
	
	Optional<Law> findByCode(final String code);
	Optional<Law> findFirstByOrderByCodeDesc();
	
	@Query("{'author' :{'$ref' : 'alderman' , '$id' : ?0}}")
	Page<Law> findByAuthorId(final String id, final Pageable page);
	
	Optional<Page<Law>> findAllByOrderByCodeDesc(final Pageable page);
	
}
