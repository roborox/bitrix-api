package ru.zipal.bitrix.serializer;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.NameValuePair;
import org.json.JSONObject;
import org.junit.Test;
import ru.zipal.bitrix.api.BitrixApiException;
import ru.zipal.bitrix.api.model.BitrixActivity;
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
}
