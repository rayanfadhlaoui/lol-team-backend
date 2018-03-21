package com.lolteam.dao;

import org.springframework.stereotype.Repository;

import com.lolteam.entities.general.SettingEntity;

@Repository
public class SettingsDao extends GenericDao<SettingEntity> {

	@Override
	protected Class<SettingEntity> getClassType() {
		return SettingEntity.class;
	}

	/** Returns the RIOT key
	 * @return The RIOT Api key.*/
	//todo handle possible exceptions.
	public String getRiotKey() {
		return em.createNamedQuery("SettingEntity.getRiotKey", String.class)
				.getSingleResult();
	}

}
