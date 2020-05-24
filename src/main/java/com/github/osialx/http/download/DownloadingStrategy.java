package com.github.osialx.http.download;

import org.apache.http.HttpException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public interface DownloadingStrategy {
    InputStream download(URI uri) throws HttpException, IOException;
}
