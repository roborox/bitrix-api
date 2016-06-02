package ru.zipal.bitrix.api.model.enums;

import ru.zipal.bitrix.api.common.BitrixEnum;

public enum ContactSource implements BitrixEnum {
    ZIPAL("9"),
    ASSIS("10"),
    JCAT("11"),
    FEEDS("12"),
    GBN24("13"),
    REALTOR_ASSIST("15");

    private final String id;

    ContactSource(String id) {
        this.id = id;
    }

    @Override
	public String getId() {
        return id;
    }
}
