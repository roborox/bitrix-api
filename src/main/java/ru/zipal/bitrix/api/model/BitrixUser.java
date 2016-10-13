package ru.zipal.bitrix.api.model;

import ru.zipal.bitrix.api.common.FieldName;

import java.util.List;

public class BitrixUser implements HasId {
	private Long id;
	private String name;
	@FieldName("LAST_NAME")
	private String lastName;
	@FieldName("UF_DEPARTMENT")
	private List<Long> departments;
	private boolean active;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Long> departments) {
		this.departments = departments;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "BitrixUser{" +
				"id=" + id +
				", name='" + name + '\'' +
				", lastName='" + lastName + '\'' +
				", departments=" + departments +
				", active=" + active +
				'}';
	}
}
