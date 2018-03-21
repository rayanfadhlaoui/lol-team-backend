package com.lolteam.dao;

import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lolteam.entities.general.ChampionEntity;

@Repository
public class ChampionDao extends GenericDao<ChampionEntity>{

	@Override
	protected Class<ChampionEntity> getClassType() {
		return ChampionEntity.class;
	}
	
	/** 
	 * Returns a optional champion from a champion id.
	 * The optional will be empty if more than one champion or no champion at all are associated to the championId 
	 * 
	 * @param championId 
	 * 		The championId
	 * 
	 * @return An optional on a ChampionId
	 */
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
