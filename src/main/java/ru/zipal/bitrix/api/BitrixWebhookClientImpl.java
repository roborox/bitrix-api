package ru.zipal.bitrix.api;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.List;

public class BitrixWebhookClientImpl implements BitrixClient {
    private static final String URL_FORMAT = "https://%s/rest/%s/%s/%s";

    private String whUserId;
    private String secretKey;
    private BitrixHttpClient httpClient;

    public BitrixWebhookClientImpl(BitrixHttpClient httpClient, String whUserId, String secretKey) {
        this.whUserId = whUserId;
        this.secretKey = secretKey;
        this.httpClient = httpClient;
    }

    @Override
    public JSONObject execute(String domain, String method, List<NameValuePair> params, Tokens tokens) throws BitrixApiException {
        return httpClient.post(getUrl(domain, method), params);
    }

    @Override
    public JSONObject execute(String domain, String method, JSONObject params, Tokens tokens) throws BitrixApiException {
        return httpClient.post(getUrl(domain, method), params);
    }

    private String getUrl(String domain, String method) {
        return String.format(URL_FORMAT, domain, whUserId, secretKey, method);
    }
}
