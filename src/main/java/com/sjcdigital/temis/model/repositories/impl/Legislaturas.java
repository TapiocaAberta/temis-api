package com.sjcdigital.temis.model.repositories.impl;

import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sjcdigital.temis.model.entities.impl.Legislatura;
import com.sjcdigital.temis.model.repositories.Repository;

public class Legislaturas extends Repository<Legislatura>{
	
	public Optional<Legislatura> comNome(final String nome) {
		TypedQuery<Legislatura> sessaoPorNomeQuery = em.createNamedQuery("Legislatura.porNome", Legislatura.class);
		sessaoPorNomeQuery.setParameter("nome", nome);
		try {
			return Optional.of(sessaoPorNomeQuery.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
}
