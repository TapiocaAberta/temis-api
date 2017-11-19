package com.sjcdigital.temis.model.repositories.impl;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import com.sjcdigital.temis.model.dto.Ano;
import com.sjcdigital.temis.model.dto.Anos;
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
	
	public List<Lei> filtraPaginado(final Long idSituacao, final Long idClasse, final Long idTipo, final Integer ano, final int total, final int pg) {
		
		StringBuilder sql = new StringBuilder("SELECT lei FROM Lei lei ");
		boolean and = false;
		boolean where = false;
		
		if(Objects.nonNull(idTipo)) {
			and = true;
			where = true;
			sql.append(" WHERE lei.tipo.id = :idTipo ");
		}
		
		if(Objects.nonNull(idClasse)) {
			adicionaWhereSeNecessario(sql, where);
			adicionaAndSeNecessario(sql, and);
			and = true;
			where = true;
			sql.append("lei.classe.id = :idClasse ");
		}
		
		if(Objects.nonNull(idSituacao)) {
			adicionaWhereSeNecessario(sql, where);
			adicionaAndSeNecessario(sql, and);
			and = true;
			where = true;
			sql.append("lei.situacaoSimplificada.id = :idSituacao ");
		}
		
		if(Objects.nonNull(ano)) {
			adicionaWhereSeNecessario(sql, where);
			adicionaAndSeNecessario(sql, and);
			and = true;
			where = true;
			sql.append("lei.ano = :ano ");
			
		}
		
		TypedQuery<Lei> query = em.createQuery(sql.toString(), Lei.class);
		
		if(Objects.nonNull(idTipo)) {
			query.setParameter("idTipo", idTipo);
		}
		
		if(Objects.nonNull(idClasse)) {
			query.setParameter("idClasse", idClasse);
		}
		
		if(Objects.nonNull(idSituacao)) {
			query.setParameter("idSituacao", idSituacao);
		}
		
		if(Objects.nonNull(ano)) {
			query.setParameter("ano", ano);
		}
		
		query.setFirstResult(pg * total);
		query.setMaxResults(total);
		
		return query.getResultList();
	}

	private void adicionaWhereSeNecessario(StringBuilder sql, boolean where) {
		if(!where) {
			sql.append(" WHERE ");
		}
	}

	private void adicionaAndSeNecessario(StringBuilder sql, boolean and) {
		if (and) {
			sql.append(" and ");
		}
	}
	
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
	
	public Anos anos() {
		TypedQuery<Ano> query = em.createQuery("SELECT DISTINCT new com.sjcdigital.temis.model.dto.Ano(lei.ano) FROM Lei lei ORDER BY lei.ano", Ano.class);
		List<Ano> anos = query.getResultList();
		return new Anos(anos);
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
