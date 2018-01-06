package com.lolteam.riot.api;

import com.lolteam.framework.utils.properties.LolTeamProperties;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;

public class RiotApiBuilder {
	//TODO mettre dans un fichier properties
	private static final String KEY = "key";

	public static RiotApi get() {
		ApiConfig apiConfig = new ApiConfig().setKey(LolTeamProperties.getPropertity(KEY));
		
		return new RiotApi(apiConfig);
	}
}
