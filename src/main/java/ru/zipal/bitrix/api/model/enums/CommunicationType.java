package ru.zipal.bitrix.api.model.enums;

import ru.zipal.bitrix.api.common.BitrixEnum;

public enum CommunicationType implements BitrixEnum {
	WORK("WORK");
	
	private final String id;

	CommunicationType(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}
}
