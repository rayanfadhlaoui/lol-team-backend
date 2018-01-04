package com.destiny.fight;

import com.destiny.entities.fight.Fighter;

public class FightControler {

	private Fight fight;
	private Integer nbRounds;

	public FightControler(Fighter fighter1, Fighter fighter2) {
		this.nbRounds = 0;
		this.fight = new Fight(fighter1, fighter2);
	}
	
	public void startFight() {
		while(!fight.isOver()) {
			fight.nextTurn();
			nbRounds++;
		}
		
		fight.updateFame();
		fight.updateExp();
	}

	public Fighter getWinner() {
		return fight.getWinner();
	}
	
	public Fighter getLoser() {
		return fight.getLoser();
	}

	public String printBattle() {
		return fight.getResume();
	}

	public Integer getNbRounds() {
		return nbRounds;
	}
}
