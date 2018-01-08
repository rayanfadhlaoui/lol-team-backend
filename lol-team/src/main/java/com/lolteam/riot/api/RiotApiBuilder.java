package com.lolteam.riot.api;

import com.lolteam.framework.utils.properties.LolTeamProperties;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;

public class RiotApiBuilder {
	private static final String LOL_AOI_KEY = "key";

	public static RiotApi get() {
		ApiConfig apiConfig = new ApiConfig().setKey(LolTeamProperties.getPropertity(LOL_AOI_KEY));
		
		return new RiotApi(apiConfig);
	}
}
