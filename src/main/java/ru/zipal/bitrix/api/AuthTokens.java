package ru.zipal.bitrix.api;

public class AuthTokens implements Tokens {
    private final String token;

    public AuthTokens(String token) {
        this.token = token;
    }

    @Override
    public void updateTokens(String accessToken, String refreshToken) {

    }

    @Override
    public String getAccessToken() {
        return token;
    }

    @Override
    public String getRefreshToken() {
        return null;
    }
}
