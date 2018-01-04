package com.lolteam.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

@RestController
@RequestMapping(value = "/testApiLol")
public class ApiLolSimulatorControler {

	@RequestMapping(value = "/firstTest", method = RequestMethod.GET)
	public @ResponseBody String purge() {
		ApiConfig config = new ApiConfig().setKey("RGAPI-a30f24f1-155b-436b-93f0-648d251bf20a");
		RiotApi api = new RiotApi(config);

		try {
			Summoner summoner = api.getSummonerByName(Platform.EUW, "Blooday94");
			return new StringBuilder()
					.append("Name: " + summoner.getName()).append("\n")
					.append("Summoner ID: " + summoner.getId()).append("\n")
					.append("Account ID: " + summoner.getAccountId()).append("\n")
					.append("Summoner Level: " + summoner.getSummonerLevel()).append("\n")
					.append("Profile Icon ID: " + summoner.getProfileIconId()).append("\n")
					.toString();
		} catch (RiotApiException e) {
			e.printStackTrace();
			return "Error" + e.getMessage();
		}
	}

}