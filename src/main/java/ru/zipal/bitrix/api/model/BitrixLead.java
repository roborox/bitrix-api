package ru.zipal.bitrix.api.model;

import ru.zipal.bitrix.api.common.FieldName;
import ru.zipal.bitrix.api.model.enums.YesNo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BitrixLead implements HasId {
	private Long id;
	private YesNo opened = YesNo.YES;
	private String name;
	private String title;
	@FieldName("ASSIGNED_BY_ID")
	private Long responsible;
	@FieldName("PHONE")
	private List<BitrixCommunication> phones;
	@FieldName("EMAIL")
	private List<BitrixCommunication> emails;
	@FieldName("SOURCE_ID")
	private String sourceId = "CALL";
	@FieldName("SOURCE_DESCRIPTION")
	private String sourceDescription;
	@FieldName("DATE_MODIFY")
	private Date updated;
	private String comments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public YesNo getOpened() {
		return opened;
	}

	public void setOpened(YesNo opened) {
		this.opened = opened;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getResponsible() {
		return responsible;
	}

	public void setResponsible(Long responsible) {
		this.responsible = responsible;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceDescription() {
		return sourceDescription;
	}

	public void setSourceDescription(String sourceDescription) {
		this.sourceDescription = sourceDescription;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "BitrixLead{" +
				"id=" + id +
				", name='" + name + '\'' +
				", responsible=" + responsible +
				", phones=" + phones +
				'}';
	}
}
