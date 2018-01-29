package com.lolteam.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolteam.dao.TeamDao;
import com.lolteam.entities.TeamEntity;

@Service
public class TeamService {

	@Autowired
	TeamDao teamDao;
	
	public Optional<TeamEntity> findTeamByUserId(Long userId) {
		return teamDao.findTeamByUserId(userId);
	}

	public void save(TeamEntity team) {
		teamDao.save(team);
	}

}
