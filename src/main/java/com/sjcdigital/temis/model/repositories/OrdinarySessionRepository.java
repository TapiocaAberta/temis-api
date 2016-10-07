package com.sjcdigital.temis.model.repositories;

import com.sjcdigital.temis.model.document.Alderman;
import com.sjcdigital.temis.model.document.OrdinarySession;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author fabiohbarbosa
 */
public interface OrdinarySessionRepository extends MongoRepository<OrdinarySession, String> {
    Optional<Alderman> findBySessionAndDateAndAlderman(int session, LocalDate date, Alderman alderman);
}
