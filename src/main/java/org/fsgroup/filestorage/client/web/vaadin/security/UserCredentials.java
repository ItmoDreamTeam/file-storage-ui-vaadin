package org.fsgroup.filestorage.client.web.vaadin.security;

public class UserCredentials {

    private final String username;
    private final String password;

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
