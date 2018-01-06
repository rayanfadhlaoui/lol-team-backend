package com.lolteam.entities;

public class SummonerEntity implements GenericEntity{
	
	private Integer	id;

	@Override
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

}
