package com.lolteam.dao;

import java.util.Optional;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.lolteam.entities.general.LolTeamUserEntity;

@Repository
public class LolTeamUserDao extends GenericDao<LolTeamUserEntity> {

	@Override
	protected Class<LolTeamUserEntity> getClassType() {
		return LolTeamUserEntity.class;
	}

	/** 
	 * Returns a optional {@link LolTeamUserEntity} from a username and a password.
	 * The optional will be empty if more than one user or no user at all are associated to the parameters 
	 * 
	 * @param username 
	 * 		The username
	 * @param password 
	 * 		The password
	 * 
	 * @return An optional on a user*/
	public Optional<LolTeamUserEntity> getUserFromUsernameAndPassord(String username, String password) {
		try {
			return Optional.of(em.createNamedQuery("ltUser.getUserFromloginAndPassword", LolTeamUserEntity.class)
			                     .setParameter("username", username)
			                     .setParameter("password", password)
			                     .getSingleResult());
		} catch (NoResultException nre) {
		} catch (NonUniqueResultException nure) {
		}

		return Optional.empty();
	}
}
