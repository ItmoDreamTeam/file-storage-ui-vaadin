package org.fsgroup.filestorage.client.web.vaadin.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RestApiUrls {

    @Value("${rest.root}")
    private String restRoot;

    public String signUp(String username, String password) {
        return String.format("%s/signup?username=%s&password=%s", restRoot, username, password);
    }

    public String user(String username) {
        return String.format("%s/user/%s", restRoot, username);
    }

    public String editUser(String username, String newPassword) {
        return String.format("%s/user/%s?password=%s", restRoot, username, newPassword);
    }

    public String file(String username) {
        return String.format("%s/user/%s/file", restRoot, username);
    }

    public String file(String username, int fileId) {
        return String.format("%s/user/%s/file/%d", restRoot, username, fileId);
    }
}
