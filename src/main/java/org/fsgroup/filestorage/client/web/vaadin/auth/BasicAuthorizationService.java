package org.fsgroup.filestorage.client.web.vaadin.auth;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BasicAuthorizationService implements AuthorizationService {

    @Resource
    private AuthenticationService authenticationService;

    @Override
    public String authString() {
        Credentials credentials = authenticationService.getUserCredentials();
        return String.format("Basic %s", basicAuth(credentials.getUsername(), credentials.getPassword()));
    }

    private static String basicAuth(String username, String password) {
        return encodeString(String.format("%s:%s", username, password));
    }

    private static String encodeString(String s) {
        return new String(Base64.encodeBase64(s.getBytes()));
    }
}
