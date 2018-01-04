package com.destiny.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.destiny.entities.general.DestinyUser;

@Repository
public class DestinyUserDao extends GenericDao<DestinyUser> {

	@Override
	protected Class<DestinyUser> getClassType() {
		return DestinyUser.class;
	}

	@PersistenceContext
	private EntityManager entityManager;

	public DestinyUser getUserFromUsernameAndPassord(DestinyUser user) {
		TypedQuery<DestinyUser> query = entityManager.createQuery("SELECT u FROM DestinyUser u WHERE u.username = :username AND u.password = :password",
				DestinyUser.class)
			.setParameter("username", user.getUsername())
			.setParameter("password", user.getPassword());
		try {
			return query.getSingleResult();
		}
		catch (NoResultException nre) {
			user.setId(-1);
			return user;
		}
		catch (NonUniqueResultException nure) {
			user.setId(-1);
			return user;
		}
	}
}
