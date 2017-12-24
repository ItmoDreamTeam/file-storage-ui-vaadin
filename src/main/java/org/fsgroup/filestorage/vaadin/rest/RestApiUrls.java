package org.fsgroup.filestorage.vaadin.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RestApiUrls {

    @Value("${rest.root}")
    private String restRoot;

    public String signUp() {
        return String.format("%s/signup", restRoot);
    }

    public String user() {
        return String.format("%s/profile", restRoot);
    }

    public String file() {
        return String.format("%s/files", restRoot);
    }

    public String file(int fileId) {
        return String.format("%s/files/%d", restRoot, fileId);
    }
}
