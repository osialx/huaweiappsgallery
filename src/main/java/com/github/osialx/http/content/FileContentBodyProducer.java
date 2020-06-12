package com.github.osialx.http.content;

import org.apache.http.HttpException;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.io.IOException;

public class FileContentBodyProducer implements ContentBodyProducer {

    private final String path;

    public FileContentBodyProducer(String path) {
        this.path = path;
    }

    @Override
    public ContentBody getContent() throws HttpException, IOException {
        return new FileBody(new File(path));
    }
}
