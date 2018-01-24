package com.lolteam.services;

import java.util.Optional;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolteam.dao.SummonerDao;
import com.lolteam.entities.SummonerEntity;
import com.lolteam.services.riotApi.RiotApiService;

import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

@Service
public class SummonerService {
	
	@Autowired
	private SummonerDao summonerDao;
	
	@Autowired
	private RiotApiService riotApiService;
		
	@Transactional
	public Optional<SummonerEntity> smartLoadSummoner(long accountId) {
		Supplier<SummonerEntity> summonerSupplier = () -> {
			Summoner summoner = riotApiService.getSummonerByAccountId(accountId).orElse(null);
			if(summoner != null) {
				SummonerEntity summonerEntity = new SummonerEntity();
				summonerEntity.setAccountId(summoner.getAccountId());
				summonerEntity.setName(summoner.getName());
				summonerDao.save(summonerEntity);
				return summonerEntity;
			}
			return null;
		};
		
		SummonerEntity summonerEntity = summonerDao.getSummonerEntityByAccountId(accountId)
				.orElseGet(summonerSupplier);
		return Optional.ofNullable(summonerEntity);
	}
}
