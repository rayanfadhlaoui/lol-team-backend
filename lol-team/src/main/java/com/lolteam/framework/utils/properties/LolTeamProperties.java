package com.lolteam.framework.utils.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

public class LolTeamProperties {

	@Autowired
	static ServletContext context;
	private static Properties appProps;

	static {
		appProps = new Properties();
		try {
			URL resource = LolTeamProperties.class.getResource("lolTeam.properties");
			appProps.load(new FileInputStream(resource.getPath()));
		} catch (IOException e) {
			// TODO LOGGER
		}
	}

	public static String getPropertity(String key) {
		return appProps.getProperty(key);
	}

}
