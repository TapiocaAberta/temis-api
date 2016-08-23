package com.sjcdigital.temis.model.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sjcdigital.temis.model.document.Law;

/**
 * @author pedro-hos
 */
public interface LawsRepository extends MongoRepository<Law, String> {

	Optional<Law> findByCode(String code);
	Optional<Law> findFirstByOrderByCodeDesc();
}
