package com.sjcdigital.temis.model.repositories.impl;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sjcdigital.temis.model.entities.impl.Classe;
import com.sjcdigital.temis.model.repositories.Repository;

/**
 * @author pesilva
 *
 */
@Stateless
public class Classes extends Repository<Classe> {
	
	@Inject
	private Logger logger;
	
	public Classe comTag(final String tag) {
		
		try {
			
			TypedQuery<Classe> query = em.createQuery("SELECT classe FROM Classe classe WHERE classe.tag = :tag", Classe.class);
			query.setParameter("tag", tag);
			return query.getSingleResult();
			
		} catch (NoResultException e) {
			logger.severe("Tag não encontrada: " + tag);
			return comTag("SEM_CLASSIFICACAO");
		}
	}

}
