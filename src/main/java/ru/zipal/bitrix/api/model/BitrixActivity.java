package ru.zipal.bitrix.api.model;

import lombok.Data;
import ru.zipal.bitrix.api.common.FieldName;
import ru.zipal.bitrix.api.model.enums.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class BitrixActivity implements HasId {
    public static final Pattern DURATION_PATTERN = Pattern.compile("Длительность звонка:\\s*((\\d+)\\s*мин)?\\s*((\\d+)\\sсек)?");

    private Long id;
    private String subject;
    private List<BitrixCommunication> communications = new ArrayList<>();
    private Date created;
    @FieldName("START_TIME")
    private Date startTime;
    @FieldName("END_TIME")
    private Date endTime;
    @FieldName("LAST_UPDATED")
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
}
