package com.sjcdigital.temis.model.repositories.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import com.sjcdigital.temis.model.dto.chart.DataChart;
import com.sjcdigital.temis.model.entities.impl.Lei;
import com.sjcdigital.temis.model.repositories.Repository;

/**
 * @author pedro-hos
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class Leis extends Repository<Lei> {
	
	// Por autor
	
	public List<DataChart> contaLeisPorTipoEAutor(final Long id) {
		
		String sql = "SELECT new com.sjcdigital.temis.model.dto.chart.DataChart(tipo.nome, count(tipo)) "
				   + "FROM Lei lei JOIN lei.tipo tipo WHERE lei.autor.id = :id GROUP BY tipo.id";
		
		TypedQuery<DataChart> query = em.createQuery(sql, DataChart.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public List<DataChart> contaLeisPorSituacaoEAutor(final Long id) {
		
		String sql = "SELECT new com.sjcdigital.temis.model.dto.chart.DataChart(situacaoSimplificada.nome, count(situacaoSimplificada)) "
				   + "FROM Lei lei JOIN lei.situacaoSimplificada situacaoSimplificada WHERE lei.autor.id = :id GROUP BY situacaoSimplificada.id";
		
		TypedQuery<DataChart> query = em.createQuery(sql, DataChart.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

	public List<DataChart> contaLeisPorClasseEAutor(final Long id) {
	
		String sql = "SELECT new com.sjcdigital.temis.model.dto.chart.DataChart(classe.nome, count(classe)) "
				   + "FROM Lei lei JOIN lei.classe classe WHERE lei.autor.id = :id GROUP BY classe.id";
		
		TypedQuery<DataChart> query = em.createQuery(sql, DataChart.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	public List<Lei> doAutor(final Long idAutor, int total, int pg) {
		TypedQuery<Lei> query = em.createQuery("SELECT lei FROM Lei lei WHERE lei.autor.id = :autor_id", Lei.class);
		query.setParameter("autor_id", idAutor);
		query.setFirstResult(pg * total);
		query.setMaxResults(total);
		return query.getResultList();
	}
	
	// Sem ser por autor
	
	public List<DataChart> contaLeisPorTipo() {
		
		String sql = "SELECT new com.sjcdigital.temis.model.dto.chart.DataChart(tipo.nome, count(tipo)) "
				   + "FROM Lei lei JOIN lei.tipo tipo GROUP BY tipo.id";
		
		TypedQuery<DataChart> query = em.createQuery(sql, DataChart.class);
		
		return query.getResultList();
	}
	
	public List<DataChart> contaLeisPorSituacao() {
		
		String sql = "SELECT new com.sjcdigital.temis.model.dto.chart.DataChart(situacaoSimplificada.nome, count(situacaoSimplificada)) "
				   + "FROM Lei lei JOIN lei.situacaoSimplificada situacaoSimplificada GROUP BY situacaoSimplificada.id";
		
		TypedQuery<DataChart> query = em.createQuery(sql, DataChart.class);
		return query.getResultList();
	}

	public List<DataChart> contaLeisPorClasse() {
	
		String sql = "SELECT new com.sjcdigital.temis.model.dto.chart.DataChart(classe.nome, count(classe)) "
				   + "FROM Lei lei JOIN lei.classe classe GROUP BY classe.id";
		
		TypedQuery<DataChart> query = em.createQuery(sql, DataChart.class);
		return query.getResultList();
	}
	
	public List<Lei> comIds(final List<Long> ids) {
		TypedQuery<Lei> query = em.createQuery("SELECT lei FROM Lei lei WHERE lei.id in (:ids)", Lei.class);
		query.setParameter("ids", ids);
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
