package org.fsgroup.filestorage.client.web.vaadin.auth;

import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    boolean isUserAuthenticated();

    Credentials getUserCredentials();

    void authenticate(Credentials credentials);

    void clear();
}
