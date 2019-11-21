package ru.zipal.bitrix.api.model;

import lombok.Data;
import ru.zipal.bitrix.api.common.FieldName;

import java.util.List;

@Data
public class BitrixUser implements HasId {
    private Long id;
    private String name;
    @FieldName("LAST_NAME")
    private String lastName;
    @FieldName("UF_DEPARTMENT")
    private List<Long> departments;
    private boolean active;
}
