package com.lolteam.dao;

import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lolteam.entities.TeamEntity;

@Repository
public class TeamDao  extends GenericDao<TeamEntity>{

	@Override
	protected Class<TeamEntity> getClassType() {
		return TeamEntity.class;
	}

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
