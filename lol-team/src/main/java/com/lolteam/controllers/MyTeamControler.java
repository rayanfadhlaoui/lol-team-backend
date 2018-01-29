package com.lolteam.controllers;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lolteam.entities.SummonerEntity;
import com.lolteam.entities.TeamEntity;
import com.lolteam.model.json.myteam.AddSummonerBody;
import com.lolteam.services.SummonerService;
import com.lolteam.services.TeamService;

@RestController
@RequestMapping(value="/myTeam")
public class MyTeamControler {
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	SummonerService summonerService;

	@RequestMapping(value = "/findTeam", method = RequestMethod.POST)
	public TeamEntity create(@RequestBody  Long userId) {
		return teamService.findTeamByUserId(userId).orElse(null);
	}
	
	@Transactional
	@RequestMapping(value = "/addSummonerToTeam", method = RequestMethod.POST)
	public TeamEntity addSummonerToTeam(@RequestBody AddSummonerBody addSummonerBody) {
		System.out.println(addSummonerBody.getTeam().getName()+ "????" + addSummonerBody.getSummonerName());
		SummonerEntity summonerEntity = summonerService.smartLoadSummoner(addSummonerBody.getSummonerName())
				.orElse(null);
		if(summonerEntity == null) {
			return null;
		}
		
		addSummonerBody.getTeam().addSummoner(summonerEntity);
		System.out.println("try to persist team");
		teamService.save(addSummonerBody.getTeam());
		return addSummonerBody.getTeam();
	}
	
}
