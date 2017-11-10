package com.sjcdigital.temis.model.repositories.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import com.sjcdigital.temis.model.entities.impl.Lei;
import com.sjcdigital.temis.model.repositories.Repository;

/**
 * @author pedro-hos
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class Leis extends Repository<Lei> {
	
	public List<Lei> comIds(final List<Long> ids) {
		TypedQuery<Lei> query = em.createQuery("SELECT lei FROM Lei lei WHERE lei.id in (:ids)", Lei.class);
		query.setParameter("ids", ids);
		return query.getResultList();
	}

	public List<Lei> doAutor(final Long idAutor) {
		TypedQuery<Lei> query = em.createQuery("SELECT lei FROM Lei lei WHERE lei.autor.id = :autor_id", Lei.class);
		query.setParameter("autor_id", idAutor);
		return query.getResultList();
	}
	
	public List<Lei> comSituacaoSimplificada(final Long id) {
		TypedQuery<Lei> query = em.createQuery("SELECT lei FROM Lei lei WHERE lei.situacaoSimplificada.id = :id", Lei.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public List<Lei> comTipo(final Long id, int total, int pg) {
		TypedQuery<Lei> query = em.createQuery("SELECT lei FROM Lei lei WHERE lei.tipo.id = :id", Lei.class);
		query.setParameter("id", id);
		query.setFirstResult(pg * total);
		query.setMaxResults(total);
		
		return query.getResultList();
	}
	
	public List<Lei> comClasse(final Long id) {
		TypedQuery<Lei> query = em.createQuery("SELECT lei FROM Lei lei WHERE lei.classe.id = :id", Lei.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

}
