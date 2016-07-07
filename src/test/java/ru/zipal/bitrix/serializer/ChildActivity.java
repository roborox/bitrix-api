package ru.zipal.bitrix.serializer;

import ru.zipal.bitrix.api.common.FieldName;
import ru.zipal.bitrix.api.model.BitrixActivity;

public class ChildActivity extends BitrixActivity {
    @FieldName("UF_CRM_1439291710")
    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
