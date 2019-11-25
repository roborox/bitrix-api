package ru.zipal.bitrix.api;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.List;

public interface BitrixClient {
    JSONObject execute(String domain, String method, List<NameValuePair> params) throws BitrixApiException;

    JSONObject execute(String domain, String method, JSONObject params) throws BitrixApiException;
}
