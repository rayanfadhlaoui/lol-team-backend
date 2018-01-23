package com.lolteam.dao;

import org.springframework.stereotype.Repository;

import com.lolteam.entities.MatchEntity;

@Repository
public class MatchEntityDao extends GenericDao<MatchEntity>{

	@Override
	protected Class<MatchEntity> getClassType() {
		return MatchEntity.class;
	}

}
