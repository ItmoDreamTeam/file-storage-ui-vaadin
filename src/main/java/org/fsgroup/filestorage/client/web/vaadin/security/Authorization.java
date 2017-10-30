package org.fsgroup.filestorage.client.web.vaadin.security;

import org.springframework.stereotype.Service;

@Service
public interface Authorization {

    String authString(UserCredentials userCredentials);
}
