package com.github.osialx.http;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.function.Function;

public class SimpleApiRequest<T> implements ApiRequest<T> {

    private final Function<String, HttpUriRequest> httpUriRequestSupplier;
    private final ApiResponseProcessor<T> apiResponseProcessor;

    public SimpleApiRequest(final Function<String, HttpUriRequest> httpUriRequestSupplier, Type type) {
        this.httpUriRequestSupplier = httpUriRequestSupplier;
        this.apiResponseProcessor = new JsonApiResponseProcessor<>(type);
    }

    @Override
    public HttpUriRequest buildHttpRequest(String baseUrl) {
        return httpUriRequestSupplier.apply(baseUrl);
    }

    @Override
    public T process(HttpResponse response) throws IOException, HttpException {
        return apiResponseProcessor.process(response);
    }
}
