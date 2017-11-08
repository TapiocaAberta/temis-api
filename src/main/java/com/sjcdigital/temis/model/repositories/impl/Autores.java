package com.sjcdigital.temis.model.repositories.impl;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sjcdigital.temis.model.entities.impl.Autor;
import com.sjcdigital.temis.model.repositories.Repository;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class Autores extends Repository<Autor> {

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Optional<Autor> comName(final String nome) {
		
		TypedQuery<Autor> query = em.createQuery("SELECT autor FROM Autor autor WHERE LOWER(autor.nome) = LOWER(:nome)", Autor.class);
		query.setParameter("nome", nome);
		
		try {
			return  Optional.of(query.getSingleResult());
			
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Optional<List<Autor>> comNameList(final String nome) {
		
		TypedQuery<Autor> query = em.createQuery("SELECT autor FROM Autor autor WHERE LOWER(autor.nome) LIKE LOWER(CONCAT('%', :nome, '%'))", Autor.class);
		query.setParameter("nome", nome);
		
		try {
			return  Optional.of(query.getResultList());
			
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void salvarNovaTransacao(Autor entidade) {
		super.salvar(entidade);
	}
}
