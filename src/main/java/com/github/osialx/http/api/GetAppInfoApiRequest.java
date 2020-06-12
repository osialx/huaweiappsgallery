package com.github.osialx.http.api;

import com.github.osialx.http.SimpleApiRequest;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.HttpGet;

public class GetAppInfoApiRequest extends SimpleApiRequest<JsonObject> {
    public GetAppInfoApiRequest(String appId, String lang) {
        super(
                baseUrl -> new HttpGet(baseUrl + "/publish/v2/app-info?appid=" + appId + "&lang=" + lang),
                null
        );
    }
}
