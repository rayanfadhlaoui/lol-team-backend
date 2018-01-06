package com.lolteam.framework.utils.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LolTeamProperties {

	private static final String PROPERTY_FILE_PATH = "applicationProperties/lolTeam.properties";
	private static Properties appProps;

	static {
		appProps = new Properties();
		try {
			appProps.load(new FileInputStream(PROPERTY_FILE_PATH));
		} catch (IOException e) {
			// TODO LOGGER
		}
	}

	public static String getPropertity(String key) {
		return appProps.getProperty(key);
	}

}
