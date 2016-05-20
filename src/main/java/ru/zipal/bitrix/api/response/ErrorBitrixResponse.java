package ru.zipal.bitrix.api.response;

public class ErrorBitrixResponse implements BitrixResponse {
    private final int status;
    private final String body;

    public ErrorBitrixResponse(int status, String body) {
        this.status = status;
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public String getBody() {
        return body;
    }
}
