package com.destiny.factory;

import com.destiny.entities.fight.Characteristic;
import com.destiny.entities.fight.Experience;
import com.destiny.entities.fight.Fighter;

public class FighterFactory {

	public static Fighter loadFighterForLevelUp(Integer fighterId, Characteristic characteristic, Experience experience) {
		Fighter fighter = new Fighter();
		fighter.setId(fighterId);
		fighter.setExperience(experience);
		fighter.setCharacteristic(characteristic);
		return fighter;
	}
}
