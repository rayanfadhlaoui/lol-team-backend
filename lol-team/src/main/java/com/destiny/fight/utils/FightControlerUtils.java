package com.destiny.fight.utils;

import com.destiny.entities.fight.Weapon;
import com.destiny.entities.fight.WeaponBonuses;

public class FightControlerUtils {
	//@formatter:off
	//x => dexterity
	//y => dodge 
	private final static Integer[][] STRIKE_PROBA = { 
		{ 50, 60, 70, 80, 90, 95, 95, 95, 95, 95 },
		{ 40, 50, 60, 70, 80, 90, 95, 95, 95, 95 }, 
		{ 30, 40, 50, 60, 70, 80, 90, 95, 95, 95 },
		{ 20, 30, 40, 50, 60, 70, 80, 90, 95, 95 }, 
		{ 10, 20, 30, 40, 50, 60, 70, 80, 90, 95 },
		{ 10, 10, 20, 30, 40, 50, 60, 70, 80, 90 }, 
		{ 10, 10, 10, 20, 30, 40, 50, 60, 70, 80 },
		{ 10, 10, 10, 10, 20, 30, 40, 50, 60, 70 }, 
		{ 10, 10, 10, 10, 10, 20, 30, 40, 50, 60 },
		{ 10, 10, 10, 10, 10, 10, 20, 30, 40, 50 } };
	
	// x => strength
	// y => defense
	private final static Integer[][] KNOCKOUT_PROBA = {
		{ 50, 60, 70, 80, 90, 95, 95, 95, 95, 95 },
		{ 40, 50, 60, 70, 80, 90, 95, 95, 95, 95 },
		{ 30, 40, 50, 60, 70, 80, 90, 95, 95, 95 },
		{ 20, 30, 40, 50, 60, 70, 80, 90, 95, 95 }, 
		{ 10, 20, 30, 40, 50, 60, 70, 80, 90, 95 },
		{ 10, 10, 20, 30, 40, 50, 60, 70, 80, 90 },
		{ 10, 10, 10, 20, 30, 40, 50, 60, 70, 80 },
		{ 10, 10, 10, 10, 20, 30, 40, 50, 60, 70 }, 
		{ 10, 10, 10, 10, 10, 20, 30, 40, 50, 60 },
		{ 10, 10, 10, 10, 10, 10, 20, 30, 40, 50 } };
	
	private final static Integer[] STARTER_PROBABILITY = {50,60,70,	80,	90,	95,	95,	95,	95,	95 };
	//@formatter:on

	public static int getStrokeProbability(int dexterity, int dodge, Weapon weapon) {
		int strikeProbability = STRIKE_PROBA[dodge - 1][dexterity - 1].intValue()
				+ weapon.getWeaponBonuses().getStrikeBonus();
		if (strikeProbability < 10) {
			strikeProbability = 10;
		} else if (strikeProbability > 95) {
			strikeProbability = 95;
		}
		return strikeProbability;
	}

	public static int getKnockoutProbability(int strength, int defense, Weapon weapon) {
		int knockoutProbability = KNOCKOUT_PROBA[defense - 1][strength - 1].intValue()
				+ weapon.getWeaponBonuses().getKnockoutBonus();
		if (knockoutProbability < 10) {
			knockoutProbability = 10;
		} else if (knockoutProbability > 95) {
			knockoutProbability = 95;
		}
		return knockoutProbability;
	}

	public static Integer[] getInjuryProba(int strength, int defense, Weapon weapon) {
		int totalAmplificator = (strength - defense) * 5;
		WeaponBonuses weaponBonuses = weapon.getWeaponBonuses();
		Integer[] injuryProbability = {	weaponBonuses.getKoProbability(), weaponBonuses.getBrokenBoneProbability(),
										weaponBonuses.getAmputeedMemberProbability(),
										weaponBonuses.getDeathProbability() };

		if (totalAmplificator == 0) {
			return injuryProbability;
		}
		if (totalAmplificator > 0) {
			increaseInjuryProbability(injuryProbability, totalAmplificator);
		} else {
			decreaseInjuryProbability(injuryProbability, Math.abs(totalAmplificator));
		}

		return injuryProbability;
	}

	private static void decreaseInjuryProbability(Integer[] injuryProbability, int totalAmplificator) {
		int koAmplificator = totalAmplificator / 2;
		int brokenBoneAmplificator = (totalAmplificator - koAmplificator) * 3 / 5;
		int amputeedMemberAmplificator = totalAmplificator - (koAmplificator + brokenBoneAmplificator);

		int koProbability = injuryProbability[0] + koAmplificator;
		int amputeedMemberProbability = injuryProbability[2] + amputeedMemberAmplificator;
		int brokenBoneProbability = injuryProbability[1] + brokenBoneAmplificator;
		int deathProbability = injuryProbability[3] - totalAmplificator;

		int leftOver = 0;

		if (deathProbability < 0) {
			leftOver = Math.abs(deathProbability);
			deathProbability = 0;
			int delta = Math.abs(leftOver / 3);
			amputeedMemberProbability -= delta + (leftOver % 3);
			brokenBoneProbability -= delta;
			koProbability -= delta;
		}

		if (amputeedMemberProbability < 0) {
			leftOver = Math.abs(amputeedMemberProbability);
			amputeedMemberProbability = 0;
			int delta = Math.abs(leftOver / 2);
			brokenBoneProbability -= delta + (leftOver % 2);
			koProbability -= delta;
		}

		if (brokenBoneProbability < 0) {
			brokenBoneProbability = 0;
			koProbability = 100;
		}

		injuryProbability[0] = koProbability;
		injuryProbability[1] = brokenBoneProbability;
		injuryProbability[2] = amputeedMemberProbability;
		injuryProbability[3] = deathProbability;
	}

	private static void increaseInjuryProbability(Integer[] injuryProbability, int totalAmplificator) {
		int deathAmplificator = totalAmplificator / 2;
		int amputeedMemberAmplificator = (totalAmplificator - deathAmplificator) * 3 / 5;
		int brokenBoneAmplificator = totalAmplificator - (deathAmplificator + amputeedMemberAmplificator);
		int deathProbability = injuryProbability[3] + deathAmplificator;

		int amputeedMemberProbability = injuryProbability[2] + amputeedMemberAmplificator;
		int brokenBoneProbability = injuryProbability[1] + brokenBoneAmplificator;
		int koProbability = injuryProbability[0] - totalAmplificator;

		int leftOver = 0;
		if (koProbability < 0) {
			leftOver = Math.abs(koProbability);
			koProbability = 0;
			int delta = Math.abs(leftOver / 3);
			brokenBoneProbability -= delta + (leftOver % 3);
			deathProbability -= delta;
			amputeedMemberProbability -= delta;
		}
		if (brokenBoneProbability < 0) {
			leftOver = Math.abs(brokenBoneProbability);
			brokenBoneProbability = 0;
			int delta = Math.abs(leftOver / 2);
			amputeedMemberProbability -= delta + (leftOver % 2);
			deathProbability -= delta;
		}

		if (amputeedMemberProbability < 0) {
			amputeedMemberProbability = 0;
			deathProbability = 100;
		}

		injuryProbability[0] = koProbability;
		injuryProbability[1] = brokenBoneProbability;
		injuryProbability[2] = amputeedMemberProbability;
		injuryProbability[3] = deathProbability;
	}

	public static int getStarterProbability(int quickness1, int quickness2) {
		if (quickness1 > quickness2) {
			return STARTER_PROBABILITY[quickness1 - quickness2].intValue();
		}

		return 100 - STARTER_PROBABILITY[quickness2 - quickness1].intValue();

	}

}
