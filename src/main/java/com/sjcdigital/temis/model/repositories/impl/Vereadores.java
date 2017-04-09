package com.sjcdigital.temis.model.repositories.impl;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sjcdigital.temis.model.entities.impl.Vereador;
import com.sjcdigital.temis.model.repositories.Repository;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class Vereadores extends Repository<Vereador> {

	/**
	 * @param nome
	 * @return
	 */
	public Optional<Vereador> comName(final String nome) {
		
		TypedQuery<Vereador> query = em.createQuery("SELECT v FROM Vereador v WHERE LOWER(v.nome) = LOWER(:nome)", Vereador.class);
		query.setParameter("nome", nome);
		
		try {
			return  Optional.of(query.getSingleResult());
			
		} catch (NoResultException e) {
			return Optional.empty();
		}
		
	}
	
}
