package com.github.osialx.http.content;

import com.github.osialx.ArrayUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Collectors;

public class RemoteContentBodyProducer implements ContentBodyProducer {

    private final URI uri;

    public RemoteContentBodyProducer(URI uri) {
        this.uri = uri;
    }

    @Override
    public ContentBody getContent() throws HttpException, IOException {
        String last = ArrayUtils.getLast(uri.getPath().split("/"));
        return new InputStreamBody(getRemoteStream(), last);
    }

    private InputStream getRemoteStream() throws IOException, HttpException {
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
