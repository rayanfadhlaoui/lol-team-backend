package com.lolteam.functionalities.myteam.components;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lolteam.entities.LolTeamUserEntity;
import com.lolteam.entities.SummonerEntity;
import com.lolteam.entities.TeamEntity;
import com.lolteam.entities.treatment.GamesToImportEntity;
import com.lolteam.entities.treatment.ImportStatus;
import com.lolteam.functionalities.myteam.data.SummonerWithUserIdData;
import com.lolteam.functionalities.myteam.data.TeamWithSummonerNameData;
import com.lolteam.services.GamesToImportService;
import com.lolteam.services.MatchEntityService;
import com.lolteam.services.SummonerService;
import com.lolteam.services.TeamService;
import com.lolteam.services.riotApi.RiotApiService;

import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;

@RestController
@RequestMapping(value = "/myTeam")
public class MyTeamControler {

	@Autowired
	private TeamService teamService;

	@Autowired
	private SummonerService summonerService;

	@Autowired
	private MatchEntityService matchEntityService;

	@Autowired
	private RiotApiService riotApiService;

	@Autowired
	private GamesToImportService gamesToImportService;

	@RequestMapping(value = "/findTeam", method = RequestMethod.POST)
	public TeamEntity findTeam(@RequestBody Long userId) {
		return teamService.findTeamByUserId(userId).orElse(null);
	}

	@RequestMapping(value = "/getNbGamesImported", method = RequestMethod.POST)
	public Integer getNbGamesImported(@RequestBody SummonerEntity summoner) {
		int totalImportedGames = matchEntityService.getNbGamesImported(summoner);
		if (summoner.getTotalImportedGames() == null || totalImportedGames != summoner.getTotalImportedGames()) {
			summoner.setTotalImportedGames(totalImportedGames);
			summonerService.save(summoner);
		}
		return totalImportedGames;
	}

	@Transactional
	@RequestMapping(value = "/addSummonerToTeam", method = RequestMethod.POST)
	public TeamEntity addSummonerToTeam(@RequestBody TeamWithSummonerNameData teamWithSummonerNameData) {
		System.out.println("add" +teamWithSummonerNameData.getSummonerName());
		Optional<SummonerEntity> optionalSummoner = summonerService.smartLoadSummoner(teamWithSummonerNameData.getSummonerName());
		if (!optionalSummoner.isPresent()) {
			return null;
		}

		teamWithSummonerNameData.getTeam().addSummoner(optionalSummoner.get());
		teamService.save(teamWithSummonerNameData.getTeam());
		return teamWithSummonerNameData.getTeam();
	}

	/**
	 * Import recent games of a summoner based on its last update.
	 * 
	 * @param summonerEntity
	 *            The summoner.
	 * @return The summoner with the current date as its last update.
	 */
	@RequestMapping(value = "/importGames", method = RequestMethod.POST)
	public SummonerEntity importGames(@RequestBody SummonerWithUserIdData summonerWithUserIdData) {

		SummonerEntity summonerEntity = summonerWithUserIdData.summoner;

		final List<Long> gamesIdToImport = findGamesIdToImport(summonerEntity);

		summonerEntity.setTotalGames(gamesIdToImport.size());
		summonerEntity.setLastUpdate(LocalDate.now());

		new Thread(() -> saveGamesToImport(summonerWithUserIdData, summonerEntity, gamesIdToImport)).start();
		return summonerEntity;
	}

	private List<Long> findGamesIdToImport(SummonerEntity summonerEntity) {

		List<Long> gameIdImported = matchEntityService.getGameIdImported(summonerEntity);
		Builder<List<MatchReference>> streamBuilder = Stream.builder();
		LocalDate lastUpdate = summonerEntity.getLastUpdate();
		int startIndex = 0;
		int endIndex = 100;
		long beginTime = lastUpdate != null ? lastUpdate.toEpochDay() : -1;

		while (startIndex != endIndex) {
			MatchList matchList = riotApiService.getMatchListByAccountId(summonerEntity.getAccountId(), beginTime, startIndex, endIndex);
			streamBuilder.accept(matchList.getMatches());
			startIndex = matchList.getStartIndex() + 100;
			endIndex = matchList.getEndIndex() + 100;
		}

		return streamBuilder.build()
							.flatMap(List::stream)
							.map(MatchReference::getGameId)
							.filter(id -> !gameIdImported.contains(id))
							.collect(Collectors.toList());
	}

	private synchronized void saveGamesToImport(SummonerWithUserIdData summonerWithUserIdData, SummonerEntity summonerEntity, final List<Long> gamesIdToImport) {
		summonerService.save(summonerEntity);
		LolTeamUserEntity user = new LolTeamUserEntity();
		user.setId(summonerWithUserIdData.userId);

		gamesIdToImport.stream().map(gameId -> {
			GamesToImportEntity gamesToImportEntity = new GamesToImportEntity();
			gamesToImportEntity.setMatchId(gameId);
			gamesToImportEntity.setImportStatus(ImportStatus.WAITING);
			gamesToImportEntity.setLolTeamUserEntity(user);
			return gamesToImportEntity;
		}).forEach(gamesToImportService::saveGameToImport);
	}

}
