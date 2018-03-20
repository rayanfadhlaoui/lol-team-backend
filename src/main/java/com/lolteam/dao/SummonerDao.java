package com.lolteam.dao;

import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lolteam.entities.SummonerEntity;

@Repository
public class SummonerDao extends GenericDao<SummonerEntity>{

	@Override
	protected Class<SummonerEntity> getClassType() {
		return SummonerEntity.class;
	}
	
	/** 
	 * Returns a optional {@link SummonerEntity} associated to the given account id.
	 * The optional will be empty if more than one summoners or no summoner at all are associated to the parameters 
	 * 
	 * @param accountId 
	 * 		The accountId
	 * 
	 * @return An optional on a summoner
	 */
	public Optional<SummonerEntity> getSummonerEntityByAccountId(long accountId) {
		try {
			return Optional.of(em.createNamedQuery("summoner.getSummonerEntityByAccountId", SummonerEntity.class)
			                     .setParameter("accountId", accountId)
			                     .getSingleResult());
		} catch (NoResultException nre) {
		} catch (NonUniqueResultException nure) {
		}
		//TODO HANDLE EXCEPTION
		return Optional.empty();
	}

	/** 
	 * Returns a optional {@link SummonerEntity} associated to the given summoner name.
	 * The optional will be empty if more than one summoners or no summoner at all are associated to the parameters 
	 * 
	 * @param summonerName 
	 * 		The summoner's name
	 * 
	 * @return An optional on a summoner
	 */
	public Optional<SummonerEntity> getSummonerEntityBySummonerName(String summonerName) {
		try {
			return Optional.of(em.createNamedQuery("summoner.getSummonerEntityBySummonerName", SummonerEntity.class)
			                     .setParameter("summonerName", summonerName.toLowerCase())
			                     .getSingleResult());
		} catch (NoResultException nre) {
		} catch (NonUniqueResultException nure) {
		}
		//TODO HANDLE EXCEPTION
		return Optional.empty();
	}

}
