package org.fsgroup.filestorage.vaadin.auth;

public interface AuthenticationService {

    boolean isUserAuthenticated();

    Credentials getUserCredentials();

    void authenticate(Credentials credentials);

    void clear();
}
