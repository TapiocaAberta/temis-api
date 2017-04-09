package com.sjcdigital.temis.model.repositories.impl;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sjcdigital.temis.model.entities.impl.Tipo;
import com.sjcdigital.temis.model.repositories.Repository;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class Tipos extends Repository<Tipo> { 
	
	public Optional<Tipo> comNome(String nome) {

		TypedQuery<Tipo> query = em.createQuery("SELECT t FROM Tipo t WHERE LOWER(t.nome) like LOWER(:nome)", Tipo.class);
		query.setParameter("nome", nome);

		try {
			return Optional.of(query.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
	
}
