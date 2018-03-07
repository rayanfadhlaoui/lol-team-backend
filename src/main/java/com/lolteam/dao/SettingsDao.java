package com.lolteam.dao;

import org.springframework.stereotype.Repository;

import com.lolteam.entities.SettingEntity;

@Repository
public class SettingsDao extends GenericDao<SettingEntity> {

	@Override
	protected Class<SettingEntity> getClassType() {
		return SettingEntity.class;
	}

	public String getRiotKey() {
		return em.createNamedQuery("SettingEntity.getRiotKey", String.class)
				.getSingleResult();
	}

}
