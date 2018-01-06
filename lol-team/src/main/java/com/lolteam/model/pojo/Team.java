package com.lolteam.model.pojo;

import java.util.List;

import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

public class Team {
	List<Summoner> summoners;
	
	public Team(List<Summoner> summoners) {
		this.summoners = summoners;
	}

}
