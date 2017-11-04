package org.fsgroup.filestorage.client.web.vaadin.service;

import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public interface FileService {

    InputStream download(OnRequestFail onRequestFail, int fileId);

    void upload(RequestResults<?> requestResults, String filename, InputStream fileStream);

    void delete(RequestResults<?> requestResults, int fileId);
}
