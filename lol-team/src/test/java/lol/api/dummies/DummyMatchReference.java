package lol.api.dummies;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;

public class DummyMatchReference extends MatchReference{

	/**
	 * 
	 */
	private static final long serialVersionUID = 629346588009364614L;
	
	private final long mockGameId;

	public DummyMatchReference(long gameId) {
		this.mockGameId = gameId;
	}
	
	@Override
	public long getGameId() {
		return mockGameId;
	}
	
	
}
