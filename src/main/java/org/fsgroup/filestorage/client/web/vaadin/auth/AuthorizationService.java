package org.fsgroup.filestorage.client.web.vaadin.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public interface AuthorizationService {

    String authString();

    default void addAuthHeader(HttpHeaders httpHeaders) {
        httpHeaders.add(HttpHeaders.AUTHORIZATION, authString());
    }
}
