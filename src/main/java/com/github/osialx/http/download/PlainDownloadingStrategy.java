package com.github.osialx.http.download;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Collectors;

public class PlainDownloadingStrategy implements DownloadingStrategy {

    @Override
    public InputStream download(URI uri) throws HttpException, IOException {
        HttpResponse response = HttpClients.createDefault().execute(new HttpGet(uri));

        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            String reason = getBodyAsString(response);
            throw new HttpException("Failed with code " + statusCode + ". Reason: " + reason);
        }

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return new BufferedInputStream(entity.getContent());
        }

        throw new HttpException("failed to download " + uri);
    }

    private String getBodyAsString(HttpResponse response) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            return reader.lines().collect(Collectors.joining("\n"));
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }
}
