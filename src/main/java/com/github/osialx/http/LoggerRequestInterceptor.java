package com.github.osialx.http;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.logging.Logger;

class LoggerRequestInterceptor implements HttpRequestInterceptor, HttpResponseInterceptor {

    @Override
    public void process(HttpRequest request, HttpContext context) {
        StringBuilder loggable = new StringBuilder(String.format("HTTP %s request %s\n", request.getRequestLine().getMethod(), ((HttpUriRequest)request).getURI()));
        appendHeaders(loggable, request.getAllHeaders());
        Logger.getLogger(getClass().getName()).info(loggable.toString());
    }

    @Override
    public void process(HttpResponse response, HttpContext context) {
        StringBuilder loggable = new StringBuilder(String.format("HTTP response %d\n", response.getStatusLine().getStatusCode()));
        appendHeaders(loggable, response.getAllHeaders());
        Logger.getLogger(getClass().getName()).info(loggable.toString());
    }

    private void appendHeaders(StringBuilder loggable, Header[] allHeaders) {
        for (Header header : allHeaders) {
            String name = header.getName();
            String value = header.getValue();
            if ("Authorization".equals(name)) {
                value = "********";
            }
            loggable.append(String.format("%s: %s\n", name, value));
        }
    }
}
