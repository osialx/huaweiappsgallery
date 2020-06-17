package com.github.osialx.http;

import com.google.gson.Gson;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class JsonApiResponseProcessor<T> implements ApiResponseProcessor<T> {

    private final Type type;
    private final Gson gson;

    public JsonApiResponseProcessor(final Type type) {
        this.type = type;
        this.gson = new Gson();
    }

    @Override
    public T process(HttpResponse response) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), Consts.UTF_8));
        return gson.fromJson(br, type);
    }
}
