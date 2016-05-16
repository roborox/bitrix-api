package ru.zipal.bitrix.api.model.enums;

import ru.zipal.bitrix.api.common.BitrixEnum;

public enum ActivityPriority implements BitrixEnum {
	LOW("1"),
	AVERAGE("2"),
	HIGH("3");
	
	private final String id;

	ActivityPriority(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}
}
