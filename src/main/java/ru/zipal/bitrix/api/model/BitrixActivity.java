package ru.zipal.bitrix.api.model;

import ru.zipal.bitrix.api.common.FieldName;
import ru.zipal.bitrix.api.model.enums.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BitrixActivity implements HasId {
	public static final Pattern DURATION_PATTERN = Pattern.compile("Длительность звонка:\\s*((\\d+)\\s*мин)?\\s*((\\d+)\\sсек)?");

	private Long id;
	private String subject;
	private List<BitrixCommunication> communications;
	private Date created;
	@FieldName("LAST_UPDATED")
	private Date startTime;
	@FieldName("START_TIME")
	private Date endTime;
	@FieldName("END_TIME")
	private Date updated;
	@FieldName("OWNER_ID")
	private Long ownerId;
	@FieldName("OWNER_TYPE_ID")
	private OwnerType ownerType = OwnerType.CONTACT;
	private ActivityPriority priority = ActivityPriority.AVERAGE;
	private String description;
	@FieldName("DESCRIPTION_TYPE")
	private ContentType descriptionType = ContentType.BBCODE;
	@FieldName("RESPONSIBLE_ID")
	private Long responsible;
	@FieldName("TYPE_ID")
	private ActivityType type = ActivityType.CALL;
	private YesNo completed = YesNo.NO;
	private ActivityDirection direction = ActivityDirection.OUTGOING;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public ActivityDirection getDirection() {
		return direction;
	}
	
	public void setDirection(ActivityDirection direction) {
		this.direction = direction;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<BitrixCommunication> getCommunications() {
		if (communications == null) {
			communications = new ArrayList<>();
		}
		return communications;
	}

	public void setCommunications(List<BitrixCommunication> communications) {
		this.communications = communications;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public OwnerType getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(OwnerType ownerType) {
		this.ownerType = ownerType;
	}

	public ActivityPriority getPriority() {
		return priority;
	}

	public void setPriority(ActivityPriority priority) {
		this.priority = priority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ContentType getDescriptionType() {
		return descriptionType;
	}

	public void setDescriptionType(ContentType descriptionType) {
		this.descriptionType = descriptionType;
	}

	public Long getResponsible() {
		return responsible;
	}

	public void setResponsible(Long responsible) {
		this.responsible = responsible;
	}

	public ActivityType getType() {
		return type;
	}

	public void setType(ActivityType type) {
		this.type = type;
	}

	public YesNo getCompleted() {
		return completed;
	}

	public void setCompleted(YesNo completed) {
		this.completed = completed;
	}

	public Integer getDuration() {
		if (description == null) return null;
		final Matcher m = DURATION_PATTERN.matcher(description);
		if (m.find()) {
			int minutes = 0;
			try {
				minutes = Integer.parseInt(m.group(2));
			} catch (Exception ignore) {
			}
			int seconds = 0;
			try {
				seconds = Integer.parseInt(m.group(4));
			} catch (Exception ignore) {
			}
			return 60 * minutes + seconds;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "BitrixActivity{" +
				"id=" + id +
				", subject='" + subject + '\'' +
				", communications=" + communications +
				", created=" + created +
				", updated=" + updated +
				", ownerId=" + ownerId +
				", ownerType=" + ownerType +
				", priority=" + priority +
				", description='" + description + '\'' +
				", descriptionType=" + descriptionType +
				", responsible=" + responsible +
				", type=" + type +
				", completed=" + completed +
				", direction=" + direction +
				'}';
	}
}
