package com.lolteam.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lolteam.entities.SummonerEntity;
import com.lolteam.entities.match.MatchEntity;
import com.lolteam.utils.NumberConvertor;

@Repository
public class MatchEntityDao extends GenericDao<MatchEntity>{

	@Override
	protected Class<MatchEntity> getClassType() {
		return MatchEntity.class;
	}

	public int getNbGamesImported(SummonerEntity summoner) {
		long nbGamesImported = em.createNamedQuery("match.getNbGamesImported", Long.class)
				.setParameter("summonerId", summoner.getId())
				.getSingleResult();
		return NumberConvertor.longToInt(nbGamesImported);		
	}

	public List<Long> getGameIdImported(SummonerEntity summonerEntity) {
			return em.createNamedQuery("match.getGameIdImported", Long.class)
			.setParameter("summonerId", summonerEntity.getId())
			.getResultList();
	}

}
