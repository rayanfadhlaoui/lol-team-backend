package com.lolteam.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lolteam.dao.GamesToImportDao;
import com.lolteam.entities.treatment.GamesToImportEntity;

@Service
public class GamesToImportService {
	
	@Autowired
	private GamesToImportDao gamesToImportDao;
	
	@Transactional
	public void saveGameToImport(GamesToImportEntity gamesToImportEntity) {
		gamesToImportDao.save(gamesToImportEntity);
	}

	public List<GamesToImportEntity> findGamesToImport(int nbRows) {
		return gamesToImportDao.findGamesToImport(nbRows);
	}

	public void saveAll(List<GamesToImportEntity> gamesToImport) {
		gamesToImportDao.saveAll(gamesToImport);
	}
}
