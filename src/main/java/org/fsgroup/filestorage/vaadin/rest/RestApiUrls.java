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

    public String user(String username) {
        return String.format("%s/user/%s", restRoot, username);
    }

    public String file(String username) {
        return String.format("%s/user/%s/file", restRoot, username);
    }

    public String file(String username, int fileId) {
        return String.format("%s/user/%s/file/%d", restRoot, username, fileId);
    }
}
