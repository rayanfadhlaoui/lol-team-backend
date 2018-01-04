package com.destiny.services;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.destiny.dao.FighterDao;
import com.destiny.entities.fight.Fighter;
@Service
public class FighterService {

    @Autowired
    private FighterDao fighterDao;

    @Transactional(readOnly=true)
    public List<Fighter> getAllFighter() {
        return fighterDao.getAll();
    }

	public void save(Fighter fighter) {
		fighterDao.save(fighter);
	}

	@Transactional
	public void saveAll(List<Fighter> listFighter) {
		fighterDao.saveAll(listFighter);
	}

	public List<Fighter> getAllFighter(int numberOfResults) {
        return fighterDao.getAll(numberOfResults);
	}
	
	public Fighter get(int id) {
        return fighterDao.get(id);
	}
}