package com.lolteam.model.filter;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;

public class TeamMatchFilter {
	
	/**
	 * Group a list of matches by team.</BR>
	 * Only keep the ones played by the hole team.
	 * @param matchRefenrences
	 *            A list composed by all games played for each summoner.
	 * 
	 * @return List of matches ids played by all summoners
	 */
	public List<Long> groupForTeam(List<MatchReference> matchRefenrences, int nbSummoners) {
		Predicate<Long> countPredicate = new CountPredicate<Long>(nbSummoners);
		
		return matchRefenrences
				.stream()
				.map(matchRef -> matchRef.getGameId())
				.sorted()
				.filter(countPredicate)
				.collect(Collectors.toList());
	}
	
}