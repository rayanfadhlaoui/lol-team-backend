package com.lolteam.entities.match;

public enum GameMode {
	UNKNOWN("UNKNOWN"),
	ARAM("ARAM"),
	STARGUARDIAN("STARGUARDIAN"),
	DOOMBOTSTEEMO("DOOMBOTSTEEMO"),
	ODIN("ODIN"),
	PROJECT("PROJECT"),
	SNOWURF("SNOWURF"),
	KINGPORO("KINGPORO"),
	CLASSIC("CLASSIC");

	private final String value;

	GameMode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
