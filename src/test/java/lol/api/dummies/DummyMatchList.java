package lol.api.dummies;

import java.util.List;

import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;

public class DummyMatchList extends MatchList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4282526936566801384L;
	
	private List<MatchReference> matchReferences;

	public DummyMatchList(List<MatchReference> matchReferences) {
		this.matchReferences = matchReferences;
	}
	
	@Override
	public List<MatchReference> getMatches() {
		return matchReferences;
	}

}
