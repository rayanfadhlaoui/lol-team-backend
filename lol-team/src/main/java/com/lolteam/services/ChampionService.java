package com.lolteam.services;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolteam.dao.ChampionDao;
import com.lolteam.entities.ChampionEntity;
import com.lolteam.services.riotApi.RiotApiService;

import net.rithms.riot.api.endpoints.static_data.dto.Champion;

@Service
public class ChampionService {
	
	@Autowired
	private ChampionDao championDao;
	
	@Autowired
	private RiotApiService riotApiService;
	
	public ChampionService() {
	}
		
	public Optional<ChampionEntity> smartLoadChampion(int championId) {
		Supplier<ChampionEntity> championSupplier = () -> {
			Champion champion = riotApiService.getChampion(championId).orElse(null);
			if(champion != null) {
				ChampionEntity championEntity = new ChampionEntity();
				championEntity.setChampionId(championId);
				championEntity.setName(champion.getName());
				return championEntity;
			}
			return null;
		};
		
		ChampionEntity championEntity = championDao.getChampionEntityByChampionId(championId)
				.orElseGet(championSupplier);
		return Optional.ofNullable(championEntity);
	}

}
