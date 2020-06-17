## About
This is a java library for publishing android app in Huawei Apps Gallery.

Official API documentation [here](https://developer.huawei.com/consumer/en/doc/development/AppGallery-connect-References/agcapi-appid-list_v2)

## Usage

```$java
HuaweiAppsGalleryApiClient appsGalleryApiClient = new HuaweiApiFactory().appsGallery(clientId, clientSecret);

        List<FileInfo> files = appsGalleryApiClient.executeRequest(new UploadFileRequest(
                appId,
                new FileContentBodyProducer("build.apk"),
                Suffix.APK,
                appsGalleryApiClient
        ));

        FileInfoConverter converter = new FileInfoConverter();
        List<PublishFileInfo> publishFileInfos = files.stream()
                .map(converter::convert)
                .peek(i -> i.setFileName("build.apk"))
                .collect(Collectors.toList());

        JsonElement updateFiles = appsGalleryApiClient.executeRequest(new UpdateFileInfoRequest(appId, publishFileInfos));

        String notes = "Release notes here";
        JsonElement submit = appsGalleryApiClient.executeRequest(new SubmitApiRequest(appId, notes));
```