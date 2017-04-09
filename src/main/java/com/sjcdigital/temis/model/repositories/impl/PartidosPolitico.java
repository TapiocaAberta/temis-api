package com.sjcdigital.temis.model.repositories.impl;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sjcdigital.temis.model.entities.impl.PartidoPolitico;
import com.sjcdigital.temis.model.repositories.Repository;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class PartidosPolitico extends Repository<PartidoPolitico> {
	
	public Optional<PartidoPolitico> comNome( final String nome) {
		
		TypedQuery<PartidoPolitico> query = em.createQuery("SELECT p FROM PartidoPolitico p WHERE LOWER(p.nome) like LOWER(:nome)", PartidoPolitico.class);
		query.setParameter("nome", nome);
		
		try {
			return  Optional.of(query.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}		
	}

	public Optional<PartidoPolitico> comSigla(final String sigla) {
		
		TypedQuery<PartidoPolitico> query = em.createQuery("SELECT p FROM PartidoPolitico p WHERE LOWER(p.sigla) like LOWER(:sigla)", PartidoPolitico.class);
		query.setParameter("sigla", sigla);
		
		try {
			return  Optional.of(query.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}	
	}

}
