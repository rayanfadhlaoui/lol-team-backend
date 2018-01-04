package com.destiny.fight;

import com.destiny.entities.fight.Weapon;
import com.destiny.entities.fight.Experience;
import com.destiny.entities.fight.Fighter;
import com.destiny.entities.fight.Injury;
import com.destiny.entities.fight.State;
import com.destiny.fight.utils.FightControlerUtils;

public class Fight {

	private Fighter fighter1;
	private Fighter fighter2;
	private boolean isOver;
	private Dice dice;
	private Resume resume;
	private Fighter winner;
	private Fighter loser;

	public Fight(Fighter fighter1, Fighter fighter2) {
		this.fighter1 = fighter1;
		this.fighter2 = fighter2;
		this.isOver = false;
		this.dice = Dice.getInstance();
		this.resume = new Resume();
	}

	/**
	 * @return The winner of the fight.<BR/>
	 *         If the fight is still running, returns null.
	 */
	public Fighter getWinner() {
		return isOver ? winner : null;
	}

	public boolean isOver() {
		return isOver;
	}

	public void nextTurn() {
		
		int quickness1 = fighter1.getCharacteristic().getReactivity();
		int quickness2 = fighter2.getCharacteristic().getReactivity();
		int starterProbability = FightControlerUtils.getStarterProbability(quickness1, quickness2);
		int roll = dice.roll(0, 100);
		if(roll <= starterProbability) {
			attack(fighter1, fighter2);
			if(!isOver) {
				attack(fighter2, fighter1);
			}
		}
		else {
			attack(fighter2, fighter1);
			if(!isOver) {
				attack(fighter1, fighter2);			
			}
		}
	}

	private void attack(Fighter attacker, Fighter defender) {
		int attack = attacker.getCharacteristic().getAttack();
		
		for (int i = 0; i < attack && !isOver; i++) {
			if (attackerHasStrikes(attacker, defender)) {
				
				int vitality = defender.getCharacteristic().getVitality() -1;
				if(vitality == 0) {
				Injury injury = getInjury(attacker, defender, attacker.getWeapon());
				State state = defender.getState();
				state.setInjury(injury);
				isOver = true;
				resume.addText(attacker + " a blessé " + defender + " état : => " + injury.getName());
				resume.addText("fight is over");
				winner = attacker;
				loser = defender;
				}
				else {
					defender.getCharacteristic().setVitality(vitality);
				}
			}
		}
	}

	void updateFame() {
		int winnerFame = winner.getHistory().getFame();
		int loserFame = loser.getHistory().getFame()-10;
		int deltaFame = 5;
		if(loserFame > winnerFame) {
			deltaFame += (loserFame - winnerFame) /3; 
		}
		winner.getHistory().setFame(winnerFame + deltaFame);
		loser.getHistory().setFame(loserFame);
	}

	void updateExp() {
		int loserLvl = loser.getExperience().getLevel();
		Experience experience = winner.getExperience();
		int winnerExp = experience.getCurrentExp() + 10 * loserLvl;
		experience.setCurrentExp(winnerExp);
	}	
	
	private Injury getInjury(Fighter attacker, Fighter defender, Weapon arme) {
		
		int destiny = defender.getCharacteristic().getDestiny();		
		
		int strength = attacker.getCharacteristic().getStrength();
		int defense = defender.getCharacteristic().getDefense();
		Integer[] injuriesProbability = FightControlerUtils.getInjuryProba(strength, defense, arme);
		int diceResult = dice.roll(0, 100);
		int injuryProbability = injuriesProbability[0];
		// case KO
		if (diceResult <= injuryProbability) {
			return new Injury("Ko");
		}

		injuryProbability += injuriesProbability[1];
		if (diceResult <= injuryProbability) {
			return new Injury("Broken Bone");
		}
		
		if(destiny > 0) {
			defender.getCharacteristic().setDestiny(destiny-1);
			return new Injury("Ko");
		}

		injuryProbability += injuriesProbability[2];
		if (diceResult <= injuryProbability) {
			return new Injury("Amputee member");
		}
		
		return new Injury("Death");

	}

	private boolean attackerHasStrikes(Fighter attacker, Fighter defender) {
		resume.addText(attacker + " attaque " + defender);
		int dexteriry = attacker.getCharacteristic().getDexterity();
		int dodge = defender.getCharacteristic().getDodge();
		Weapon attackerWeapon = attacker.getWeapon();
		int strokeProbability = FightControlerUtils.getStrokeProbability(dexteriry, dodge, attackerWeapon);
		if (dice.roll(0, 100) <= strokeProbability) {
			int strength = attacker.getCharacteristic().getStrength();
			int defense = defender.getCharacteristic().getDefense();
			int knockoutProbability = FightControlerUtils.getStrokeProbability(strength, defense, attackerWeapon);
			boolean isKnockout = dice.roll(0, 100) <= knockoutProbability;
			if (!isKnockout) {
				resume.addText(attacker + " n'a pas blessé " + defender);
			}
			return isKnockout;
		}

		resume.addText(defender + " a esquivé l'attaque de " + attacker);
		return false;
	}

	public String getResume() {
		return resume.readResume();
	}

	public Fighter getLoser() {
		return isOver ? loser : null;
	}


}
