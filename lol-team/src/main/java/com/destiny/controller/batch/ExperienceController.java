package com.destiny.controller.batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.destiny.dao.CharacteristicDao;
import com.destiny.dao.ExperienceDao;
import com.destiny.entities.fight.Characteristic;
import com.destiny.entities.fight.Experience;
import com.destiny.entities.fight.Fighter;
import com.destiny.fight.utils.ExperienceUtils;

@RestController
@RequestMapping(value="/experience")
public class ExperienceController {
	
	@Autowired
	ExperienceDao experienceDao;
	
	@Autowired
	CharacteristicDao characteristicDao;
	
	@Transactional
	@RequestMapping(value = "/simulateLevelUp", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String simulateLevelUp() {
		
		List<Fighter> leveledUpFighters = experienceDao.getLeveledUpFighters();
		
		long start = System.currentTimeMillis();
		for (Fighter fighter : leveledUpFighters) {
			ExperienceUtils.levelUp(fighter);
		}
		System.out.println((start - System.currentTimeMillis())/1000 + " s Pour levelUp");

		
		updateFighters(leveledUpFighters);
		
		
		return "Done";
	}

	private void updateFighters(List<Fighter> fighterList) {
		List<Experience> experienceList = new ArrayList<>();
		List<Characteristic> characteristicList = new ArrayList<>();
		for (Fighter fighter : fighterList) {
			experienceList.add(fighter.getExperience());
			characteristicList.add(fighter.getCharacteristic());
		}
		
		long start = System.currentTimeMillis();		
		experienceDao.saveAll(experienceList);
		System.out.println((start - System.currentTimeMillis())/1000 + " s Pour experience");
		start = System.currentTimeMillis();
		characteristicDao.saveAll(characteristicList);
		System.out.println((start - System.currentTimeMillis())/1000 + " s Pour characteristics");

	}
}
