package com.github.osialx.http;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;

import java.io.IOException;

public interface ApiResponseProcessor<T> {

    T process(HttpResponse response) throws IOException, HttpException;
}