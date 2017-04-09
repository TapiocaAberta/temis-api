package com.sjcdigital.temis.model.repositories;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * 
 * Uma classe Repositorio abstrata para uso com as entidades do nosso sistema
 * 
 * @author Pedro Hos
 * @author William Siqueira
 * 
 */
@Stateless
public abstract class Repository<T> {

	protected Class<T> tipo = retornaTipo();

	@PersistenceContext(unitName = "temis-unit")
	protected EntityManager em;

	public T salvar(T entidade) {
		em.persist(entidade);
		return entidade;
	}

	public void remover(T entidade) {
		em.remove(entidade);
	}

	@SuppressWarnings("unchecked")
	public List<T> todos() {
		CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(tipo));
		return (List<T>) em.createQuery(cq).getResultList();
	}

	public T buscarPorId(long id) {
		return em.find(tipo, id);
	}

	public T atualizar(T entidade) {
		return em.merge(entidade);
	}

	/**
	 * @author Pedro Hos<br>
	 * 
	 *         Utilizando Exemplo de Eduardo Guerra!
	 *         https://groups.google.com/forum
	 *         /#!topic/projeto-oo-guiado-por-padroes/pOIiOD9cifs
	 * 
	 *         Este método retorna o tipo da Classe, dessa maneira não é
	 *         necessário cada Service expor seu tipo!!!!
	 * 
	 * @return Class<T>
	 */
	@SuppressWarnings({ "unchecked" })
	private Class<T> retornaTipo() {
		Class<?> clazz = this.getClass();

		while (!clazz.getSuperclass().equals(Repository.class)) {
			clazz = clazz.getSuperclass();
		}

		ParameterizedType tipoGenerico = (ParameterizedType) clazz.getGenericSuperclass();
		return (Class<T>) tipoGenerico.getActualTypeArguments()[0];
	}
}
