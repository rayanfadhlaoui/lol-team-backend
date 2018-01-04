package com.destiny.entities.fight;

public enum TitleEnum {
	KING("King"),
	GENERAL("General"),
	COLONEL("Colonel"),
	CORPORAL("Corporal"), 
	MAJOR("Major"),
	CAPTAIN("Captain"),
	LIEUTENANT("Lieutenant"),
	SERGEANT("Sergeant"), 
	ELITE_KNIGHT("Elite Knight"),
	KNIGHT("Knight"),
	ELITE_SQUIRE("Elite_squire"),
	SQUIRE("Squire"),
	ELITE_SOLDIER("Elite Soldier"),
	SOLDIER("Soldier"),
	NOVICE("Novice");
	
	private String value;

	TitleEnum(String value) {
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
