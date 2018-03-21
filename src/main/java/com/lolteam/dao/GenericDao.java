package com.lolteam.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.lolteam.entities.general.GenericEntity;

public abstract class GenericDao<T extends GenericEntity> {

	@PersistenceContext
	protected EntityManager em;

	/**
	 * Returns the classType of the entity associated to the DAO.
	 * @return The Class of the entity associated to the DAO.
	 * */
	protected abstract Class<T> getClassType();

	// TODO CAN BE AUTOMATIC
	// private Class<T> test(){
	// Class<?> class1 = getClass();
	// Type[] actualTypeArguments = ((ParameterizedType)
	// class1.getGenericSuperclass()).getActualTypeArguments();
	// return (Class<T>) actualTypeArguments[0];
	// }

	/** 
	 * Returns a list of all the entities in the database.<br/>
	 * <b>Careful, if the database contains too many entities, this can cause a OutOfMemoryException</b>
	 *  
	 * @return A list of all the entities.
	 */
	//todo add test
	public List<T> getAll() {
		return getTypedQueryAll().getResultList();
	}

	/** 
	 * Returns a list of entities with no specific filter.
	 * The maximum size of the list depends on the parameter maxResult
	 * 
	 * @param maxResult 
	 * 		Maximum rows returned by the method.
	 *  
	 * @return A list of all the entities.
	 */
	public List<T> getAll(int maxResult) {
		return getTypedQueryAll()
				.setMaxResults(maxResult)
				.getResultList();
	}

	/** 
	 * Returns the entity associated to the parameter id
	 * 
	 * @param maxResult 
	 * 		Maximum rows returned by the method.
	 *  
	 * @param id 
	 *  	The id.
	 *  
	 * @return The entity associated to an id.
	 */
	//todo handle possible exceptions
	public T get(int id) {
		Class<T> classType = getClassType();

		String sqlRequest = "select t " + "from " + classType.getSimpleName() + " t " + "where t.id = :id";
		System.out.println(sqlRequest);
		TypedQuery<T> query = em.createQuery(sqlRequest, classType).setParameter("id", id);
		return query.getSingleResult();
	}

	/** 
	 * Save the entity.
	 * 
	 * @param entity 
	 * 		The entity to save.
	 *  
	 */
	//todo remove the Transactional annotation
	@Transactional
	public void save(T entity) {
		if (entity.getId() == null) {
			em.persist(entity);
		} else {
			em.merge(entity);
		}
		em.flush();
	}

	/** 
	 * Save all the entities.
	 * 
	 * @param listEntities 
	 * 		The entities to save.
	 *  
	 */
	public void saveAll(Collection<T> listEntities) {
		for (T entity : listEntities) {
			if (entity.getId() == null) {
				em.persist(entity);
			} else {
				em.merge(entity);
			}
		}
	}

	/** 
	 * delete all the entities.
	 * 
	 * @param listEntities 
	 * 		The entities to delete.
	 *  
	 */
	public void deleteAll(List<T> listEntities) {
		for (T entity : listEntities) {
			if (entity.getId() != null) {
				T merge = em.merge(entity);
				em.remove(merge);
			}
		}
		em.flush();
	}

	/** 
	 * Returns a { {@link TypeQuery} able to fetch all the entities.
	 * 
	 * @return Returns a { {@link TypeQuery} able to fetch all the entities..
	 *  
	 */
	private TypedQuery<T> getTypedQueryAll() {
		final CriteriaBuilder lCriteriaBuilder = em.getCriteriaBuilder();
		Class<T> classType = getClassType();
		final CriteriaQuery<T> queryBuilder = lCriteriaBuilder.createQuery(classType);
		final Root<T> lRoot = queryBuilder.from(classType);
		queryBuilder.select(lRoot).where();
		return em.createQuery(queryBuilder);
	}

}
