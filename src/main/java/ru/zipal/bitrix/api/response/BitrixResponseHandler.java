package ru.zipal.bitrix.api.response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BitrixResponseHandler implements ResponseHandler<BitrixResponse> {
    public static final Logger logger = LoggerFactory.getLogger(BitrixResponseHandler.class);

    private final boolean logResponse;

    public BitrixResponseHandler(boolean logResponse) {
        this.logResponse = logResponse;
    }

    @Override
    public BitrixResponse handleResponse(HttpResponse response) throws IOException {
        try {
            final int status = response.getStatusLine().getStatusCode();
            final String content = EntityUtils.toString(response.getEntity());
            if (logResponse) {
                logger.info(content);
            }
            if (status >= 300) {
                return new ErrorBitrixResponse(status, content);
            } else {
                return new SuccessBitrixResponse(new JSONObject(content));
            }
        } finally {
            EntityUtils.consume(response.getEntity());
        }
    }
}
