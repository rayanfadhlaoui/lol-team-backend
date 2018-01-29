package com.lolteam.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity(name = "SummonerEntity")
@Table(name = "summoner")
@NamedQueries({
	@NamedQuery(name = "summoner.getSummonerEntityByAccountId", query = "SELECT s FROM SummonerEntity s WHERE s.accountId = :accountId"),
	@NamedQuery(name = "summoner.getSummonerEntityBySummonerName", query = "SELECT s FROM SummonerEntity s WHERE s.name = :summonerName"),
})
public class SummonerEntity implements GenericEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name="account_id")
	private long accountId;
	
	private String name;

	@Override
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SummonerEntity) {
			SummonerEntity summonerEntity = (SummonerEntity) obj;
			return id == summonerEntity.id;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) (id * 31);
	}
}
