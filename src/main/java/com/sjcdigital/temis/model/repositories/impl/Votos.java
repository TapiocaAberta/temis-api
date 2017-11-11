package com.sjcdigital.temis.model.repositories.impl;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sjcdigital.temis.model.entities.impl.Lei;
import com.sjcdigital.temis.model.entities.impl.Voto;
import com.sjcdigital.temis.model.repositories.Repository;

/**
 * @author pedro-hos
 *
 */
@Stateless
public class Votos extends Repository<Voto> {
	
	public Boolean podeVotar(String ip, Lei lei) {

		TypedQuery<Voto> query = em.createQuery("SELECT v FROM Voto v WHERE v.ip = :ip and v.lei.id = :idLei", Voto.class);
		query.setParameter("ip", ip);
		query.setParameter("idLei", lei.getId());
		
		try {
			
			query.getSingleResult();
			return false;
			
		} catch (NoResultException e) {
			salvar(new Voto(ip, lei));
			return true;
		}
		
	}
}
