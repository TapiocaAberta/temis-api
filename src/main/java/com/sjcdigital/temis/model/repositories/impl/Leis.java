package com.sjcdigital.temis.model.repositories.impl;

import java.util.List;
import java.util.Objects;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

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
	
	public Long contaTodosFiltrado(final Long idSituacao, final Long idClasse, final Long idTipo, final Integer ano,
										final Long idAutor) {

		StringBuilder sql = new StringBuilder("SELECT COUNT(lei) FROM Lei lei ");
		StringBuilder where = new StringBuilder();

		escreveSeNaoNulo(idTipo, "lei.tipo.id = :idTipo ", where);
		escreveSeNaoNulo(idClasse, "lei.classe.id = :idClasse ", where);
		escreveSeNaoNulo(idSituacao, "lei.situacaoSimplificada.id = :idSituacao ", where);
		escreveSeNaoNulo(ano, "lei.ano = :ano ", where);
		escreveSeNaoNulo(idAutor, "lei.autor.id = :idAutor ", where);

		adicionaWhereSeNecessario(sql, where);

		TypedQuery<Long> query = em.createQuery(sql.toString(), Long.class);

		setParameterSeNaoNulo(idTipo, "idTipo", query);
		setParameterSeNaoNulo(idClasse, "idClasse", query);
		setParameterSeNaoNulo(idSituacao, "idSituacao", query);
		setParameterSeNaoNulo(ano, "ano", query);
		setParameterSeNaoNulo(idAutor, "idAutor", query);

		return query.getSingleResult();
	}
	
	public List<Lei> filtraPaginado( final Long idSituacao, final Long idClasse, final Long idTipo, final Integer ano, final Long idAutor, 
									 final int total, final int pg) {
		
		StringBuilder sql = new StringBuilder("SELECT lei FROM Lei lei ");
		StringBuilder where = new StringBuilder();
		
		escreveSeNaoNulo(idTipo, "lei.tipo.id = :idTipo ", where);
		escreveSeNaoNulo(idClasse, "lei.classe.id = :idClasse ", where);
		escreveSeNaoNulo(idSituacao, "lei.situacaoSimplificada.id = :idSituacao ", where);
		escreveSeNaoNulo(ano, "lei.ano = :ano ", where);
		escreveSeNaoNulo(idAutor, "lei.autor.id = :idAutor ", where);
		
		adicionaWhereSeNecessario(sql, where);
		
		TypedQuery<Lei> query = em.createQuery(sql.toString(), Lei.class);
		
		setParameterSeNaoNulo(idTipo, "idTipo", query);
		setParameterSeNaoNulo(idClasse, "idClasse", query);
		setParameterSeNaoNulo(idSituacao, "idSituacao", query);
		setParameterSeNaoNulo(ano, "ano", query);
		setParameterSeNaoNulo(idAutor, "idAutor", query);
		
		query.setFirstResult(pg * total);
		query.setMaxResults(total);
		
		return query.getResultList();
	}
	
	private void escreveSeNaoNulo(final Object obj, final String whereQuery, StringBuilder where) {
		if(Objects.nonNull(obj)) {
			adicionaAndSeNecessario(where);
			where.append(whereQuery);
		}
	}
	
	private void setParameterSeNaoNulo(final Object obj, final String parameter, TypedQuery<?> query) {
		if(Objects.nonNull(obj)) {
			query.setParameter(parameter, obj);
		}
	}

	private void adicionaWhereSeNecessario(StringBuilder sql, StringBuilder where) {
		if(StringUtils.isNotEmpty(where)) {
			sql.append(" WHERE ").append(where);
		}
	}

	private void adicionaAndSeNecessario(StringBuilder where) {
		if(StringUtils.isNotEmpty(where)) {
			where.append(" AND ");
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
	
	public Anos anos(final Long idAutor) {
		StringBuilder sql = new StringBuilder("SELECT DISTINCT new com.sjcdigital.temis.model.dto.Ano(lei.ano) FROM Lei lei ");
		
		if(Objects.nonNull(idAutor)) {
			sql.append("WHERE lei.autor.id = :idAutor ");
			
		}
		
		sql.append("ORDER BY lei.ano");
		
		TypedQuery<Ano> query = em.createQuery(sql.toString(), Ano.class);
		
		if(Objects.nonNull(idAutor)) {
			query.setParameter("idAutor", idAutor);
		}
		
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
