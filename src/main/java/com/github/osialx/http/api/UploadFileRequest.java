package com.github.osialx.http.api;

import com.github.osialx.ArrayUtils;
import com.github.osialx.http.ApiRequest;
import com.github.osialx.http.HuaweiAppsGalleryApiClient;
import com.github.osialx.http.JsonApiResponseProcessor;
import com.github.osialx.http.download.DownloadingStrategy;
import com.github.osialx.http.model.FileInfo;
import com.github.osialx.http.model.FileServerOriResult;
import com.github.osialx.http.model.Suffix;
import com.github.osialx.http.model.UploadFileRef;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

public class UploadFileRequest implements ApiRequest<List<FileInfo>> {

    private final String appId;
    private final Suffix suffix;
    private final URI path;
    private final HuaweiAppsGalleryApiClient client;
    private final DownloadingStrategy downloadingStrategy;

    public UploadFileRequest(String appId, Suffix suffix, URI path,
                             HuaweiAppsGalleryApiClient client, DownloadingStrategy downloadingStrategy) {
        this.appId = appId;
        this.suffix = suffix;
        this.path = path;
        this.client = client;
        this.downloadingStrategy = downloadingStrategy;
    }

    @Override
    public HttpUriRequest buildHttpRequest(String baseUrl) throws IOException, HttpException {
        ContentBody bin = getContentBody(path);

        UploadFileRef fileUploadingMeta = client.executeRequest(new GetFileUploadingLinkRequest(appId, suffix));

        // Construct a POST request.
        HttpPost post = new HttpPost(fileUploadingMeta.getUploadUrl());
        post.addHeader("accept", "application/json");
        post.setEntity(MultipartEntityBuilder.create()
                .addPart("file", bin)
                .addTextBody("authCode", fileUploadingMeta.getAuthCode())
                .addTextBody("fileCount", "1")
                .addTextBody("parseType", "1")
                .addTextBody("name", "build.apk")
                .build());

        return post;
    }

    private ContentBody getContentBody(final URI uri) throws IOException, HttpException {
        if (uri == null) {
            throw new IllegalArgumentException("uri is null");
        }

        final String scheme = uri.getScheme();
        if (scheme == null) {
            throw new IllegalArgumentException("uri scheme is null");
        }
        switch (scheme) {
            case "file":
                return new FileBody(new File(uri));
            case "http":
            case "https":
                return createRemoteFileContentBody(uri);
            default:
                throw new IllegalArgumentException("unsupported protocol " + uri);
        }
    }

    private InputStreamBody createRemoteFileContentBody(URI uri) throws IOException, HttpException {
        InputStream inputStream = downloadingStrategy.download(uri);
        String last = ArrayUtils.getLast(uri.getPath().split("/"));
        return new InputStreamBody(inputStream, last);
    }

    @Override
    public List<FileInfo> process(HttpResponse response) throws IOException, HttpException {
        FileServerOriResult result = new JsonApiResponseProcessor<FileServerOriResult>(FileServerOriResult.class)
                .process(response);

        if (!"0".equals(result.getResult().getResultCode())) {
            throw new HttpException("Failed to load file. Reason: " + result);
        }
        return result.getResult().getUploadFileRsp().getFileInfoList();
    }
}
