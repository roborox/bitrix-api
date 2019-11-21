package ru.zipal.bitrix.api.model;

import lombok.Data;
import ru.zipal.bitrix.api.common.FieldName;
import ru.zipal.bitrix.api.model.enums.YesNo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BitrixLead implements HasId {
	private Long id;
	private YesNo opened = YesNo.YES;
	private String name;
	private String title;
	@FieldName("ASSIGNED_BY_ID")
	private Long responsible;
	@FieldName("PHONE")
	private List<BitrixCommunication> phones = new ArrayList<>();
	@FieldName("EMAIL")
	private List<BitrixCommunication> emails = new ArrayList<>();
	@FieldName("SOURCE_ID")
	private String sourceId = "CALL";
	@FieldName("SOURCE_DESCRIPTION")
	private String sourceDescription;
	@FieldName("DATE_MODIFY")
	private Date updated;
	private String comments;
}
