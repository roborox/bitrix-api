package ru.zipal.bitrix.api.serialize;

import org.apache.commons.beanutils.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class BitrixDateConverter implements Converter {
    public static final TimeZone MSK_TIME_ZONE = TimeZone.getTimeZone("Europe/Moscow");

    @Override
    public Object convert(Class type, Object value) {
        try {
            final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+03:00'");
            fmt.setTimeZone(MSK_TIME_ZONE);
            final String stringValue = (String) value;
            if (org.apache.commons.lang3.StringUtils.isEmpty(stringValue)) {
                return null;
            } else {
                return fmt.parse(stringValue);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
