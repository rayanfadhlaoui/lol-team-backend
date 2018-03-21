package com.lolteam.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolteam.dao.TeamDao;
import com.lolteam.entities.general.TeamEntity;

@Service
public class TeamService {

	@Autowired
	TeamDao teamDao;
	
	/** 
	 * Returns an optional {@link TeamEntity} associated to a given user id.
	 * The optional will be empty if more than one team or no team at all are associated to the parameters 
	 * 
	 * @param userId 
	 * 		The user id
	 * 
	 * @return An optional on a team
	 */
	public Optional<TeamEntity> findTeamByUserId(Long userId) {
		return teamDao.findTeamByUserId(userId);
	}


	/** 
	 * save the team in database.
	 * @param team 
	 * 		The team to save.
	 */
	public void save(TeamEntity team) {
		teamDao.save(team);
	}

}
