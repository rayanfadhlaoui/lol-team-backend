package com.lolteam.model.filter;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.lolteam.entities.MatchEntity;
import com.lolteam.framework.factory.entities.MatchEntityFactory;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;

public class TeamMatchFilter {
	
	private static MatchEntityFactory matchEntityFactory;

	static {
		matchEntityFactory = new MatchEntityFactory();
	}

	/**
	 * Group a list of matches by team.</BR>
	 * Only keep the ones played by the hole team.
	 * @param matchRefenrences
	 *            A list composed by all games played for each summoner.
	 * 
	 * @return List of matches played by all five summoners
	 */
	public List<MatchEntity> groupForTeam(List<MatchReference> matchRefenrences) {
		Predicate<MatchReference> atLeastFivePredicate = new HasAtLeastFiveMatchRefPredicate();
		
		return matchRefenrences
				.stream()      
				.sorted((m1, m2) -> Long.compare(m1.getGameId(), m2.getGameId()))
				.filter(atLeastFivePredicate)
				.map(matchEntityFactory::createMatch)
				.collect(Collectors.toList());
	}
	
}