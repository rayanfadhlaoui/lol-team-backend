package com.lolteam.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolteam.dao.MatchEntityDao;
import com.lolteam.entities.general.SummonerEntity;
import com.lolteam.entities.match.MatchEntity;

@Service("MatchEntityBean")
public class MatchEntityService {

	@Autowired
	MatchEntityDao matchEntityDao;
	
	@Transactional
	public void save(MatchEntity matchEntity) {
		matchEntityDao.save(matchEntity);
	}
	
	public void saveAll(List<MatchEntity> matchEntities) {
		matchEntityDao.saveAll(matchEntities);
	}

	public int getNbGamesImported(SummonerEntity summoner) {
		return matchEntityDao.getNbGamesImported(summoner);
	}

	public List<Long> getGameIdImported(SummonerEntity summonerEntity) {
		return matchEntityDao.getGameIdImported(summonerEntity);
	}

}
