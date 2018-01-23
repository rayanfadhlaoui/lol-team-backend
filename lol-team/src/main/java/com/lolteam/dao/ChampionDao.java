package com.lolteam.dao;

import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lolteam.entities.ChampionEntity;

@Repository
public class ChampionDao extends GenericDao<ChampionEntity>{

	@Override
	protected Class<ChampionEntity> getClassType() {
		return ChampionEntity.class;
	}
	
	public Optional<ChampionEntity> getChampionEntityByChampionId(int championId) {
		try {
			return Optional.of(em.createNamedQuery("champion.getChampionFromChampionId", ChampionEntity.class)
			                     .setParameter("championId", championId)
			                     .getSingleResult());
		} catch (NoResultException nre) {
		} catch (NonUniqueResultException nure) {
		}
		//TODO HANDLE EXCEPTION
		return Optional.empty();
	}

}
