package com.lolteam.functionalities.myteam.data;

import com.lolteam.entities.TeamEntity;

public class TeamWithSummonerNameData {
	private TeamEntity team;
	private String summonerName;
	
	public TeamWithSummonerNameData() {
		
	}

	public TeamEntity getTeam() {
		return team;
	}

	public void setTeam(TeamEntity team) {
		this.team = team;
	}

	public String getSummonerName() {
		return summonerName;
	}

	public void setSummonerName(String summonerName) {
		this.summonerName = summonerName;
	}
}