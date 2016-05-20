package ru.zipal.bitrix.api.response;

import org.json.JSONObject;

public class SuccessBitrixResponse implements BitrixResponse {
    private final JSONObject result;

    public SuccessBitrixResponse(JSONObject result) {
        this.result = result;
    }

    public JSONObject getResult() {
        return result;
    }
}
