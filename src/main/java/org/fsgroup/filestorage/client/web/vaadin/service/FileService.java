package org.fsgroup.filestorage.client.web.vaadin.service;

import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface FileService {

    void upload(RequestResults<?> requestResults, UserCredentials userCredentials, File file);

    //TODO
    void download(RequestResults<?> requestResults, UserCredentials userCredentials, int fileId);

    void delete(RequestResults<?> requestResults, UserCredentials userCredentials, int fileId);
}
