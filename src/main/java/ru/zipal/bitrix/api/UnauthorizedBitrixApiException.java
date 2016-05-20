package ru.zipal.bitrix.api;

public class UnauthorizedBitrixApiException extends BitrixApiHttpException {
    public UnauthorizedBitrixApiException(String message, int status) {
        super(message, status);
    }
}
