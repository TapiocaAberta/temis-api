package com.sjcdigital.temis.model.repositories;

import com.sjcdigital.temis.model.document.OrdinarySession;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author fabiohbarbosa
 */
public interface OrdinarySessionRepository extends MongoRepository<OrdinarySession, String> {
}
