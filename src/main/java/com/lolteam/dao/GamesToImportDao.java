package com.lolteam.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lolteam.entities.treatment.GamesToImportEntity;

@Repository
public class GamesToImportDao extends GenericDao<GamesToImportEntity> {

	@Override
	protected Class<GamesToImportEntity> getClassType() {
		return GamesToImportEntity.class;
	}

	public List<GamesToImportEntity> findGamesToImport(int nbRows) {
		return em.createNamedQuery("GamesToImportEntity.findGamesToImport", GamesToImportEntity.class)
				.setMaxResults(nbRows)
				.getResultList();
	}

}
