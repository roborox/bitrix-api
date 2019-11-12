package ru.zipal.bitrix.api;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.List;

public interface BitrixClient
{
    JSONObject execute(String domain, String method, List<NameValuePair> params, Tokens tokens) throws BitrixApiException;
    JSONObject execute(String domain, String method, JSONObject params, Tokens tokens) throws BitrixApiException;
}
