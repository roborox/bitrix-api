package ru.zipal.bitrix.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.zipal.bitrix.api.model.*;
import ru.zipal.bitrix.api.serialize.Serializer;

import java.util.*;

@SuppressWarnings("unused")
public class BitrixApi<Contact extends HasId, Activity, Lead extends HasId, User> {
    private final BitrixClient client;
    private final Serializer serializer;
    private final Tokens tokens;
    private final String domain;

    private final Class<Contact> contactClass;
    private final Class<Activity> activityClass;
    private final Class<Lead> leadClass;
    private final Class<User> userClass;

    protected BitrixApi(BitrixClient client, Serializer serializer, Tokens tokens, String domain, Class<Contact> contactClass, Class<Activity> activityClass, Class<Lead> leadClass, Class<User> userClass) {
        this.client = client;
        this.serializer = serializer;
        this.tokens = tokens;
        this.domain = domain;
        this.contactClass = contactClass;
        this.activityClass = activityClass;
        this.leadClass = leadClass;
        this.userClass = userClass;
    }

    public static BitrixApi<BitrixContact, BitrixActivity, BitrixLead, BitrixUser> createDefault(BitrixClient client, Serializer serializer, Tokens tokens, String domain) {
        return new BitrixApi<>(client, serializer, tokens, domain, BitrixContact.class, BitrixActivity.class, BitrixLead.class, BitrixUser.class);
    }

    public static <Contact extends HasId, Activity, Lead extends HasId, User> BitrixApi<Contact, Activity, Lead, User> custom(BitrixClient client, Serializer serializer, Tokens tokens, String domain, Class<Contact> contactClass, Class<Activity> activityClass, Class<Lead> leadClass, Class<User> userClass) {
        return new BitrixApi<>(client, serializer, tokens, domain, contactClass, activityClass, leadClass, userClass);
    }

    public BitrixPage<Contact> listContacts(Integer start, NameValuePair... additional) throws BitrixApiException {
        final List<NameValuePair> params = new ArrayList<>();
        if (start != null) {
            params.add(new BasicNameValuePair("start", start.toString()));
        }
        if (additional != null) {
            params.addAll(Arrays.asList(additional));
        }
        return getPage(client.execute(domain, "crm.contact.list", params, tokens), contactClass);
    }

    public Contact getContact(long id) throws BitrixApiException {
        return serializer.deserialize(contactClass, client.execute(domain, "crm.contact.get", Collections.singletonList(new BasicNameValuePair("id", Long.toString(id))), tokens).getJSONObject("result"));
    }

    public void removeContact(long id) throws BitrixApiException {
        client.execute(domain, "crm.contact.delete", Collections.singletonList(new BasicNameValuePair("id", Long.toString(id))), tokens);
    }

    public Long createContact(Contact contact) throws BitrixApiException {
        return client.execute(domain, "crm.contact.add", serializer.serialize(contact), tokens).getLong("result");
    }

    public void updateContact(Contact contact) throws BitrixApiException {
        final List<NameValuePair> params = serializer.serialize(contact);
        params.add(new BasicNameValuePair("id", Long.toString(contact.getId())));
        client.execute(domain, "crm.contact.update", params, tokens);
    }

    public BitrixPage<Lead> listLeads(Integer start, NameValuePair... additional) throws BitrixApiException {
        final List<NameValuePair> params = new ArrayList<>();
        if (start != null) {
            params.add(new BasicNameValuePair("start", start.toString()));
        }
        if (additional != null) {
            params.addAll(Arrays.asList(additional));
        }
        return getPage(client.execute(domain, "crm.lead.list", params, tokens), leadClass);
    }

    public Long createLead(Lead lead) throws BitrixApiException {
        return client.execute(domain, "crm.lead.add", serializer.serialize(lead), tokens).getLong("result");
    }

    public void updateLead(Lead lead) throws BitrixApiException {
        final List<NameValuePair> params = serializer.serialize(lead);
        params.add(new BasicNameValuePair("id", Long.toString(lead.getId())));
        client.execute(domain, "crm.lead.update", params, tokens);
    }

    public BitrixPage<User> listUsers(Integer start) throws BitrixApiException {
        final List<NameValuePair> params = new ArrayList<>();
        if (start != null) {
            params.add(new BasicNameValuePair("start", start.toString()));
        }
        return getPage(client.execute(domain, "user.get", params, tokens), userClass);
    }

    public BitrixPage<Activity> listActivities(Integer start, NameValuePair... additional) throws BitrixApiException {
        final List<NameValuePair> params = new ArrayList<>();
        if (start != null) {
            params.add(new BasicNameValuePair("start", start.toString()));
        }
        if (additional != null) {
            params.addAll(Arrays.asList(additional));
        }
        return getPage(client.execute(domain, "crm.activity.list", params, tokens), activityClass);
    }

    public Long createActivity(Activity activity, NameValuePair... additional) throws BitrixApiException {
        final List<NameValuePair> params = serializer.serialize(activity);
        if (additional != null) {
            params.addAll(Arrays.asList(additional));
        }
        return client.execute(domain, "crm.activity.add", params, tokens).getLong("result");
    }

    public void bindEvent(String event, String handler) throws BitrixApiException {
        client.execute(domain, "event.bind", Arrays.asList(new BasicNameValuePair("event", event), new BasicNameValuePair("handler", handler)), tokens);
    }

    public void unbindEvent(String event, String handler) throws BitrixApiException {
        client.execute(domain, "event.unbind", Arrays.asList(new BasicNameValuePair("event", event), new BasicNameValuePair("handler", handler)), tokens);
    }

    public boolean isAdmin(String domain, Tokens tokens) throws BitrixApiException {
        return client.execute(domain, "user.admin", Collections.emptyList(), tokens).getBoolean("result");
    }

    private <T> BitrixPage<T> getPage(JSONObject json, Class<T> clazz) throws BitrixApiException {
        final JSONArray array = json.getJSONArray("result");
        return new BitrixPage<>(json.has("next") ? json.getInt("next") : null, serializer.deserializeArray(clazz, array));
    }
}
