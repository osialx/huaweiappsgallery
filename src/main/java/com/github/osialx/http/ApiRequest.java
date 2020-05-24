package com.github.osialx.http;

import org.apache.http.HttpException;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;

public interface ApiRequest<T> extends ApiResponseProcessor<T> {

    HttpUriRequest buildHttpRequest(String baseUrl) throws IOException, HttpException;
}
