package com.lolteam.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lolteam.riot.api.RiotApiBuilder;

import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

@RestController
@RequestMapping(value = "/TeamSynchronizer")
public class TeamStatusSynchronizerController {

	// TODO persit into database
	private static final List<String> summonerNames = Arrays.asList("Dremsy", "0Kordan0", "Victoriusss", "Luidji94", "Wasagreen");
	private final RiotApi api;

	public TeamStatusSynchronizerController() {
		this.api = RiotApiBuilder.get();
	}

	@RequestMapping(value = "/getTeamSummoners", method = RequestMethod.GET)
	public List<Summoner> getTeamSummoners() {
		SummonerNameConsumer summonerNameConsumer = new SummonerNameConsumer();
		summonerNames.forEach(summonerNameConsumer);
		return summonerNameConsumer.getSummoners();
	}

	
	//TODO simple test
	@RequestMapping(value = "/getAllMatches", method = RequestMethod.GET)
	public Map<Summoner, List<MatchReference>> getAllMatches() {
		List<Summoner> summoners = getTeamSummoners();
		Map<Summoner, List<MatchReference>> matchReferenceBySummoner = new HashMap<>();
		summoners.forEach(summoner -> {
			MatchList matchList;
			try {
				matchList = api.getMatchListByAccountId(Platform.EUW, summoner.getAccountId());
				matchList.getMatches()
				         .forEach(matchReference -> {
					         matchReferenceBySummoner.putIfAbsent(summoner, new ArrayList<>());
					         matchReferenceBySummoner.compute(summoner, (key, matchReferences) -> {
						         matchReferences.add(matchReference);
						         return matchReferences;
					         });
				         });
			} catch (RiotApiException e) {
			}
		});

		return matchReferenceBySummoner;
	}
	
	class SummonerNameConsumer implements Consumer<String> {

		private final List<Summoner> summoners;

		public SummonerNameConsumer() {
			summoners = new ArrayList<>();
		}

		@Override
		public void accept(String summonerName) {
			Summoner summoner;
			try {
				summoner = api.getSummonerByName(Platform.EUW, summonerName);
			} catch (RiotApiException e) {
				summoner = new Summoner();
			}
			summoners.add(summoner);
		}

		public List<Summoner> getSummoners() {
			return summoners;
		}
	}

}