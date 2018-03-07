package com.lolteam.entities.match;

import java.util.HashMap;
import java.util.Map;

public enum QueueEnum {
	UNKNOWN(-1),
	CUSTOM_GAMES(0),
	BLIND_PICK_5V5(2),
	RANKED_SOLO_5V5(4),
	RANKED_PREMADE_5V5(6),
	DRAFT_PICK_5V5(14),
	CO_OP_VS_AI_BEGINNER_BOT(32),
	CO_OP_VS_AI_INTERMEDIATE_BOT(33),
	ONE_FOR_ALL(70),
	SNOWDOWN_SHOWDOW_1V1(72),
	SNOWDOWN_SHOWDOW_2V2(73),
	HEXAKILL_6V6(75),
	ULTRA_RAPID_FIRE(76),
	ONE_FOR_ALL_MIRROR_MODE(78),
	CO_OP_VS_AI_ULTRA_RAPID_FIRE(83),
	DRAFT_PICK_5V5_V2(400),
	RANKED_DYNAMIC_5V5(410),
	RANKED_SOLO_5V5_V2(420),
	BLIND_PICK_5V5_V2(430),
	RANKED_FLEX_5V5(440),
	ARAM_5V5(65),
	ARAM_5V5_V2(450);

	private int value;
	private static Map<Integer, QueueEnum> map = new HashMap<>();

	private QueueEnum(int value) {
		this.value = value;
	}

	static {
		for (QueueEnum pageType : QueueEnum.values()) {
			map.put(pageType.value, pageType);
		}
	}

	public static QueueEnum valueOf(int queueType) {
		QueueEnum queueEnum = map.get(queueType);
		if(queueEnum == null) {
			throw new IllegalArgumentException();
		}
		return queueEnum;
	}

	public int getValue() {
		return value;
	}
}
