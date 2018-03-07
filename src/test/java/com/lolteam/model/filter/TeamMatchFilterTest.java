package com.lolteam.model.filter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.lolteam.api.factory.LolApiFactory;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
public class TeamMatchFilterTest {

	private TeamMatchFilter teamMatchFilter;
	private LolApiFactory factory;

	@BeforeAll
	public void setUp() {
		teamMatchFilter = new TeamMatchFilter();
		factory = new LolApiFactory();
	}

	/** Assert the list of shared matches is empty if none in common */
	@Test
	@DisplayName("Test teamMatchFilter returns an empty list")
	public void noSharedMatches() {
		List<MatchReference> matchReferences = createMatchReferences(1, 3);


		List<Long> sharedMatches = teamMatchFilter.groupForTeam(matchReferences, 5);
		assertTrue(sharedMatches.isEmpty());
	}
	
	/** Assert the list of shared matches contains one element and test its value */
	@Test
	@DisplayName("Test teamMatchFilter returns one element")
	public void oneSharedMatche() {
		List<MatchReference> matchReferences = createMatchReferences(1, 3);
		matchReferences.addAll(createMatchReferences(2, 5));
		matchReferences.addAll(createMatchReferences(3, 4));

		List<Long> sharedMatches = teamMatchFilter.groupForTeam(matchReferences, 5);
		assertEquals(1, sharedMatches.size());
		int actualMatchId = sharedMatches.get(0).intValue();
		assertEquals(2, actualMatchId);
	}

	private List<MatchReference> createMatchReferences(int gameId, int numberOfResults) {
		return IntStream.range(1, numberOfResults + 1)
		                .mapToObj(it -> factory.createMatchReference(gameId))
		                .collect(Collectors.toList());
	}

}
