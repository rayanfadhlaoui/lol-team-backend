package com.destiny.fight;

import java.util.List;
import java.util.Random;

import com.destiny.entities.fight.Fighter;

public class Dice {
	
	private static Dice INSTANCE;
	private Random rand;
	
	public static Dice getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Dice();
		}
		return INSTANCE;
	}

	private Dice() {
		rand = new Random();
	}

	public int roll(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		return rand.nextInt((max - min) + 1) + min;
	}

	public int rollFromList(List<Fighter> list) {
		if(list.size() -1 == 0) {
			return 0;
		}
		
		return roll(0, list.size() - 1);
	}
}
