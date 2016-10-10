package ru.zipal.bitrix.serializer;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.NameValuePair;
import org.json.JSONObject;
import org.junit.Test;
import ru.zipal.bitrix.api.BitrixApiException;
import ru.zipal.bitrix.api.model.BitrixActivity;
import ru.zipal.bitrix.api.model.BitrixUser;
import ru.zipal.bitrix.api.serialize.Serializer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;

public class SerializerTest {
    public static final String JSON_FORMAT = "{\"DESCRIPTION\": \"%s\", \"RESPONSIBLE_ID\": %s}";
    private final Serializer serializer = new Serializer();

    @Test
    public void serialize() {
        final ChildActivity activity = new ChildActivity();
        activity.setResponsible(RandomUtils.nextLong(0, Integer.MAX_VALUE));
        activity.setDescription(RandomStringUtils.randomAlphabetic(10));

        final List<NameValuePair> params = serializer.serialize(activity);
        final Map<String, String> map = params.stream().collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));

        assertEquals(map.get("fields[DESCRIPTION]"), activity.getDescription());
        assertEquals(map.get("fields[RESPONSIBLE_ID]"), Long.toString(activity.getResponsible()));
    }

    @Test
    public void deserialize() throws BitrixApiException {
        final String description = RandomStringUtils.randomAlphabetic(10);
        final long responsibleId = RandomUtils.nextLong(0, Integer.MAX_VALUE);

        final BitrixActivity activity = serializer.deserialize(ChildActivity.class, new JSONObject(String.format(JSON_FORMAT, description, responsibleId)));

        assertEquals(activity.getDescription(), description);
        assertEquals(activity.getResponsible().longValue(), responsibleId);
    }

    @Test
    public void deserializeUser() throws BitrixApiException {
        final String json = "{\"UF_SKYPE\":\"dene_bola\",\"ACTIVE\":true,\"PERSONAL_ZIP\":null,\"UF_FACEBOOK\":null,\"UF_PHONE_INNER\":null,\"EMAIL\":\"irina@zipal.ru\",\"LAST_NAME\":\"Савельева\",\"PERSONAL_STREET\":null,\"PERSONAL_PHOTO\":\"https://cdn.bitrix24.ru/b807897/main/631/631019b8d5528050cfb30542f6eba290/WSutDo4.jpg\",\"SECOND_NAME\":\"\",\"UF_SKILLS\":null,\"UF_XING\":null,\"PERSONAL_BIRTHDAY\":\"1990-12-17T03:00:00+03:00\",\"PERSONAL_PHONE\":null,\"PERSONAL_CITY\":\"Смоленск\",\"UF_WEB_SITES\":null,\"ID\":\"54\",\"PERSONAL_COUNTRY\":null,\"PERSONAL_MOBILE\":\"+79002272526\",\"WORK_COMPANY\":null,\"UF_INTERESTS\":null,\"UF_LINKEDIN\":null,\"UF_DEPARTMENT\":[7],\"WORK_POSITION\":\"\",\"WORK_PHONE\":\"\",\"NAME\":\"Ирина\",\"PERSONAL_PROFESSION\":null,\"UF_TWITTER\":null,\"UF_DISTRICT\":null,\"PERSONAL_FAX\":null,\"PERSONAL_STATE\":null,\"PERSONAL_PAGER\":null,\"PERSONAL_GENDER\":\"F\",\"PERSONAL_WWW\":\"\",\"PERSONAL_ICQ\":null}";

        final BitrixUser user = serializer.deserialize(BitrixUser.class, new JSONObject(json));

        System.out.println("result is " + user);
        assertEquals(user.getDepartments().size(), 1);
        assertEquals(user.getDepartments().get(0).longValue(), 7L);
    }
}
