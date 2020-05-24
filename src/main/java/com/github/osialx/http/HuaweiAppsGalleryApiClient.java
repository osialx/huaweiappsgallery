package com.github.osialx.http;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

public class HuaweiAppsGalleryApiClient {

    private final String baseUrl;
    private final HttpClient httpClient;

    public HuaweiAppsGalleryApiClient(String baseUrl, CloseableHttpClient httpClient) {
        this.baseUrl = baseUrl;
        this.httpClient = httpClient;
    }

    public <T> T executeRequest(ApiRequest<T> apiRequest) throws IOException, HttpException {
        HttpResponse httpResponse = null;
        try {
            HttpUriRequest request = apiRequest.buildHttpRequest(baseUrl);
            httpResponse = httpClient.execute(request);
            final int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new HttpException("Http code " + statusCode);
            }

            return apiRequest.process(httpResponse);
        } finally {
            HttpClientUtils.closeQuietly(httpResponse);
        }
    }
}
