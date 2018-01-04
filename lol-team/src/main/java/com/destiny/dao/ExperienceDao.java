package com.destiny.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.destiny.entities.fight.Characteristic;
import com.destiny.entities.fight.Experience;
import com.destiny.entities.fight.Fighter;
import com.destiny.factory.FighterFactory;

@Repository
public class ExperienceDao extends GenericDao<Experience> {

	@Override
	protected Class<Experience> getClassType() {
		return Experience.class;
	}

	public List<Fighter> getLeveledUpFighters() {
		long start = System.currentTimeMillis();
		
		List<Object[]> resultList = em.createNamedQuery("experience.getLeveledUpFighters", Object[].class)
		.getResultList();
		
		
		List<Fighter> fighterList = new ArrayList<>();
		for (Object[] result : resultList) {
			Integer fighterId = (Integer) result[0];
			Characteristic characteristic = (Characteristic) result[1];
			Experience experience = (Experience) result[2];
			Fighter fighter = FighterFactory.loadFighterForLevelUp(fighterId, characteristic, experience);
			fighterList.add(fighter);
		}
		

		System.out.println((start - System.currentTimeMillis())/1000 + " s Pour requete de " + resultList.size());
		
		return fighterList;
		
	}
}
