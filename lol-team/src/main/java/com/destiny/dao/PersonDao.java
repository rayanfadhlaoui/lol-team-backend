package com.destiny.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.destiny.entities.general.Person;

@Repository
public class PersonDao extends GenericDao<Person>{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected Class<Person> getClassType() {
		return Person.class;
	}
	
	public List<Person> rechercherPerson() {
		final CriteriaBuilder lCriteriaBuilder = entityManager.getCriteriaBuilder();

		final CriteriaQuery<Person> lCriteriaQuery = lCriteriaBuilder.createQuery(Person.class);
		final Root<Person> lRoot = lCriteriaQuery.from(Person.class);
		lCriteriaQuery.select(lRoot);
		final TypedQuery<Person> lTypedQuery = entityManager.createQuery(lCriteriaQuery);

		return lTypedQuery.getResultList();
	}


}
