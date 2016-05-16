package ru.zipal.bitrix.api.model.enums;

import ru.zipal.bitrix.api.common.BitrixEnum;

public enum YesNo implements BitrixEnum {
	YES("Y"),
	NO("N");
	
	private final String id;

	YesNo(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}
}
