package com.github.osialx.http.api;

import com.alibaba.fastjson.JSONObject;
import com.github.osialx.http.ApiRequest;
import com.github.osialx.http.JsonApiResponseProcessor;
import com.github.osialx.http.model.PublishFileInfo;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class UpdateFileInfoRequest implements ApiRequest<JSONObject> {

    private final String appId;
    private final List<PublishFileInfo> files;

    public UpdateFileInfoRequest(String appId, List<PublishFileInfo> files) {
        this.appId = appId;
        this.files = files;
    }

    @Override
    public HttpUriRequest buildHttpRequest(String baseUrl) {
        HttpPut put = new HttpPut(baseUrl + "/publish/v2/app-file-info?appId=" + appId);

        JSONObject keyString = new JSONObject();
        keyString.put("files", files);
        keyString.put("fileType", 5);

        StringEntity entity = new StringEntity(keyString.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        put.setEntity(entity);

        return put;
    }

    @Override
    public JSONObject process(HttpResponse response) throws IOException {
        return (JSONObject) new JsonApiResponseProcessor(JSONObject.class).process(response);
    }
}
