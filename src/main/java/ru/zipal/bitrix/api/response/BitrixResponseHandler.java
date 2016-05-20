package ru.zipal.bitrix.api.response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class BitrixResponseHandler implements ResponseHandler<BitrixResponse> {
    @Override
    public BitrixResponse handleResponse(HttpResponse response) throws IOException {
        try {
            final int status = response.getStatusLine().getStatusCode();
            if (status >= 300) {
                return new ErrorBitrixResponse(status, EntityUtils.toString(response.getEntity()));
            } else {
                return new SuccessBitrixResponse(new JSONObject(EntityUtils.toString(response.getEntity())));
            }
        } finally {
            EntityUtils.consume(response.getEntity());
        }
    }
}
