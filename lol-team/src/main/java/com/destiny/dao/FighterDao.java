package com.destiny.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.destiny.entities.fight.Fighter;
import com.destiny.entities.fight.TitleEnum;

@Repository
public class FighterDao extends GenericDao<Fighter> {

	@Override
	protected Class<Fighter> getClassType() {
		return Fighter.class;
	}

	public List<Fighter> getAllFighter() {
		final CriteriaBuilder lCriteriaBuilder = em.getCriteriaBuilder();

		final CriteriaQuery<Fighter> lCriteriaQuery = lCriteriaBuilder.createQuery(Fighter.class);
		final Root<Fighter> lRoot = lCriteriaQuery.from(Fighter.class);
		lCriteriaQuery.select(lRoot);
		final TypedQuery<Fighter> lTypedQuery = em.createQuery(lCriteriaQuery);

		return lTypedQuery.getResultList();
	}

	//TODO Enum for race
	public List<Fighter> getFighterByRace(String race) {
		return em.createNamedQuery("fighter.getFighterByRace", Fighter.class)
				.setParameter("race", race)
				.getResultList();
	}
	
	@Transactional
	public void purgeDeadFighters() {
		System.out.println("start purge");
		List<Fighter> deadFighters = em.createQuery("select f from Fighter f where f.state.injury.name ='Death'", Fighter.class).getResultList();
		System.out.println(deadFighters.size() + " fighters to purge");
		deleteAll(deadFighters);

		System.out.println("end purge");
	}

	public int getNumberFighterByRace(String race) {
		return ((Long) em.createNamedQuery("fighter.getFighterByRace.count")
				.setParameter("race", race)
				.getSingleResult())
				.intValue();
	}


	public List<Fighter> getFighterByRaceAndClasse(String race, TitleEnum title) {
		return em.createNamedQuery("fighter.getFighterByRaceAndClasse", Fighter.class)
		.setParameter("race", race)
		.setParameter("title", title)
		.getResultList();
	}

}
