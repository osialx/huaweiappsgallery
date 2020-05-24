package com.github.osialx.http.api;

import com.github.osialx.http.SimpleApiRequest;
import com.github.osialx.http.model.Suffix;
import com.github.osialx.http.model.UploadFileRef;
import org.apache.http.client.methods.HttpGet;

public class GetFileUploadingLinkRequest extends SimpleApiRequest<UploadFileRef> {

    public GetFileUploadingLinkRequest(String appId, Suffix suffix) {
        super(
                baseUrl -> new HttpGet(baseUrl + "/publish/v2/upload-url?appId=" + appId + "&suffix=" + suffix.name().toLowerCase()),
                UploadFileRef.class
        );
    }
}
