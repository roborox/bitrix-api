package ru.zipal.bitrix.api.model.enums;

import ru.zipal.bitrix.api.common.BitrixEnum;

public enum ContactType implements BitrixEnum {
	CLIENT("CLIENT"),
	PARTNER("PARTNER"),
	BOARDS("1"),
	CALLCENTER("SHARE");
	
	private final String id;

	ContactType(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}
}
