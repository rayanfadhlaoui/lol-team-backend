package com.destiny.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.destiny.entities.general.Name;

@Repository
public class NameDao {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Name> getAllLastNames() {

		TypedQuery<Name> query = entityManager.createQuery("select name from Name name where name.type = 'last name'",
				Name.class);

		return query.getResultList();
	}

	public List<Name> getAllFirstNames() {
		final CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		final CriteriaQuery<Name> query = cb.createQuery(Name.class);

		final Root<Name> rootName = query.from(Name.class);
		query.select(rootName).where(cb.equal(rootName.get("type"), "first name"));

		return entityManager.createQuery(query).getResultList();
	}

	@Transactional
	public void save(Name name) {
		entityManager.merge(name);
		entityManager.flush();
	}

	public List<Name> getFirstNamesByOrigine(String origine) {
		TypedQuery<Name> query = entityManager.createQuery("select name from Name name where name.type = 'first name' AND name.origine = :origine", Name.class)
				.setParameter("origine", origine);
	    return query.getResultList();
	}
}
