package com.destiny.dao;


import org.springframework.stereotype.Repository;

import com.destiny.entities.fight.Characteristic;

@Repository
public class CharacteristicDao extends GenericDao<Characteristic> {
	
	@Override
	protected Class<Characteristic> getClassType() {
		return Characteristic.class;
	}
}
