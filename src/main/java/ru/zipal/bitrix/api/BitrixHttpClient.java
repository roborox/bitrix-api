package ru.zipal.bitrix.api;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.fluent.Request;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class BitrixHttpClient {
    public static final Logger logger = LoggerFactory.getLogger(BitrixHttpClient.class);

    public static final int TIMEOUT = 30000;
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private JSONObject execute(Request request) throws IOException {
        return new JSONObject(request.socketTimeout(TIMEOUT).connectTimeout(TIMEOUT).execute().returnContent().asString());
    }

    public JSONObject post(String url, List<NameValuePair> params) throws BitrixApiException {
        try {
            logger.info("post {} with {}", url, params);
            return execute(Request.Post(url).bodyForm(params, UTF_8));
        } catch (HttpResponseException e) {
            if (e.getMessage().equals("Unauthorized") || e.getMessage().equals("Service Temporarily Unavailable")) {
                throw new UnauthorizedBitrixApiException(e);
            } else {
                throw new BitrixApiException(e);
            }
        } catch (IOException e) {
            throw new BitrixApiException(e);
        }
    }

    public JSONObject get(String url) throws BitrixApiException {
        try {
            return execute(Request.Get(url));
        } catch (HttpResponseException e) {
            if (e.getStatusCode() == 400) {
                throw new UnauthorizedBitrixApiException(e);
            } else {
                throw new BitrixApiException(e);
            }
        } catch (IOException e) {
            throw new BitrixApiException(e);
        }
    }
}
