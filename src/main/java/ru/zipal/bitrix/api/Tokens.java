package ru.zipal.bitrix.api;

public interface Tokens {
    void updateTokens(String accessToken, String refreshToken);
    String getAccessToken();
    String getRefreshToken();
}
