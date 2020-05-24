package com.github.osialx.http.download;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class PlainDownloadingStrategy implements DownloadingStrategy {

    @Override
    public InputStream download(URI uri) throws HttpException, IOException {
        HttpResponse response = HttpClients.createDefault().execute(new HttpGet(uri));

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return new BufferedInputStream(entity.getContent());
        }

        throw new HttpException("failed to download " + uri);
    }
}
