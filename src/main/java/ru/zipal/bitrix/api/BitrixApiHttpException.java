package ru.zipal.bitrix.api;

public class BitrixApiHttpException extends BitrixApiException {
    private final int status;

    public BitrixApiHttpException(String message, int status) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return "status=" + status + " message=" + super.getMessage();
    }
}
