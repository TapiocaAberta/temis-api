package com.sjcdigital.temis.model.repositories.impl;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sjcdigital.temis.model.entities.impl.Sessao;
import com.sjcdigital.temis.model.repositories.Repository;

@Stateless
public class Sessoes extends Repository<Sessao> {

	public Optional<Sessao> comNome(final String nome) {
		TypedQuery<Sessao> sessaoPorNomeQuery = em.createNamedQuery("Sessao.porNome", Sessao.class);
		sessaoPorNomeQuery.setParameter("nome", nome);
		try {
			return Optional.of(sessaoPorNomeQuery.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

}
