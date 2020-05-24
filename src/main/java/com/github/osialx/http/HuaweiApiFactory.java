package com.github.osialx.http;

import com.github.osialx.auth.AuthInterceptor;
import com.github.osialx.auth.AuthTokenProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HuaweiApiFactory {

    private static final String DOMAIN = "https://connect-api.cloud.huawei.com/api/";

    public HuaweiAppsGalleryApiClient appsGallery(String clientId, String clientSecret) {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .addInterceptorFirst(new AuthInterceptor(DOMAIN, clientId, clientSecret, new AuthTokenProvider()))
                .build();
        return new HuaweiAppsGalleryApiClient(DOMAIN, httpClient);
    }
}
