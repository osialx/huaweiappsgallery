## About
This is a java library for publishing android app in Huawei Apps Gallery.

Official API documentation [here](https://developer.huawei.com/consumer/en/doc/development/AppGallery-connect-References/agcapi-appid-list_v2)

## Usage

```$java
HuaweiAppsGalleryApiClient appsGalleryApiClient = new HuaweiApiFactory().appsGallery(clientId, clientSecret);

List<FileInfo> files = appsGalleryApiClient.executeRequest(new UploadFileRequest(
    appId,
    Suffix.APK,
    URI.create("file:///build.apk"),
    appsGalleryApiClient,
    new PlainDownloadingStrategy()
));

FileInfoConverter converter = new FileInfoConverter();
List<PublishFileInfo> publishFileInfos = files.stream()
    .map(converter::convert)
    .peek(i -> i.setFileName("build.apk"))
    .collect(Collectors.toList());

JSONObject updateFiles = appsGalleryApiClient.executeRequest(new UpdateFileInfoRequest(appId, publishFileInfos));

String notes = "Release notes here";
JSONObject submit = appsGalleryApiClient.executeRequest(new SubmitApiRequest(appId, notes));
```