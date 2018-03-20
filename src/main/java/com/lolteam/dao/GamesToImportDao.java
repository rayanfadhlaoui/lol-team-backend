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

	/** 
	 * Returns a list of {@link GamesToImportEntity} with the status 'Waiting' 
	 * for a given number of rows.
	 * 
	 * @param nbRows 
	 * 		The number of rows to be returned
	 * 
	 * @return A list of {@link GamesToImportEntity}
	 * */
	public List<GamesToImportEntity> findGamesToImport(int nbRows) {
		return em.createNamedQuery("GamesToImportEntity.findGamesToImport", GamesToImportEntity.class)
				.setMaxResults(nbRows)
				.getResultList();
	}

}
