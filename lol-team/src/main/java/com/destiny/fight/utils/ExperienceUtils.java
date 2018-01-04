package com.destiny.fight.utils;

import com.destiny.entities.fight.Characteristic;
import com.destiny.entities.fight.Experience;
import com.destiny.entities.fight.Fighter;
import com.destiny.fight.Dice;

public class ExperienceUtils {
	
	private static Dice dice = Dice.getInstance();
	
	public static void levelUp(Fighter fighter) {
		while(shouldLevelUp(fighter)) {
			int rand = dice.roll(0, 100);
			int destiny = fighter.getCharacteristic().getDestiny();
			fighter.getCharacteristic().setDestiny(destiny + dice.roll(1, 3));
			if(rand < 95) {
				levelUpSkill(fighter);
			}
			else {
				levelUpCharacteristic(fighter);
			}
			updateExperience(fighter);
		}
	}

	private static void updateExperience(Fighter fighter) {
		Experience experience = fighter.getExperience();
		Integer level = experience.getLevel();
		int nextLvlExp = (int) Math.abs(level * (Math.sqrt(level) * 10));
		experience.setLevel(level + 1);
		experience.setNextLvlExp(nextLvlExp);
	}

	private static void levelUpCharacteristic(Fighter fighter) {
		Characteristic characteristic = fighter.getCharacteristic();
		int rand = dice.roll(0, 100);
		if(rand > 80) {
			updatePreciousCharacteristic(characteristic);
		}
		else {
			updateBasicCharacteristic(characteristic);
		}
		
	}

	private static void updatePreciousCharacteristic(Characteristic characteristic) {
		int rand = dice.roll(0, 1);
		if(rand == 1) {
			int attack = characteristic.getAttack();	
			characteristic.setAttack(attack);;
		}
		else if(rand == 1) {
			int vitality = characteristic.getVitality();	
			characteristic.setVitality(vitality);
		}
	}

	private static void updateBasicCharacteristic(Characteristic characteristic) {
		int rand = dice.roll(0, 4);
		if(rand == 0) {
			int defense = characteristic.getDefense();	
			characteristic.setDefense(defense);
		}
		else if(rand == 1) {
			int strength = characteristic.getStrength();	
			characteristic.setStrength(strength);
		}
		else if(rand == 2) {
			int reactivity = characteristic.getReactivity();	
			characteristic.setReactivity(reactivity);
		}
		else if(rand == 3) {
			int dodge = characteristic.getDodge();	
			characteristic.setDodge(dodge);
		}
		else if(rand == 4) {
			int dexterity = characteristic.getDexterity();
			characteristic.setDexterity(dexterity);
		}		
	}

	//TODO
	private static void levelUpSkill(Fighter fighter) {
		
	}

	private static boolean shouldLevelUp(Fighter fighter) {
		Experience experience = fighter.getExperience();
		return experience.getCurrentExp() >= experience.getNextLvlExp();
	}
}
