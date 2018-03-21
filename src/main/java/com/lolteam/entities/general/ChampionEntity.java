package com.lolteam.entities.general;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity(name = "ChampionEntity")
@Table(name = "champion")
@NamedQueries({
	@NamedQuery(name = "champion.getChampionFromChampionId", query = "SELECT c FROM ChampionEntity c WHERE c.championId = :championId"),
})
public class ChampionEntity implements GenericEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "champion_id")
	private int championId;

	@Column(name = "champion_name")
	private String championName;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * External ID provided by the Riot API.
	 * 
	 * @return The champion ID provided by the Riot API.
	 * 
	 * @see net.rithms.riot.api.Dto.Champion
	 */
	public int getChampionId() {
		return championId;
	}

	public void setChampionId(int championId) {
		this.championId = championId;

	}

	public void setChampionName(String championName) {
		this.championName = championName;
	}
	
	public String getChampionName() {
		return championName;
	}

	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ChampionEntity) {
			ChampionEntity championEntity = (ChampionEntity) obj;
			return id == championEntity.id;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) (id * 31);
	}

}
