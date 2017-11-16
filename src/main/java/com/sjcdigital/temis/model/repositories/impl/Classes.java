package com.sjcdigital.temis.model.repositories.impl;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import com.sjcdigital.temis.model.entities.impl.Classe;
import com.sjcdigital.temis.model.repositories.Repository;

/**
 * @author pesilva
 *
 */
@Stateless
public class Classes extends Repository<Classe> {
	
	public Classe comTag(final String tag) {
		TypedQuery<Classe> query = em.createQuery("SELECT classe FROM Classe classe WHERE classe.tag = :tag", Classe.class);
		query.setParameter("tag", tag);
		return query.getSingleResult();
	}

}
