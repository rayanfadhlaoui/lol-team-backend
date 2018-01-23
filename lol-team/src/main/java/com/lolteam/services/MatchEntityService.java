package com.lolteam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolteam.dao.MatchEntityDao;
import com.lolteam.entities.MatchEntity;

@Service
public class MatchEntityService {

	@Autowired
	MatchEntityDao matchEntityDao;
	
	public void saveAll(List<MatchEntity> matchEntities) {
		matchEntityDao.saveAll(matchEntities);
	}

}
