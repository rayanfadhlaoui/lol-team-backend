package com.lolteam.services;


import org.springframework.stereotype.Service;

import com.lolteam.framework.utils.properties.LolTeamProperties;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;

@Service
public class RiotApiService extends RiotApi {
	private static final String LOL_AOI_KEY = "key";

	public RiotApiService() {
		super(new ApiConfig().setKey(LolTeamProperties.getPropertity(LOL_AOI_KEY)));
	}

}
