package ru.zipal.bitrix.api.model;

import lombok.Data;
import ru.zipal.bitrix.api.common.FieldName;
import ru.zipal.bitrix.api.model.enums.ContactSource;
import ru.zipal.bitrix.api.model.enums.ContactType;
import ru.zipal.bitrix.api.model.enums.YesNo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BitrixContact implements HasId {
	private Long id;
	private String name;
	@FieldName("LAST_NAME")
	private String lastName;
	private YesNo opened = YesNo.YES;
	@FieldName("DATE_CREATE")
	private Date created;
	@FieldName("DATE_MODIFY")
	private Date updated;
	@FieldName("TYPE_ID")
	private ContactType type = ContactType.CLIENT;
	@FieldName("SOURCE_ID")
	private ContactSource source;
	@FieldName("ASSIGNED_BY_ID")
	private Long responsible;
	@FieldName("LEAD_ID")
	private Long leadId;
	private String comments;
	@FieldName("PHONE")
	private List<BitrixCommunication> phones = new ArrayList<>();
	@FieldName("EMAIL")
	private List<BitrixCommunication> emails = new ArrayList<>();
}
