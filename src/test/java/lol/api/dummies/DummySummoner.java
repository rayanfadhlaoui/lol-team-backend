package lol.api.dummies;

import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

public class DummySummoner extends Summoner {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2793569003618654121L;
	private final long mockId;
	private String mockSummonerName;

	public DummySummoner(long id, String summonerName) {
		mockId = id;
		mockSummonerName = summonerName;
	}
	
	@Override
	public long getId() {
		return mockId;
	}
	
	@Override
	public String getName(){
		return mockSummonerName;
	}
	
	@Override
	public long getAccountId() {
		return mockId;
	}
}
