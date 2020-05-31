package com.github.osialx.http.model;

public class PublishFileInfo {

    private String fileName;

    private String fileDestUrl;

    private String imageResolution;

    private String imageResolutionSingature;

    private int size;

    public String getFileDestUrl() {
        return fileDestUrl;
    }

    public void setFileDestUrl(String fileDestUrl) {
        this.fileDestUrl = fileDestUrl;
    }

    public String getImageResolution() {
        return imageResolution;
    }

    public void setImageResolution(String imageResolution) {
        this.imageResolution = imageResolution;
    }

    public String getImageResolutionSingature() {
        return imageResolutionSingature;
    }

    public void setImageResolutionSingature(String imageResolutionSingature) {
        this.imageResolutionSingature = imageResolutionSingature;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
