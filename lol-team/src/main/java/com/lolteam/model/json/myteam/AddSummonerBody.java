package com.lolteam.model.json.myteam;

import com.lolteam.entities.TeamEntity;

public class AddSummonerBody {
	private TeamEntity team;
	private String summonerName;
	
	public AddSummonerBody() {
		
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