package com.destiny.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.destiny.dao.TownDao;
import com.destiny.entities.fight.Characteristic;
import com.destiny.entities.fight.Experience;
import com.destiny.entities.fight.Fighter;
import com.destiny.entities.fight.History;
import com.destiny.entities.fight.State;
import com.destiny.entities.fight.TitleEnum;
import com.destiny.entities.fight.Town;
import com.destiny.entities.fight.Weapon;
import com.destiny.entities.fight.WeaponBonuses;
import com.destiny.entities.general.Name;
import com.destiny.entities.general.Person;
import com.destiny.fight.Dice;
import com.destiny.services.FighterService;
import com.destiny.services.NamesService;

@RestController
public class HomeControler {

	@Autowired
	private NamesService namesService;

	@Autowired
	private TownDao townDao;

	@Autowired
	private FighterService fighterService;
	
	private Dice dice = Dice.getInstance();


	@Transactional
	@RequestMapping(value = "/generateHuman", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Fighter generate() {

		List<Name> allFirstNames = namesService.getAllFirstNames("Human");
		List<Name> allLastNames = namesService.getAllLastNames();
		Town town = townDao.getTown("Valena");

		List<Fighter> listFighter = new ArrayList<>();
		for (int i = 0; i < 19096; i++) {
			Fighter fighter = createFighter(allFirstNames, allLastNames, town);
			listFighter.add(fighter);
		}

		fighterService.saveAll(listFighter);
		return new Fighter();
	}

	private Fighter createFighter(List<Name> allFirstNames, List<Name> allLastNames, Town town) {
		Fighter fighter = new Fighter();

		WeaponBonuses weaponBonuses = new WeaponBonuses(dice.roll(0, 10), dice.roll(0, 10), 0, 10, 40, 50);
		Weapon weapon = new Weapon("Iron Sword", "Sword", 50, weaponBonuses);
		State state = new State();
		Person person = new Person();
		String firstName = allFirstNames.get(dice.roll(0, allFirstNames.size() - 1)).getName();
		String lastName = allLastNames.get(dice.roll(0, allLastNames.size() - 1)).getName();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setRace("Human");
		person.setHomeTown(town);
		person.setBirthDate(DateUtils.parse("01/01/00"));
		person.setTitle(TitleEnum.NOVICE);
		Characteristic characteristic = createHumanCharacteristic();
		characteristic.setAttack(1);
		characteristic.setBravery(dice.roll(0, 10));
		
		Experience experience = new Experience();
		experience.setCurrentExp(0);
		experience.setNextLvlExp(10);
		experience.setLevel(1);
		
		
		int destiny = getDestiny(dice);
		characteristic.setDestiny(destiny);

		fighter.setCharacteristic(characteristic);
		fighter.setPerson(person);
		fighter.setState(state);
		fighter.setWeapon(weapon);
		fighter.setExperience(experience);

		History history = new History();
		history.setNumberOfKills(0);
		fighter.setHistory(history);
		return fighter;
	}

	private Characteristic createHumanCharacteristic() {
		int strenght = getHumanValue(3);
		int defense = getHumanValue(3);
		int dexterity = getHumanValue(3);
		int dodge = getHumanValue(3);
		int reactivity = getHumanValue(3);
		int vitality = getHumanValue(1);
		int attack = getHumanValue(1);
		Characteristic characteristic = new Characteristic(strenght, defense, dexterity, dodge, reactivity, vitality, attack);
		return characteristic;
	}

	private int getHumanValue(int value) {
		while(true) {
			int roll = dice.roll(0, 100);
			if(roll < 98) {
				return value;
			}
			value++;
		}
	}

	@RequestMapping(value = "/generateOrc", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Fighter generateOrc() {

		List<Name> allFirstNames = namesService.getAllFirstNames("Orc");
		Town town = townDao.getTown("Draktar");
		List<Fighter> listFighter = new ArrayList<>();

		for (int i = 0; i <= 1000; i++) {
			Fighter fighter = new Fighter();

			WeaponBonuses weaponBonuses = new WeaponBonuses(dice.roll(0, 10), dice.roll(0, 10), 0, 10, 40, 50);
			Weapon weapon = new Weapon("Iron Sword", "Sword", 50, weaponBonuses);
			State state = new State();
			Person person = new Person();
			int roll = dice.roll(0, allFirstNames.size() - 1);
			String firstName = allFirstNames.get(roll).getName();
			person.setFirstName(firstName);
			person.setLastName(null);
			person.setHomeTown(town);
			person.setRace("Orc");
			person.setBirthDate(DateUtils.parse("01/01/00"));
			person.setTitle(TitleEnum.NOVICE);
			Characteristic characteristic = new Characteristic(4, 4, 2, 2, 2, 1, 1);
			characteristic.setAttack(1);
			characteristic.setBravery(dice.roll(0, 10));
			int destiny = getDestiny(dice);
			characteristic.setDestiny(destiny);

			fighter.setCharacteristic(characteristic);
			fighter.setPerson(person);
			fighter.setState(state);
			fighter.setWeapon(weapon);

			History history = new History();
			history.setNumberOfKills(0);
			history.setFame(0);
			fighter.setHistory(history);

			listFighter.add(fighter);
		}

		fighterService.saveAll(listFighter);
		return new Fighter();
	}

	@RequestMapping(value = "/findAllFighter", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Fighter> findAllFighter() {
		List<Fighter> listFighter = fighterService.getAllFighter(100);
		return listFighter;
	}

	@RequestMapping(value = "/findFighter", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Fighter findFighter(@RequestParam(value="id") Integer id) {
		Fighter fighter = fighterService.get(id);
		return fighter;
	}

	private int getDestiny(Dice dice) {
		int destiny = 0;
		for (int i = 0; i < 20; i++) {
			if (dice.roll(0, 100) > 95) {
				destiny++;
			}
		}
		return destiny;
	}

}