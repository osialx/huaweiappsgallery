package com.github.osialx.http.content;

import org.apache.http.HttpException;
import org.apache.http.entity.mime.content.ContentBody;

import java.io.IOException;

public interface ContentBodyProducer {
    ContentBody getContent() throws HttpException, IOException;
}
