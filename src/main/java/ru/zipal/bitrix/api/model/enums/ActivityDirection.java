package ru.zipal.bitrix.api.model.enums;

import ru.zipal.bitrix.api.common.BitrixEnum;

public enum ActivityDirection implements BitrixEnum {
	INCOMING("1", true),
	OUTGOING("2", false);
	
	private final String id;
	private final boolean incoming;

	ActivityDirection(String id, boolean incoming) {
		this.id = id;
		this.incoming = incoming;
	}
	
	@Override
	public String getId() {
		return id;
	}

	public boolean isIncoming() {
		return incoming;
	}
}
