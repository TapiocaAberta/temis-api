package com.sjcdigital.temis.model.repositories.impl;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sjcdigital.temis.model.entities.impl.SituacaoSimplificada;
import com.sjcdigital.temis.model.repositories.Repository;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class SituacoesSimplificada extends Repository<SituacaoSimplificada> {

	public Optional<SituacaoSimplificada> comNome(String nome) {

		TypedQuery<SituacaoSimplificada> query = em.createQuery("SELECT s FROM SituacaoSimplificada s WHERE LOWER(s.nome) like LOWER(:nome)", SituacaoSimplificada.class);
		query.setParameter("nome", nome);

		try {
			return Optional.of(query.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

}
