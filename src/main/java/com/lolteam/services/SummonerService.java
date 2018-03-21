package com.lolteam.services;

import java.util.Optional;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolteam.dao.SummonerDao;
import com.lolteam.entities.general.SummonerEntity;
import com.lolteam.framework.core.db.EntityCache;
import com.lolteam.services.riotApi.RiotApiService;

import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

@Service("summonerService")
public class SummonerService implements EntityService<Long, SummonerEntity>{

	@Autowired
	private SummonerDao summonerDao;

	@Autowired
	private RiotApiService riotApiService;


	/** 
	 *	Tries to load a summoner from the database, and if not present, load it from
	 *  the Riot API.
	 *  
	 *   @param accountId 
	 *   		The account id associated to the summoner
	 *   
	 *   @return An optional on a summoner.
	 */
	public Optional<SummonerEntity> smartLoadSummoner(long accountId) {

		Optional<SummonerEntity> optionalSummonerEntity = summonerDao.getSummonerEntityByAccountId(accountId);
		if(!optionalSummonerEntity.isPresent()) {
			optionalSummonerEntity = createAndSaveSummonerEntity(riotApiService.getSummonerByAccountId(accountId).get());
		}
		return optionalSummonerEntity;
	}

	/** 
	 *	Tries to load a summoner from the database, and if not present, load it from
	 *  the Riot API.
	 *  
	 *   @param summonerName 
	 *   		The summoner name.
	 *   
	 *   @return An optional on a summoner.
	 */
	public Optional<SummonerEntity> smartLoadSummoner(String summonerName) {
		Optional<SummonerEntity> optionalSummonerEntity = summonerDao.getSummonerEntityBySummonerName(summonerName);
		if(!optionalSummonerEntity.isPresent()) {
			optionalSummonerEntity = createAndSaveSummonerEntity(riotApiService.getSummonerByName(summonerName).get());
		}
		return optionalSummonerEntity;
	}

	/** 
	 * Save the summoner.
	 * 
	 * @param summoner 
	 * 		The summoner to save.
	 *  
	 */
	@Transactional
	public void save(SummonerEntity summoner) {
		summonerDao.save(summoner);
	}

	/** 
	 * Returns summoner based on its summonerId.
	 * 
	 * @param summonerId 
	 * 		The summoner id.
	 * 
	 * @return The summoner.
	 *  
	 */
	public SummonerEntity get(Integer summonerId) {
		return summonerDao.get(summonerId);
	}

	/** {@inheritDoc} */
	@Override
	public EntityCache<Long, SummonerEntity> getCache() {
		Function<Long, SummonerEntity> summonerLoaderFunction = accountId -> smartLoadSummoner(accountId)
																							.orElse(null);
		return new EntityCache<>(summonerLoaderFunction, (accountId, summoner) -> summoner.getAccountId() == accountId, this::save);
	}
	
	/** Creates and saves a summonerEntity from a s{@link Summoner}.
	 * 
	 * @param summoner 
	 * 		A summoner.
	 * 
	 * @return An optional on summonerEntity.<br/>
	 *  	   <b>Will be empty if summoner is null.</b>*/
	private Optional<SummonerEntity> createAndSaveSummonerEntity(Summoner summoner) {
		if(summoner != null) {
			SummonerEntity summonerEntity = new SummonerEntity();
			summonerEntity.setAccountId(summoner.getAccountId());
			summonerEntity.setName(summoner.getName());
			summonerDao.save(summonerEntity);
			return Optional.of(summonerEntity);
		}
		return Optional.empty();
	}
}
