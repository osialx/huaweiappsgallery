package com.github.osialx.http.api;

import com.alibaba.fastjson.JSONObject;
import com.github.osialx.http.SimpleApiRequest;
import org.apache.http.client.methods.HttpPost;

public class SubmitApiRequest extends SimpleApiRequest<JSONObject> {

    public SubmitApiRequest(String appId) {
        super(
                baseUrl -> new HttpPost(baseUrl + "/publish/v2/app-submit?appid=" + appId),
                null
        );
    }
}
