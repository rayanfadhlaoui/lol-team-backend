package com.lolteam.utils.enums;

public enum ImportStatus {
	WAITING("Waiting"),
	SUCCESS("Success"),
	ERROR("Error");
	
	private final String value;

	ImportStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
