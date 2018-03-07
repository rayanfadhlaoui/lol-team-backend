package com.lolteam.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.lolteam.entities.LolTeamUserEntity;

@Repository
public class LolTeamUserDao extends GenericDao<LolTeamUserEntity> {

	@Override
	protected Class<LolTeamUserEntity> getClassType() {
		return LolTeamUserEntity.class;
	}

	@PersistenceContext
	private EntityManager em;

	public Optional<LolTeamUserEntity> getUserFromUsernameAndPassord(LolTeamUserEntity user) {
		try {
			return Optional.of(em.createNamedQuery("ltUser.getUserFromloginAndPassword", LolTeamUserEntity.class)
			                     .setParameter("username", user.getUsername())
			                     .setParameter("password", user.getPassword())
			                     .getSingleResult());
		} catch (NoResultException nre) {
		} catch (NonUniqueResultException nure) {
		}

		return Optional.empty();
	}
}
