package ru.zipal.bitrix.api.model.enums;

import ru.zipal.bitrix.api.common.BitrixEnum;

public enum OwnerType implements BitrixEnum {
	LEAD("1"),
	DEAL("2"),
	CONTACT("3"),
	COMPANY("4");
	
	private final String id;

	OwnerType(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}
}
