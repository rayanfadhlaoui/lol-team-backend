package com.lolteam.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.lolteam.entities.GenericEntity;

public abstract class GenericDao<T extends GenericEntity> {

	@PersistenceContext
	protected EntityManager em;

	protected abstract Class<T> getClassType();

	public List<T> getAll() {
		return getTypedQueryAll().getResultList();
	}

	public List<T> getAll(int maxResult) {
		return getTypedQueryAll().setMaxResults(maxResult).getResultList();
	}

	public T get(int id) {
		Class<T> classType = getClassType();

		String sqlRequest = "select t " + "from " + classType.getSimpleName() + " t " + "where t.id = :id";
		System.out.println(sqlRequest);
		TypedQuery<T> query = em.createQuery(sqlRequest, classType).setParameter("id", id);
		return query.getSingleResult();
	}

	private TypedQuery<T> getTypedQueryAll() {
		final CriteriaBuilder lCriteriaBuilder = em.getCriteriaBuilder();
		Class<T> classType = getClassType();
		final CriteriaQuery<T> queryBuilder = lCriteriaBuilder.createQuery(classType);
		final Root<T> lRoot = queryBuilder.from(classType);
		queryBuilder.select(lRoot).where();
		final TypedQuery<T> lTypedQuery = em.createQuery(queryBuilder);
		return lTypedQuery;
	}

	@Transactional
	public void save(T entity) {
		em.persist(entity);
		em.flush();
	}

	public void saveAll(Collection<T> listEntities) {
		for (T entity : listEntities) {
			if (entity.getId() == null) {
				em.persist(entity);
			} else {
				em.merge(entity);
			}
		}
	}

	public void deleteAll(List<T> listEntities) {
		for (T entity : listEntities) {
			if (entity.getId() != null) {
				T merge = em.merge(entity);
				em.remove(merge);
			}
		}
		em.flush();
	}

}
