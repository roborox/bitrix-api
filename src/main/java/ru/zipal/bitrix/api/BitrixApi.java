package ru.zipal.bitrix.api;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.zipal.bitrix.api.model.*;
import ru.zipal.bitrix.api.model.enums.EntityType;
import ru.zipal.bitrix.api.serialize.Serializer;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class BitrixApi<User, Activity, Contact extends HasId, Lead extends HasId, Company extends HasId> {
    private final BitrixClient client;
    private final Serializer serializer;
    private final Tokens tokens;
    private final String domain;

    private final Class<User> userClass;
    private final Class<Activity> activityClass;
    private final Class<Contact> contactClass;
    private final Class<Lead> leadClass;
    private final Class<Company> companyClass;

    protected BitrixApi(BitrixClient client, Serializer serializer, Tokens tokens, String domain, Class<User> userClass, Class<Activity> activityClass, Class<Contact> contactClass, Class<Lead> leadClass, Class<Company> companyClass) {
        this.client = client;
        this.serializer = serializer;
        this.tokens = tokens;
        this.domain = domain;
        this.contactClass = contactClass;
        this.activityClass = activityClass;
        this.leadClass = leadClass;
        this.userClass = userClass;
        this.companyClass = companyClass;
    }

    public static BitrixApi<BitrixUser, BitrixActivity, BitrixContact, BitrixLead, BitrixCompany> createDefault(BitrixClient client, Serializer serializer, Tokens tokens, String domain) {
        return new BitrixApi<>(client, serializer, tokens, domain, BitrixUser.class, BitrixActivity.class, BitrixContact.class, BitrixLead.class, BitrixCompany.class);
    }

    public static <User, Activity, Contact extends HasId, Lead extends HasId, Company extends HasId> BitrixApi<User, Activity, Contact, Lead, Company> custom(BitrixClient client, Serializer serializer, Tokens tokens, String domain, Class<User> userClass, Class<Activity> activityClass, Class<Contact> contactClass, Class<Lead> leadClass, Class<Company> companyClass) {
        return new BitrixApi<>(client, serializer, tokens, domain, userClass, activityClass, contactClass, leadClass, companyClass);
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

    public Map<Long, Contact> getContacts(Collection<Long> ids) throws BitrixApiException {
        return getBatch(contactClass, ids, "crm.contact.get");
    }

    public Map<Long, Lead> getLeads(Collection<Long> ids) throws BitrixApiException {
        return getBatch(leadClass, ids, "crm.lead.get");
    }

    public Map<Long, Company> getCompanies(Collection<Long> ids) throws BitrixApiException {
        return getBatch(companyClass, ids, "crm.company.get");
    }

    protected <T extends HasId> Map<Long, T> getBatch(Class<T> clazz, Collection<Long> ids, String method) throws BitrixApiException {
        final JSONObject json = client.execute(domain,"batch", ids.stream().map(id -> new BasicNameValuePair("cmd[e_" + id + "]", method + "?ID=" + id)).collect(Collectors.toList()), tokens);
        final JSONObject result = json.getJSONObject("result").getJSONObject("result");
        final HashMap<Long, T> map = new HashMap<>();
        for (Long id : ids) {
            if (result.has("e_" + id)) {
                map.put(id, serializer.deserialize(clazz, result.getJSONObject("e_" + id)));
            }
        }
        return map;
    }

    protected Class<?> getEntityClass(EntityType entityType) {
        switch (entityType) {
            case USER:
                return userClass;
            case ACTIVITY:
                return activityClass;
            case CONTACT:
                return contactClass;
            case LEAD:
                return leadClass;
            case COMPANY:
                return companyClass;
        }
        throw new IllegalArgumentException("not supported: " + entityType);
    }

    protected Map<Long, Object> getBatch(Collection<Pair<EntityType, Long>> whatToLoad) throws BitrixApiException {
        final JSONObject json = client.execute(domain,"batch", whatToLoad.stream().map(pair -> new BasicNameValuePair("cmd[e_" + pair.getValue() + "]", pair.getKey().getGetMethod() + "?ID=" + pair.getValue())).collect(Collectors.toList()), tokens);
        final JSONObject result = json.getJSONObject("result").getJSONObject("result");
        final HashMap<Long, Object> map = new HashMap<>();
        for (Pair<EntityType, Long> pair : whatToLoad) {
            if (result.has("e_" + pair.getValue())) {
                map.put(pair.getValue(), serializer.deserialize(getEntityClass(pair.getKey()), result.getJSONObject("e_" + pair.getValue())));
            }
        }
        return map;
    }

    public Contact getContact(long id) throws BitrixApiException {
        return serializer.deserialize(contactClass, client.execute(domain, "crm.contact.get", Collections.singletonList(new BasicNameValuePair("id", Long.toString(id))), tokens).getJSONObject("result"));
    }

    public Lead getLead(long id) throws BitrixApiException {
        return serializer.deserialize(leadClass, client.execute(domain, "crm.lead.get", Collections.singletonList(new BasicNameValuePair("id", Long.toString(id))), tokens).getJSONObject("result"));
    }

    public Company getCompany(long id) throws BitrixApiException {
        return serializer.deserialize(companyClass, client.execute(domain, "crm.company.get", Collections.singletonList(new BasicNameValuePair("id", Long.toString(id))), tokens).getJSONObject("result"));
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

    public Activity getActivity(long id) throws BitrixApiException {
        return serializer.deserialize(activityClass, client.execute(domain, "crm.activity.get", Collections.singletonList(new BasicNameValuePair("id", Long.toString(id))), tokens).getJSONObject("result"));
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
