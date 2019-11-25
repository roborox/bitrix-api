package ru.zipal.bitrix.api.model;

import lombok.Data;
import ru.zipal.bitrix.api.common.FieldName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BitrixCompany implements HasId {
    private Long id;
    private String name;
    @FieldName("DATE_CREATE")
    private Date created;
    @FieldName("DATE_MODIFY")
    private Date updated;
    @FieldName("LEAD_ID")
    private Long leadId;
    @FieldName("PHONE")
    private List<BitrixCommunication> phones = new ArrayList<>();
    @FieldName("ASSIGNED_BY_ID")
    private Long responsible;
    @FieldName("EMAIL")
    private List<BitrixCommunication> emails = new ArrayList<>();
}
