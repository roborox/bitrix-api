package ru.zipal.bitrix.api;

import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zipal.bitrix.api.response.BitrixResponse;
import ru.zipal.bitrix.api.response.BitrixResponseHandler;
import ru.zipal.bitrix.api.response.ErrorBitrixResponse;
import ru.zipal.bitrix.api.response.SuccessBitrixResponse;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class BitrixHttpClient {
    public static final Logger logger = LoggerFactory.getLogger(BitrixHttpClient.class);

    public static final int TIMEOUT = 30000;
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private JSONObject execute(Request request) throws BitrixApiException {
        try {
            final BitrixResponse response = request.socketTimeout(TIMEOUT).connectTimeout(TIMEOUT).execute().handleResponse(new BitrixResponseHandler());
            if (response instanceof SuccessBitrixResponse) {
                return ((SuccessBitrixResponse) response).getResult();
            } else if (response instanceof ErrorBitrixResponse){
                if (((ErrorBitrixResponse) response).getStatus() == 401 || ((ErrorBitrixResponse) response).getStatus() == 403) {
                    throw new UnauthorizedBitrixApiException(((ErrorBitrixResponse) response).getBody(), ((ErrorBitrixResponse) response).getStatus());
                } else {
                    throw new BitrixApiHttpException(((ErrorBitrixResponse) response).getBody(), ((ErrorBitrixResponse) response).getStatus());
                }
            } else {
                throw new BitrixApiException("Unkown response type: " + response.getClass());
            }
        } catch (IOException e) {
            throw new BitrixApiException(e);
        }
    }

    public JSONObject post(String url, List<NameValuePair> params) throws BitrixApiException {
        logger.info("post {} with {}", url, params);
        return execute(Request.Post(url).bodyForm(params, UTF_8));
    }

    public JSONObject get(String url) throws BitrixApiException {
        try {
            return execute(Request.Get(url));
        } catch (BitrixApiHttpException e) {
            if (e.getStatus() == 400) {
                throw new UnauthorizedBitrixApiException(e.getMessage(), e.getStatus());
            } else {
                throw e;
            }
        }
    }
}
