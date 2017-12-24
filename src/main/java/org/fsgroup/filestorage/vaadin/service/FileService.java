package org.fsgroup.filestorage.vaadin.service;

import java.io.InputStream;

public interface FileService {

    InputStream download(OnRequestFail onRequestFail, int fileId);

    void upload(RequestResults<?> requestResults, String filename, InputStream fileStream);

    void delete(RequestResults<?> requestResults, int fileId);
}
