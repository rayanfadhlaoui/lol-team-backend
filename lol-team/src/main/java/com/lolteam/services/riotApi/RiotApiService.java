package com.lolteam.services.riotApi;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import javax.naming.LimitExceededException;

import org.springframework.stereotype.Service;

import com.lolteam.framework.utils.properties.LolTeamProperties;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.static_data.dto.Champion;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.api.request.ratelimit.RateLimitException;
import net.rithms.riot.api.endpoints.match.dto.Match;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.match.dto.MatchTimeline;
import net.rithms.riot.constant.Platform;

@Service
public class RiotApiService extends RiotApi {
	private static final String LOL_AOI_KEY = "key";
	private RiotApiHandle riotApiHandle = new RiotApiHandle();
	private int nbCall = 0;

	public RiotApiService() {
		super(new ApiConfig().setKey(LolTeamProperties.getPropertity(LOL_AOI_KEY)));
	}

	// public Optional<MatchTimeline> getTimelineByMatchId(long matchId) {
	// MatchTimeline matchTimeline = riotApiHandle.execute(() ->
	// getTimelineByMatchId(Platform.EUW, matchId));
	// return Optional.ofNullable(matchTimeline);
	// }

	public Optional<Match> getMatch(long matchId) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		nbCall++;
		try {
			return Optional.of(getMatch(Platform.EUW, matchId));
		} catch (RiotApiException e) {
			return handleRiotApiException(e, () -> getMatch(matchId));
		}
	}

	public Optional<Champion> getChampion(int championId) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		nbCall++;
		try {
			return Optional.of(getDataChampion(Platform.EUW, championId));
		} catch (RiotApiException e) {
			return handleRiotApiException(e, () -> getChampion(championId));
		}
	}

	public Optional<Summoner> getSummonerByAccountId(long accountId) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		nbCall++;
		try {
			return Optional.of(getSummonerByAccount(Platform.EUW, accountId));
		} catch (RiotApiException e) {
			return handleRiotApiException(e, () -> getSummonerByAccountId(accountId));
		}
	}

	// TODO FINISH THE JAVADOC
	/**
	 * @return return an empty list if a error occur
	 */
	public List<MatchReference> findMatchReferenceListForSummoner(Summoner summoner) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		nbCall++;
		try {
			return getMatchListByAccountId(Platform.EUW, summoner.getAccountId()).getMatches();
		} catch (RiotApiException e) {
			return handleRiotApiException(e, () -> findMatchReferenceListForSummoner(summoner));
		}
	}

	public Optional<Summoner> getSummonerByName(String summonerName) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		nbCall++;
		try {
			return Optional.of(getSummonerByName(Platform.EUW, summonerName));
		} catch (RiotApiException e) {
			return handleRiotApiException(e, () -> getSummonerByName(summonerName));
		}
	}

	//todo refacto
	private <T> T handleRiotApiException(RiotApiException e, Supplier<T> supplier) {
		if(e instanceof RateLimitException) {
			RateLimitException rateExeption = (RateLimitException) e;
			System.out.println("nb call -> " + nbCall);
			System.out.println("Need to reconnect after "+rateExeption.getRetryAfter()+  " s");
			try {
				Thread.sleep(rateExeption.getRetryAfter()* 1000);
				nbCall = 0;
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			return supplier.get();
		}
		e.printStackTrace();
		return null;
	}
}
