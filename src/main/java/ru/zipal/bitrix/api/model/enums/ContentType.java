package ru.zipal.bitrix.api.model.enums;

import ru.zipal.bitrix.api.common.BitrixEnum;

public enum ContentType implements BitrixEnum {
	PLAIN_TEXT("1"),
	BBCODE("2"),
	HTML("3");
	
	private final String id;

	ContentType(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}
}
