package com.lolteam.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lolteam.entities.general.SummonerEntity;
import com.lolteam.entities.match.MatchEntity;
import com.lolteam.utils.NumberConvertor;

@Repository
public class MatchEntityDao extends GenericDao<MatchEntity>{

	@Override
	protected Class<MatchEntity> getClassType() {
		return MatchEntity.class;
	}

	/** Returns the number of games imported for a given summoner.
	 * 
	 * @param summoner 
	 * 		The summoner.
	 * @return The number of games imported for a given summoner.*/
	public int getNbGamesImported(SummonerEntity summoner) {
		long nbGamesImported = em.createNamedQuery("match.getNbGamesImported", Long.class)
				.setParameter("summonerId", summoner.getId())
				.getSingleResult();
		return NumberConvertor.longToInt(nbGamesImported);		
	}

	/**Returns a list of the games ids imported for a given summoner.
	 * @param summoner 
	 * 		The summoner.
	 * @return A list of the games ids imported for a given summoner.*/
	public List<Long> getGameIdImported(SummonerEntity summoner) {
			return em.createNamedQuery("match.getGameIdImported", Long.class)
			.setParameter("summonerId", summoner.getId())
			.getResultList();
	}

}
