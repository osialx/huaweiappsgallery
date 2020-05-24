package com.github.osialx.http.model;

public class UploadFileRef {

    private String authCode;

    private String uploadUrl;

    public String getAuthCode() {
        return authCode;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }
}
