package com.sjcdigital.temis.model.repositories.impl;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sjcdigital.temis.model.entities.impl.Autor;
import com.sjcdigital.temis.model.entities.impl.Presenca;
import com.sjcdigital.temis.model.repositories.Repository;

@Stateless
public class Presencas extends Repository<Presenca> {

	public List<Presenca> porAutor(Autor autor) {
		TypedQuery<Presenca> presencasPorAutorQuery = em.createNamedQuery("Presenca.porAutor", Presenca.class);
		presencasPorAutorQuery.setParameter("autor", autor);
		try {
			return presencasPorAutorQuery.getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
		
	}

}
