package com.sjcdigital.temis.model.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sjcdigital.temis.model.document.Law;

/**
 * @author pedro-hos
 */
public interface LawsRepository extends MongoRepository<Law, String> {
	
	Optional<Law> findByCode(final String code);
	Optional<Law> findFirstByOrderByCodeDesc();
	
	@Query("{'author' :{'$ref' : 'alderman' , '$id' : ?0}}")
	Page<Law> findByAuthorId(final String id, final Pageable page);
	
	Optional<Page<Law>> findAllByOrderByDateDesc(final Pageable page);
	
}
