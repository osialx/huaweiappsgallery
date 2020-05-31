package com.github.osialx.http.api;

import com.alibaba.fastjson.JSONObject;
import com.github.osialx.http.ApiRequest;
import com.github.osialx.http.JsonApiResponseProcessor;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import java.io.IOException;

public class SubmitApiRequest implements ApiRequest<JSONObject> {

    private final String appId;
    private final String remark;

    public SubmitApiRequest(String appId, String remark) {
        this.appId = appId;
        this.remark = remark;
    }

    @Override
    public HttpUriRequest buildHttpRequest(String baseUrl) {
        RequestBuilder builder = RequestBuilder.create(HttpPost.METHOD_NAME);
        builder
                .setUri(baseUrl + "/publish/v2/app-submit")
                .addParameter("appId", appId);

        if (remark != null && remark.length() > 0) {
            builder.addParameter("remark", remark);
        }

        return builder.build();
    }

    @Override
    public JSONObject process(HttpResponse response) throws IOException, HttpException {
        return new JsonApiResponseProcessor<JSONObject>(null).process(response);
    }
}
