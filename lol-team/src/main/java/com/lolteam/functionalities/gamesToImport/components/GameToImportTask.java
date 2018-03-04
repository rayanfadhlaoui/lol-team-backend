package com.lolteam.functionalities.gamesToImport.components;

import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lolteam.entities.treatment.GamesToImportEntity;
import com.lolteam.functionalities.gamesToImport.matchImporter.MatchEntityImporter;
import com.lolteam.services.ChampionService;
import com.lolteam.services.GamesToImportService;
import com.lolteam.services.MatchEntityService;
import com.lolteam.services.SummonerService;
import com.lolteam.services.riotApi.RiotApiService;

@Component
public class GameToImportTask {

	@Autowired
	private MatchEntityService matchEntityService;
	
	@Autowired
	private SummonerService summonerService;

	@Autowired
	private RiotApiService riotApiService;

	@Autowired
	private ChampionService championService;

	@Autowired
	private GamesToImportService gamesToImportService;

	private MatchEntityImporter matchEntityImporter;
	
	@PostConstruct
	public void init() {
		matchEntityImporter = new MatchEntityImporter(riotApiService, summonerService, championService, matchEntityService);
	}

	@Transactional
    @Scheduled(fixedDelay = 100)
    synchronized public void importGames() {
    	List<GamesToImportEntity> gamesToImport = gamesToImportService.findGamesToImport(5);

    	if(gamesToImport.isEmpty()){
    		try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	gamesToImport.stream()
		.map(matchEntityImporter::importMatchEntity)
		.filter(Objects::nonNull)
		.forEach(matchEntityService::save);
    	gamesToImportService.saveAll(gamesToImport);
    }
    
}
