package com.lolteam.services;

import com.lolteam.entities.general.GenericEntity;
import com.lolteam.framework.core.db.EntityCache;

public interface EntityService<U extends Number,T extends GenericEntity> {

	EntityCache<U, T> getCache();

}
