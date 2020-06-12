package com.github.osialx.http.api;

import com.github.osialx.http.ApiRequest;
import com.github.osialx.http.HuaweiAppsGalleryApiClient;
import com.github.osialx.http.JsonApiResponseProcessor;
import com.github.osialx.http.content.ContentBodyProducer;
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

import java.io.IOException;
import java.util.List;

public class UploadFileRequest implements ApiRequest<List<FileInfo>> {

    private final String appId;
    private final ContentBodyProducer contentProducer;
    private final Suffix suffix;
    private final HuaweiAppsGalleryApiClient client;

    public UploadFileRequest(String appId, ContentBodyProducer contentProducer, Suffix suffix, HuaweiAppsGalleryApiClient client) {
        this.appId = appId;
        this.contentProducer = contentProducer;
        this.suffix = suffix;
        this.client = client;
    }

    @Override
    public HttpUriRequest buildHttpRequest(String baseUrl) throws IOException, HttpException {
        UploadFileRef fileUploadingMeta = client.executeRequest(new GetFileUploadingLinkRequest(appId, suffix));

        ContentBody content = contentProducer.getContent();

        // Construct a POST request.
        HttpPost post = new HttpPost(fileUploadingMeta.getUploadUrl());
        post.addHeader("accept", "application/json");
        post.setEntity(MultipartEntityBuilder.create()
                .addPart("file", content)
                .addTextBody("authCode", fileUploadingMeta.getAuthCode())
                .addTextBody("fileCount", "1")
                .addTextBody("parseType", "1")
                .addTextBody("name", "build.apk")
                .build());

        return post;
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
