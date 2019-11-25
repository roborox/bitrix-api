package ru.zipal.bitrix.api.model;

import lombok.Data;
import ru.zipal.bitrix.api.common.FieldName;
import ru.zipal.bitrix.api.model.enums.CommunicationType;

@Data
public class BitrixCommunication {
    private String value;
    @FieldName("VALUE_TYPE")
    private CommunicationType type = CommunicationType.WORK;

    public BitrixCommunication() {
    }

    public BitrixCommunication(String value) {
        this.value = value;
    }
}
