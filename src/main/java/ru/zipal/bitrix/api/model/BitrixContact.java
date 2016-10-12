package ru.zipal.bitrix.api.model;

import ru.zipal.bitrix.api.common.FieldName;
import ru.zipal.bitrix.api.model.enums.ContactSource;
import ru.zipal.bitrix.api.model.enums.ContactType;
import ru.zipal.bitrix.api.model.enums.YesNo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private List<BitrixCommunication> phones;
	@FieldName("EMAIL")
	private List<BitrixCommunication> emails;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public YesNo getOpened() {
		return opened;
	}

	public void setOpened(YesNo opened) {
		this.opened = opened;
	}

	public ContactType getType() {
		return type;
	}

	public void setType(ContactType type) {
		this.type = type;
	}

	public ContactSource getSource() {
		return source;
	}

	public void setSource(ContactSource source) {
		this.source = source;
	}

	public Long getResponsible() {
		return responsible;
	}

	public void setResponsible(Long responsible) {
		this.responsible = responsible;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<BitrixCommunication> getPhones() {
		if (phones == null) {
			phones = new ArrayList<>();
		}
		return phones;
	}

	public void setPhones(List<BitrixCommunication> phones) {
		this.phones = phones;
	}

	public List<BitrixCommunication> getEmails() {
		if (emails == null) {
			emails = new ArrayList<>();
		}
		return emails;
	}

	public void setEmails(List<BitrixCommunication> emails) {
		this.emails = emails;
	}

	public Long getLeadId() {
		return leadId;
	}

	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "BitrixContact{" +
				"id=" + id +
				", name='" + name + '\'' +
				", lastName=" + lastName +
				'}';
	}
}
