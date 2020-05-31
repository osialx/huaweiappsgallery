package com.github.osialx.http.model;

public class FileInfoConverter {

    public PublishFileInfo convert(FileInfo fileInfo) {
        PublishFileInfo publishFileInfo = new PublishFileInfo();
        publishFileInfo.setSize(fileInfo.getSize());
        publishFileInfo.setFileDestUrl(fileInfo.getFileDestUlr());
        publishFileInfo.setImageResolution(fileInfo.getImageResolution());
        publishFileInfo.setImageResolutionSingature(fileInfo.getImageResolutionSingature());
        return publishFileInfo;
    }
}
