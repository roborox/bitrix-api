package ru.zipal.bitrix.api;

public class BitrixApiException extends Exception {
    public BitrixApiException(Throwable cause) {
        super(cause);
    }

    public BitrixApiException(String message) {
        super(message);
    }
}
