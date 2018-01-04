package com.destiny.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.destiny.dao.ExperienceDao;
import com.destiny.dao.FighterDao;
import com.destiny.entities.fight.Experience;
import com.destiny.entities.fight.Fighter;
import com.destiny.fight.Dice;
import com.destiny.fight.FightControler;

@RestController
@RequestMapping(value = "/simulateWar")
public class WarSimulatorControler {

	@Autowired
	private FighterDao fighterDao;

	@Autowired
	private ExperienceDao experienceDao;
	
	Dice dice = Dice.getInstance();

	@RequestMapping(value = "/getFightersNumber", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody Map<String, Integer> generate(@RequestBody List<String> raceList) {
		Map<String, Integer> numberFighterByRace = new HashMap<>();
		for (String race : raceList) {
			int numberFighter = fighterDao.getNumberFighterByRace(race);
			numberFighterByRace.put(race, numberFighter);
		}
		return numberFighterByRace;
	}

	@Transactional
	@RequestMapping(value = "/simulateWar", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Map<String, String> simulateWars() {
		System.out.println("Début Algo");
		long localStart = System.currentTimeMillis();
		long start = System.currentTimeMillis();
		List<Fighter> listHumans = new LinkedList<>(fighterDao.getFighterByRace("Human"));
		System.out.println("Fin requete 1 " + listHumans.size() + " results "
				+ (System.currentTimeMillis() - localStart) / 1000 + "seconds");
		localStart = System.currentTimeMillis();

		List<Fighter> listOrc = new LinkedList<>(fighterDao.getFighterByRace("Orc"));
		System.out.println("Fin requete 1 " + listOrc.size() + " results "
				+ (System.currentTimeMillis() - localStart) / 1000 + "seconds");

		List<Fighter> listDeadFighters = new ArrayList<>();
		List<Fighter> listInjuredFighters = new ArrayList<>();

		localStart = System.currentTimeMillis();
		while (warIsNotOver(listOrc, listHumans)) {
			System.out.println("listOrc => "+listOrc.size());
			System.out.println("listHuman => "+listHumans.size());
			System.out.println("list Dead Persons => "+listDeadFighters.size());
			List<FightControler> fightControlerList = shuffleFighters(listHumans, listOrc);

			for (FightControler fightControler : fightControlerList) {
				fightControler.startFight();

				Fighter winner = fightControler.getWinner();
				Fighter loser = fightControler.getLoser();

				addLoserToProperListIfNotDead(listHumans, listOrc, listDeadFighters, listInjuredFighters, loser);
				addFighterToProperList(listHumans, listOrc, winner);
			}
		}

		System.out.println("Fin algo 1 " + (System.currentTimeMillis() - localStart) / 1000 + "seconds");
		localStart = System.currentTimeMillis();

		System.out.println(listDeadFighters.size() + "Personnages sont morts");
		fighterDao.deleteAll(listDeadFighters);
		System.out.println("Fin delete 1 " + (System.currentTimeMillis() - localStart) / 1000 + "seconds");
		localStart = System.currentTimeMillis();
		List<Fighter> allFighters = listOrc;
		allFighters.addAll(listHumans);
		allFighters.addAll(listInjuredFighters);
		fighterDao.saveAll(allFighters);
		System.out.println("Fin update 1 " + (System.currentTimeMillis() - localStart) / 1000 + "seconds");
		localStart = System.currentTimeMillis();

		System.out.println("Fin tout" + (System.currentTimeMillis() - start) / 1000 + "seconds");
		Map<String, String> result = new HashMap<>();
		String winningRace = getWinningRace(listHumans, listOrc);
		result.put("winningRace", winningRace);
		return result;
	}

	@RequestMapping(value = "/purge", method = RequestMethod.GET)
	public @ResponseBody String purge() {
		fighterDao.purgeDeadFighters();
		return "done";
	}

	private String getWinningRace(List<Fighter> listHumans, List<Fighter> listOrc) {
		if (listHumans.isEmpty()) {
			return "Orc";
		}
		return "Human";
	}

	private boolean warIsNotOver(List<Fighter> listOrc, List<Fighter> listHumans) {

		return !listOrc.isEmpty() && !listHumans.isEmpty();
	}

	private void addLoserToProperListIfNotDead(List<Fighter> listHumans, List<Fighter> listOrc, List<Fighter> listInjuredFigther, 
			List<Fighter> listDeadFighters, Fighter loser) {
		if (loser.getState().getInjury().getName().equals("Death")) {
			listDeadFighters.add(loser);
		}else{
			listInjuredFigther.add(loser);
		} 
	}

	private void addFighterToProperList(List<Fighter> listHumans, List<Fighter> listOrc, Fighter fighter) {
		if (fighter.getPerson().getRace().equals("Orc")) {
			listOrc.add(fighter);
		} else {
			listHumans.add(fighter);
		}
	}

	private List<FightControler> shuffleFighters(List<Fighter> listHumans, List<Fighter> listOrc) {

		long localStart = System.currentTimeMillis();
		int nbFight = Math.min(listHumans.size(), listOrc.size());

		List<FightControler> listFight = new ArrayList<>();
		for (int i = 0; i < nbFight; i++) {

			int humanIndex = dice.rollFromList(listHumans);
			int orcIndex = dice.rollFromList(listOrc);
			Fighter orc = listOrc.get(orcIndex);
			Fighter human = listHumans.get(humanIndex);
			FightControler fightControler = new FightControler(human, orc);
			listFight.add(fightControler);

			listHumans.remove(humanIndex);
			listOrc.remove(orcIndex);
		}
		System.out.println("Fin tirage au sort " + (System.currentTimeMillis() - localStart) / 1000 + "seconds");
		return listFight;
	}

	private Map<Integer, Experience> updateFighter(List<Integer> listId) {
		Map<Integer, Experience> idExpByFighterId = new HashMap<>();
		for (Integer id : listId) {
			System.out.println(id);
			Experience experience = new Experience();
			experience.setLevel(1);
			experience.setNextLvlExp(10);
			experience.setCurrentExp(0);
			idExpByFighterId.put(id, experience);
		}
		return idExpByFighterId;
	}

}