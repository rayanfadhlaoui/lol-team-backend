package com.lolteam.entities.treatment;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lolteam.entities.GenericEntity;
import com.lolteam.entities.LolTeamUserEntity;

@Entity(name = "GamesToImportEntity")
@Table(name = "games_to_import")
@NamedQueries({
	@NamedQuery(name = "GamesToImportEntity.findGamesToImport", query = "SELECT g FROM GamesToImportEntity g WHERE g.importStatus = com.lolteam.entities.treatment.ImportStatus.WAITING"),
})
public class GamesToImportEntity implements GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "user_id")
	private LolTeamUserEntity lolTeamUserEntity;

	@Column(name = "match_id", unique=true)
	private long matchId;
	
	@Enumerated(EnumType.STRING)
	@Column(name="import_status")
	private ImportStatus importStatus;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public LolTeamUserEntity getLolTeamUserEntity() {
		return lolTeamUserEntity;
	}

	public void setLolTeamUserEntity(LolTeamUserEntity lolTeamUserEntity) {
		this.lolTeamUserEntity = lolTeamUserEntity;
	}

	public long getMatchId() {
		return matchId;
	}

	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}

	public ImportStatus getImportStatus() {
		return importStatus;
	}

	public void setImportStatus(ImportStatus importStatus) {
		this.importStatus = importStatus;
	}
}
