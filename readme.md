## About
This is a java library for publishing android app in Huawei Apps Gallery.

Official API documentation [here](//https://developer.huawei.com/consumer/en/doc/development/AppGallery-connect-References/agcapi-appid-list_v2)

## Usage

```$java
HuaweiAppsGalleryApiClient appsGalleryApiClient = new HuaweiApiFactory().appsGallery(clientId, clientSecret);

JSONObject appInfo = appsGalleryApiClient.executeRequest(new GetAppInfoApiRequest(appId, "ru-RU"));

List<FileInfo> files = appsGalleryApiClient.executeRequest(new UploadFileRequest(
    appId,
    Suffix.APK,
    URI.create("http://teamcity.your-company-domain.com/path/to/artifact.apk"),
    appsGalleryApiClient,
    new PlainDownloadingStrategy()
));

appsGalleryApiClient.executeRequest(new SubmitApiRequest(appId));
```