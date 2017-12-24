package org.fsgroup.filestorage.vaadin.auth;

import org.springframework.http.HttpHeaders;

public interface AuthorizationService {

    String authString();

    default void addAuthHeader(HttpHeaders httpHeaders) {
        httpHeaders.add(HttpHeaders.AUTHORIZATION, authString());
    }
}
