package ru.zipal.bitrix.api.model.enums;

import ru.zipal.bitrix.api.common.BitrixEnum;

public enum ActivityType implements BitrixEnum {
	MEETING("1"),
	CALL("2"),
	TASK("3"),
	EMAIL("4"),
	ACTION("4");
	
	private final String id;

	ActivityType(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}
}
