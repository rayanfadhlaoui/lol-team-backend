package com.lolteam.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolteam.dao.SettingsDao;

@Service
public class SettingService {
	
	@Autowired
	SettingsDao settingsDao;
	
	public String getRiotKey() {
		return settingsDao.getRiotKey();
	}
}
