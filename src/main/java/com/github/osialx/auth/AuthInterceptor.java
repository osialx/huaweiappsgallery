package com.github.osialx.auth;

import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;

public class AuthInterceptor implements HttpRequestInterceptor {

    private String token;

    private String domain;
    private String clientId;
    private String clientSecret;

    private AuthTokenProvider authTokenProvider;

    public AuthInterceptor(String domain, String clientId, String clientSecret, AuthTokenProvider authTokenProvider) {
        this.domain = domain;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authTokenProvider = authTokenProvider;
    }

    private String getToken() throws AuthenticationException {
        if (token == null) {
            token = authTokenProvider.obtainToken(domain, clientId, clientSecret);
        }
        return token;
    }

    @Override
    public void process(HttpRequest request, HttpContext context) throws AuthenticationException {
        request.addHeader(new BasicHeader("Authorization", "Bearer " + getToken()));
        request.addHeader(new BasicHeader("client_id", clientId));
    }
}
