package com.destiny.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.destiny.entities.fight.Town;

@Repository
public class TownDao {
	@PersistenceContext
	private EntityManager entityManager;

	public Town getTown(String name) {

		TypedQuery<Town> query = entityManager.createQuery("select town from Town town where town.name = :name", Town.class)
				.setParameter("name", name);
		return query.getSingleResult();
	}
}
