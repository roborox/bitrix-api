package ru.zipal.bitrix.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zipal.bitrix.api.common.FieldName;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BitrixDeal implements HasId {
    private Long id;
    @FieldName("BEGINDATE")
    private Date beginDate;
    @FieldName("CLOSEDATE")
    private Date closeDate;
    @FieldName("TITLE")
    private String title;
    @FieldName("COMMENTS")
    private String comment;
    @FieldName("CONTACT_ID")
    private Long contactId;
}
