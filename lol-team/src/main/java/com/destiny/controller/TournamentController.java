package com.destiny.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.destiny.dao.FighterDao;
import com.destiny.dao.PersonDao;
import com.destiny.entities.fight.Fighter;
import com.destiny.entities.fight.TitleEnum;
import com.destiny.entities.fight.Weapon;
import com.destiny.entities.fight.WeaponBonuses;
import com.destiny.entities.general.Person;
import com.destiny.fight.Dice;
import com.destiny.fight.FightControler;

@RestController
@RequestMapping(value = "/tournament")
public class TournamentController {

	@Autowired
	FighterDao fighterDao;

	@Autowired
	PersonDao personDao;
	
	Dice dice = Dice.getInstance();
	
	@Transactional
	@RequestMapping(value = "/simulate", method = RequestMethod.GET, produces = "application/json")
	public String simulateTournament() {

		List<Fighter> fighterList = fighterDao.getFighterByRaceAndClasse("Human", TitleEnum.NOVICE);
		WeaponBonuses weaponBonuses = new WeaponBonuses(0, 0, 100, 0, 0, 0);
		Weapon woodenSword = new Weapon("Wooden Sword", "Sword", 10, weaponBonuses);

		List<Fighter> participantList = new LinkedList<>();

		for (Fighter fighter : fighterList) {
			fighter.setWeapon(woodenSword);
			participantList.add(fighter);
		}
		
		simulateTournament(participantList);
		
		Fighter winner = participantList.get(0);
		Person person = winner.getPerson();
		person.setTitle(TitleEnum.SOLDIER);
		
		personDao.save(person);

		return "Done";
	}

	private void simulateTournament(List<Fighter> participantList) {
		boolean displayFight = false;
		int nbRound = 1;

		while (participantList.size() != 1) {
			
			if(participantList.size() <= 8) {
				if(participantList.size() == 2) {
					System.out.println("Final Round");
				}
				else {
					System.out.println("Round n°"+nbRound);					
				}
				displayFight = true;
			}

			List<FightControler> fightControllerList = shuffleFighters(participantList);

			for (FightControler fightControler : fightControllerList) {
				fightControler.startFight();
				if(displayFight) {
					System.out.println(fightControler.printBattle());
				}
				participantList.remove(fightControler.getLoser());
			}
			nbRound++;
		}
	}
	
	private List<FightControler> shuffleFighters(List<Fighter> fighterList) {
		
		List<Fighter> tmpFighterList = new ArrayList<>(fighterList);

		int nbFight = tmpFighterList.size() / 2;

		List<FightControler> listFight = new ArrayList<>();
		for (int i = 0; i < nbFight; i++) {

			int index1 = dice.rollFromList(tmpFighterList);
			Fighter fighter1 = tmpFighterList.get(index1);
			tmpFighterList.remove(index1);

			int index2 = dice.rollFromList(tmpFighterList);
			Fighter fighter2 = tmpFighterList.get(index2);
			tmpFighterList.remove(index2);

			FightControler fightControler = new FightControler(fighter2, fighter1);
			listFight.add(fightControler);

		}
		return listFight;
	}
}
