package lol.api.factory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lol.api.dummies.DummyMatchList;
import lol.api.dummies.DummyMatchReference;
import lol.api.dummies.DummySummoner;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

public class LolApiFactory {
	
	@Deprecated
	public Summoner createSummoner(long id) {
		return createSummoner(id, "No Name Provided");
	}
	
	public Summoner createSummoner(Long id, String summonerName) {
		return new DummySummoner(id, summonerName);
	}
	
	public MatchReference createMatchReference(Integer gameId) {
		return new DummyMatchReference(gameId);
	}

	public MatchList createMatchList(Integer ... ids) {
		List<MatchReference> matchReferences = Stream.of(ids)
				.map(DummyMatchReference::new)
				.collect(Collectors.toList());
		return new DummyMatchList(matchReferences);
	}



	
}
