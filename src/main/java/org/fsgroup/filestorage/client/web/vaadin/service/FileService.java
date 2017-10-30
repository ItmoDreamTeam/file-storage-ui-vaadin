package org.fsgroup.filestorage.client.web.vaadin.service;

import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;
import org.springframework.stereotype.Service;

@Service
public interface FileService {

    void upload(RequestResults<?> requestResults, UserCredentials userCredentials);

    void download(RequestResults<?> requestResults, UserCredentials userCredentials, int fileId);

    void edit(RequestResults<?> requestResults, UserCredentials userCredentials, int fileId, String name);

    void delete(RequestResults<?> requestResults, UserCredentials userCredentials, int fileId);
}
