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

    public <T extends BitrixContact> BitrixPage<T> listContacts(Class<T> contactClass, String domain, Tokens tokens, Integer start, String phone) throws BitrixApiException {
        final List<NameValuePair> params = new ArrayList<>();
        if (start != null) {
            params.add(new BasicNameValuePair("start", start.toString()));
        }
        if (phone != null) {
            params.add(new BasicNameValuePair("filter[PHONE]", phone));
        }
        return getPage(client.execute(domain, "crm.contact.list", params, tokens), contactClass);
    }

    public BitrixPage<BitrixContact> listContacts(String domain, Tokens tokens, Integer start, String phone) throws BitrixApiException {
        return listContacts(BitrixContact.class, domain, tokens, start, phone);
    }

    public BitrixPage<BitrixLead> listLeads(String domain, Tokens tokens, Integer start, String phone) throws BitrixApiException {
        final List<NameValuePair> params = new ArrayList<>();
        if (start != null) {
            params.add(new BasicNameValuePair("start", start.toString()));
        }
        if (phone != null) {
            params.add(new BasicNameValuePair("filter[PHONE]", phone));
        }
        return getPage(client.execute(domain, "crm.lead.list", params, tokens), BitrixLead.class);
    }

    public BitrixPage<BitrixUser> getUsers(String domain, Tokens tokens, Integer start) throws BitrixApiException {
        final List<NameValuePair> params = new ArrayList<>();
        if (start != null) {
            params.add(new BasicNameValuePair("start", start.toString()));
        }
        return getPage(client.execute(domain, "user.get", params, tokens), BitrixUser.class);
    }

    public <T extends BitrixContact> T getContact(Class<T> contactClass, String domain, Tokens tokens, long id) throws BitrixApiException {
        return serializer.deserialize(contactClass, client.execute(domain, "crm.contact.get", Collections.singletonList(new BasicNameValuePair("id", Long.toString(id))), tokens).getJSONObject("result"));
    }

    public BitrixContact getContact(String domain, Tokens tokens, long id) throws BitrixApiException {
        return getContact(BitrixContact.class, domain, tokens, id);
    }

    public void removeContact(String domain, Tokens tokens, long id) throws BitrixApiException {
        client.execute(domain, "crm.contact.delete", Collections.singletonList(new BasicNameValuePair("id", Long.toString(id))), tokens);
    }

    public Long createContact(String domain, Tokens tokens, BitrixContact contact) throws BitrixApiException {
        return client.execute(domain, "crm.contact.add", serializer.serialize(contact), tokens).getLong("result");
    }

    public void updateContact(String domain, Tokens tokens, BitrixContact contact) throws BitrixApiException {
        final List<NameValuePair> params = serializer.serialize(contact);
        params.add(new BasicNameValuePair("id", Long.toString(contact.getId())));
        client.execute(domain, "crm.contact.update", params, tokens);
    }

    public Long createLead(String domain, Tokens tokens, BitrixLead lead) throws BitrixApiException {
        return client.execute(domain, "crm.lead.add", serializer.serialize(lead), tokens).getLong("result");
    }

    public Long createActivity(String domain, Tokens tokens, BitrixActivity activity, NameValuePair... additional) throws BitrixApiException {
        final List<NameValuePair> params = serializer.serialize(activity);
        if (additional != null) {
            params.addAll(Arrays.asList(additional));
        }
        return client.execute(domain, "crm.activity.add", params, tokens).getLong("result");
    }

    public void bindEvent(String domain, Tokens tokens, String event, String handler) throws BitrixApiException {
        client.execute(domain, "event.bind", Arrays.asList(new BasicNameValuePair("event", event), new BasicNameValuePair("handler", handler)), tokens);
    }

    public void unbindEvent(String domain, Tokens tokens, String event, String handler) throws BitrixApiException {
        client.execute(domain, "event.unbind", Arrays.asList(new BasicNameValuePair("event", event), new BasicNameValuePair("handler", handler)), tokens);
    }

    public boolean isAdmin(String domain, Tokens tokens) throws BitrixApiException {
        return client.execute(domain, "user.admin", Collections.emptyList(), tokens).getBoolean("result");
    }

    private <T> BitrixPage<T> getPage(JSONObject json, Class<T> clazz) throws BitrixApiException {
        final JSONArray array = json.getJSONArray("result");
        System.out.println(json.toString());
        return new BitrixPage<>(json.has("next") ? json.getInt("next") : null, serializer.deserializeArray(clazz, array));
    }
}
