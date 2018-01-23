package com.lolteam.entities.match;

public enum GameMode {
	CLASSIC("CLASSIC");

	private final String value;

	GameMode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
