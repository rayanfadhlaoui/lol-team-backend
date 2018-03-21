package com.lolteam.dao;

import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lolteam.entities.general.TeamEntity;

@Repository
public class TeamDao  extends GenericDao<TeamEntity>{

	@Override
	protected Class<TeamEntity> getClassType() {
		return TeamEntity.class;
	}

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
		try {
			return Optional.of(em.createNamedQuery("team.findTeamByUserId", TeamEntity.class)
			                     .setParameter("userId", userId)
			                     .getSingleResult());
		} catch (NoResultException nre) {
		} catch (NonUniqueResultException nure) {
		}
		//TODO HANDLE EXCEPTION
		return Optional.empty();
	}	
	
}
