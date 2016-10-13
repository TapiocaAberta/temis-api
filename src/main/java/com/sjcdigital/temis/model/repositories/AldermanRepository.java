package com.sjcdigital.temis.model.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sjcdigital.temis.model.document.Alderman;

/**
 * @author pedro-hos
 */
public interface AldermanRepository extends MongoRepository<Alderman, String> {
	
	Optional<Alderman> findByName(final String name);
	Optional<Alderman> findByNameContainingIgnoreCase(final String name);
	
}
