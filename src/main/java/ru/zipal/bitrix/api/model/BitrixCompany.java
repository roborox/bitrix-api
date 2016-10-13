package ru.zipal.bitrix.api.model;

import ru.zipal.bitrix.api.common.FieldName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	@FieldName("EMAIL")
	private List<BitrixCommunication> emails = new ArrayList<>();

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
		return "BitrixCompany{" +
				"id=" + id +
				", name='" + name + '\'' +
				", phones=" + phones +
				", emails=" + emails +
				'}';
	}
}
