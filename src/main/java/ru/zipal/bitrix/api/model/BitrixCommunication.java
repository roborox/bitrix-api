package ru.zipal.bitrix.api.model;

import ru.zipal.bitrix.api.common.FieldName;
import ru.zipal.bitrix.api.model.enums.CommunicationType;

public class BitrixCommunication {
	private String value;
	@FieldName("VALUE_TYPE")
	private CommunicationType type = CommunicationType.WORK;

	public BitrixCommunication() {
	}
	
	public BitrixCommunication(String value) {
		this.value = value;
	}
	
	public BitrixCommunication(String value, CommunicationType type) {
		this.value = value;
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CommunicationType getType() {
		return type;
	}

	public void setType(CommunicationType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "BitrixCommunication{" +
				"value='" + value + '\'' +
				", type=" + type +
				'}';
	}
}
