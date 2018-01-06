package lol.api.factory;

import lol.api.dummies.DummyMatchReference;
import lol.api.dummies.DummySummoner;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

public class LolApiFactory {
	
	public Summoner createSummoner(long id) {
		return new DummySummoner(id);
	}
	
	public MatchReference createMatchReference(Integer gameId) {
		return new DummyMatchReference(gameId);
	}
}
