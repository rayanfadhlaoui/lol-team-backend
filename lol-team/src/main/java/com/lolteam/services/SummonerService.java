package com.lolteam.services;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolteam.dao.SummonerDao;
import com.lolteam.entities.SummonerEntity;
import com.lolteam.framework.core.db.EntityCache;
import com.lolteam.services.riotApi.RiotApiService;

import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

@Service("summonerService")
public class SummonerService implements EntityService<Long, SummonerEntity>{

	@Autowired
	private SummonerDao summonerDao;

	@Autowired
	private RiotApiService riotApiService;

	public Optional<SummonerEntity> smartLoadSummoner(long accountId) {
		Supplier<SummonerEntity> summonerSupplier = apiSummonerSupplier(() -> riotApiService.getSummonerByAccountId(accountId)
																							.orElse(null));

		SummonerEntity summonerEntity = summonerDao	.getSummonerEntityByAccountId(accountId)
													.orElseGet(summonerSupplier);
		return Optional.ofNullable(summonerEntity);
	}

	public Optional<SummonerEntity> smartLoadSummoner(String summonerName) {
		Supplier<SummonerEntity> summonerSupplier = apiSummonerSupplier(() -> riotApiService.getSummonerByName(summonerName)
																							.orElse(null));

		SummonerEntity summonerEntity = summonerDao	.getSummonerEntityBySummonerName(summonerName)
													.orElseGet(summonerSupplier);
		return Optional.ofNullable(summonerEntity);
	}

	private Supplier<SummonerEntity> apiSummonerSupplier(Supplier<Summoner> summonerSupplier) {
		Supplier<SummonerEntity> apiSummonerSupplier = () -> {
			Summoner summoner = summonerSupplier.get();
			if (summoner != null) {
				SummonerEntity summonerEntity = new SummonerEntity();
				summonerEntity.setAccountId(summoner.getAccountId());
				summonerEntity.setName(summoner.getName());
				summonerDao.save(summonerEntity);
				return summonerEntity;
			}
			return null;
		};
		return apiSummonerSupplier;
	}

	@Transactional
	public void save(SummonerEntity summonerEntity) {
		summonerDao.save(summonerEntity);
	}

	public SummonerEntity get(Integer summonerId) {
		return summonerDao.get(summonerId);
	}

	@Override
	public EntityCache<Long, SummonerEntity> getCache() {
		Function<Long, SummonerEntity> summonerLoaderFunction = accountId -> smartLoadSummoner(accountId)
																							.orElse(null);
		return new EntityCache<>(summonerLoaderFunction, (accountId, summoner) -> summoner.getAccountId() == accountId, this::save);
	}
}
