package com.destiny.fight;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.destiny.entities.fight.Weapon;
import com.destiny.entities.fight.WeaponBonuses;
import com.destiny.entities.fight.Characteristic;
import com.destiny.entities.fight.Experience;
import com.destiny.entities.fight.Fighter;
import com.destiny.entities.fight.History;
import com.destiny.entities.fight.State;
import com.destiny.entities.general.Person;

public class FightControlerTest {

	@Before
	public void setUp() {

	}

	@Test
	public void testFairStrikeAndKnockout() {

		// 0 > start, 1> strikes, 2 > knockout, 3 > injury
		Integer[] firstCaseDiceResults = { 50, 50, 50, 50 };
		mockDice(firstCaseDiceResults);
		Fighter fighter1 = createFighter("Fighter1");
		Fighter fighter2 = createFighter("Fighter2");

		FightControler fightControler = simulateFight(fighter1, fighter2);
		assertFight(fighter1, fighter2, fightControler, 1, "Ko");

		// Round 1: 0 > start(f1), 1 > strikes(true), 2 > knockout(false), 3 > f2
		// strike(false),
		// Round 2: 4 > start(f1), 5 > strikes(true), 5 > knockout(true) , 6 >
		// injury(ko)
		Integer[] firstCaseDiceResults2Rounds = { 50, 50, 51, 51, 50, 50, 50, 99 };
		mockDice(firstCaseDiceResults2Rounds);

		fighter1 = createFighter("Fighter1");
		fighter2 = createFighter("Fighter2");

		fightControler = simulateFight(fighter1, fighter2);
		assertFight(fighter1, fighter2, fightControler, 2, "Ko");

		/* f1 starts but doesnt knockout f2, f2 knockout f1 */
		Integer[] secondCaseDiceResults = { 50, 50, 51, 50, 50, 50 };
		mockDice(secondCaseDiceResults);

		fighter1 = createFighter("Fighter1");
		fighter2 = createFighter("Fighter2");

		fightControler = simulateFight(fighter1, fighter2);
		assertFight(fighter2, fighter1, fightControler, 1, "Ko");

	}

	@Test
	public void testUnfairStrikeAndKnockout() throws Exception {
		// 0 > start, 1> strikes, 2 > knockout, 3 > injury
		Integer[] firstCaseDiceResults = { 49, 59, 59, 99 };
		mockDice(firstCaseDiceResults);
		Fighter fighter1 = createFighter("Fighter1");
		Fighter fighter2 = createFighter("Fighter2", new Characteristic(4, 4, 4, 4, 4, 1, 1));

		FightControler fightControler = simulateFight(fighter1, fighter2);
		assertFight(fighter2, fighter1, fightControler, 1, "Death");

		// f1 start, f1 !strikes, f2 stricks, f2 knockout, f2 injured f1
		Integer[] secondCaseDiceResults = { 41, 61, 39, 39, 39 };
		mockDice(secondCaseDiceResults);
		fighter1 = createFighter("Fighter1");
		fighter2 = createFighter("Fighter2", new Characteristic(4, 4, 4, 4, 4, 1, 1));

		fightControler = simulateFight(fighter1, fighter2);
		assertFight(fighter1, fighter2, fightControler, 1, "Ko");
	}

	@Test
	public void testMultipleAttackAndVitality() throws Exception {
		Integer[] firstCaseDiceResults = new Integer[14];
		/* f2 starts, f2 !strikes */
		firstCaseDiceResults[0] = 51;
		firstCaseDiceResults[1] = 51;
		/* f1 strikes, !knockout */
		firstCaseDiceResults[2] = 50;
		firstCaseDiceResults[3] = 51;
		/* f1 strikes, !knockout */
		firstCaseDiceResults[4] = 50;
		firstCaseDiceResults[5] = 51;
		/* f1 strikes, f1 knockout */
		firstCaseDiceResults[6] = 50;
		firstCaseDiceResults[7] = 50;
		/* f1 starts, f1 strikes, f1 knockout */
		firstCaseDiceResults[8] = 50;
		firstCaseDiceResults[9] = 50;
		firstCaseDiceResults[10] = 50;
		/* f1 strikes, f1 knockout, f1 injured f2 */
		firstCaseDiceResults[11] = 50;
		firstCaseDiceResults[12] = 50;
		firstCaseDiceResults[13] = 50;
		mockDice(firstCaseDiceResults);
		Fighter fighter1 = createFighter("Fighter1", new Characteristic(4, 4, 4, 4, 4, 1, 3));
		Fighter fighter2 = createFighter("Fighter2", new Characteristic(4, 4, 4, 4, 4, 3, 1));

		FightControler fightControler = simulateFight(fighter1, fighter2);
		assertFight(fighter1, fighter2, fightControler, 2, "Ko");
	}

	private Fighter createFighter(String name, Characteristic characteristic) {
		History history = new History();
		Experience experience = new Experience();
		experience.setNextLvlExp(10);
		experience.setCurrentExp(0);
		experience.setLevel(1);
		Person person = new Person();
		person.setFirstName(name);
		WeaponBonuses weaponBonuses = new WeaponBonuses(0, 0, 100, 0, 0, 0);
		Weapon mainNue = new Weapon("Main nue", "Main", 100, weaponBonuses);
		Fighter fighter = new Fighter(person, characteristic, new State());
		fighter.setWeapon(mainNue);
		fighter.setHistory(history);
		fighter.setExperience(experience);
		return fighter;
	}

	private Fighter createFighter(String name) {
		return createFighter(name, new Characteristic(3, 3, 3, 3, 3, 1, 1));
	}

	private void assertFight(Fighter fighter1, Fighter fighter2, FightControler fightControler, int nbRound, String injury) {
		Fighter loser = fightControler.getLoser();
		String loserName = loser.getPerson().getFirstName();

		Fighter winner = fightControler.getWinner();
		String winnerName = winner.getPerson().getFirstName();

		assertEquals(fighter2.getPerson().getFirstName(), loserName);
		assertEquals(fighter1.getPerson().getFirstName(), winnerName);

		assertEquals(10, winner.getExperience().getCurrentExp().intValue());
		assertEquals(0, loser.getExperience().getCurrentExp().intValue());

		assertEquals(5, winner.getHistory().getFame());
		assertEquals(-10, loser.getHistory().getFame());

		assertEquals(null, winner.getState().getInjury());
		assertEquals(injury, loser.getState().getInjury().getName());

		assertEquals(nbRound, fightControler.getNbRounds().intValue());
	}

	private FightControler simulateFight(Fighter fighter1, Fighter fighter2) {
		FightControler fightControler = new FightControler(fighter1, fighter2);
		fightControler.startFight();
		return fightControler;
	}

	private void mockDice(Integer[] expectedDiceResults) {
		Dice diceMock = Mockito.mock(Dice.class);

		Answer<Integer> answer = new Answer<Integer>() {
			public int nbCall = 0;
			private Integer[] valueList = expectedDiceResults;

			public Integer answer(InvocationOnMock invocation) throws Throwable {
				if( nbCall > valueList.length -1 ) {
					throw new Exception("dernière valeur : " + toString());
				}
				int value = valueList[nbCall];
				nbCall++;		
				return value;
			}

			private Integer getLastValue() {
				return valueList[nbCall];
			}
			
			@Override
			public String toString() {
				return "valueList["+nbCall+"] = " + getLastValue();
			}
			
		};

		Mockito.when(diceMock.roll(Mockito.anyInt(), Mockito.anyInt())).thenAnswer(answer);

		Field field;
		try {
			field = Dice.class.getDeclaredField("INSTANCE");
			field.setAccessible(true);
			field.set(null, diceMock);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.getStackTrace();
		}
	}

}
