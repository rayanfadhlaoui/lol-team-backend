package com.lolteam.controllers;

import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;
import org.springframework.web.bind.annotation.RestController;

import com.lolteam.services.RiotApiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(value = "/testApiLol")
public class ApiLolSimulatorControler {
	
	@Autowired
	private RiotApiService riotApiService;

	@RequestMapping(value = "/firstTest", method = RequestMethod.GET)
	public @ResponseBody String purge() {
		try {
			Summoner summoner = riotApiService.getSummonerByName(Platform.EUW, "Blooday94");
			return new StringBuilder()
					.append("Name: " + summoner.getName()).append("\n")
					.append("Summoner ID: " + summoner.getId()).append("\n")
					.append("Account ID: " + summoner.getAccountId()).append("\n")
					.append("Summoner Level: " + summoner.getSummonerLevel()).append("\n")
					.append("Profile Icon ID: " + summoner.getProfileIconId()).append("\n")
					.toString();
		} catch (RiotApiException e) {
			System.out.println(e.getMessage());
			return "Error" + e.getMessage();
		}
	}

}