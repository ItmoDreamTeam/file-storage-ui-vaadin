package org.fsgroup.filestorage.client.web.vaadin.service;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface FileService {

    void upload(RequestResults<?> requestResults, File file);

    //TODO
    void download(RequestResults<?> requestResults, int fileId);

    void delete(RequestResults<?> requestResults, int fileId);
}
