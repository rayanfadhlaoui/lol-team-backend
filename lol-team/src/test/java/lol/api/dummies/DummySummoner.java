package lol.api.dummies;

import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

public class DummySummoner extends Summoner {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2793569003618654121L;
	private final long mockId;

	public DummySummoner(long id) {
		mockId = id;
	}
	
	@Override
	public long getId() {
		return mockId;
	}
	
	@Override
	public long getAccountId() {
		return mockId;
	}
}
