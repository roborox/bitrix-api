package ru.zipal.bitrix.api;

import ru.zipal.bitrix.api.model.BitrixActivity;
import ru.zipal.bitrix.api.model.BitrixContact;
import ru.zipal.bitrix.api.model.BitrixLead;
import ru.zipal.bitrix.api.model.BitrixUser;
import ru.zipal.bitrix.api.serialize.Serializer;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class BitrixApi {
    private final BitrixClient client;
    private final Serializer serializer;

    public BitrixApi(BitrixClient client, Serializer serializer) {
        this.client = client;
        this.serializer = serializer;
    }

    public BitrixPage<BitrixContact> listContacts(String domain, BitrixClient.Tokens tokens, Integer start, String phone) throws BitrixApiException {
        final List<NameValuePair> params = new ArrayList<>();
        if (start != null) {
            params.add(new BasicNameValuePair("start", start.toString()));
        }
        if (phone != null) {
            params.add(new BasicNameValuePair("filter[PHONE]", phone));
        }
        return getPage(client.execute(domain, "crm.contact.list", params, tokens), BitrixContact.class);
    }

    public BitrixPage<BitrixLead> listLeads(String domain, BitrixClient.Tokens tokens, Integer start, String phone) throws BitrixApiException {
        final List<NameValuePair> params = new ArrayList<>();
        if (start != null) {
            params.add(new BasicNameValuePair("start", start.toString()));
        }
        if (phone != null) {
            params.add(new BasicNameValuePair("filter[PHONE]", phone));
        }
        return getPage(client.execute(domain, "crm.lead.list", params, tokens), BitrixLead.class);
    }

    public BitrixPage<BitrixUser> getUsers(String domain, BitrixClient.Tokens tokens, Integer start) throws BitrixApiException {
        final List<NameValuePair> params = new ArrayList<>();
        if (start != null) {
            params.add(new BasicNameValuePair("start", start.toString()));
        }
        return getPage(client.execute(domain, "user.get", params, tokens), BitrixUser.class);
    }

    public void removeContact(String domain, BitrixClient.Tokens tokens, long id) throws BitrixApiException {
        client.execute(domain, "crm.contact.delete", Collections.singletonList(new BasicNameValuePair("id", Long.toString(id))), tokens);
    }

    public Long createContact(String domain, BitrixClient.Tokens tokens, BitrixContact contact) throws BitrixApiException {
        return client.execute(domain, "crm.contact.add", serializer.serialize(contact), tokens).getLong("result");
    }

    public Long createLead(String domain, BitrixClient.Tokens tokens, BitrixLead lead) throws BitrixApiException {
        return client.execute(domain, "crm.lead.add", serializer.serialize(lead), tokens).getLong("result");
    }

    public Long createActivity(String domain, BitrixClient.Tokens tokens, BitrixActivity activity, NameValuePair... additional) throws BitrixApiException {
        final List<NameValuePair> params = serializer.serialize(activity);
        if (additional != null) {
            params.addAll(Arrays.asList(additional));
        }
        return client.execute(domain, "crm.activity.add", params, tokens).getLong("result");
    }

    public void bindEvent(String domain, BitrixClient.Tokens tokens, String event, String handler) throws BitrixApiException {
        client.execute(domain, "event.bind", Arrays.asList(new BasicNameValuePair("event", event), new BasicNameValuePair("handler", handler)), tokens);
    }

    private <T> BitrixPage<T> getPage(JSONObject json, Class<T> clazz) throws BitrixApiException {
        final JSONArray array = json.getJSONArray("result");
        System.out.println(json.toString());
        return new BitrixPage<>(json.has("next") ? json.getInt("next") : null, serializer.deserializeArray(clazz, array));
    }
}
