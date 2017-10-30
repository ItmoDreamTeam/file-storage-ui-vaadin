package org.fsgroup.filestorage.client.web.vaadin.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class BasicAuthorization implements Authorization {

    @Override
    public String authString(UserCredentials userCredentials) {
        return "Basic " + basicAuth(userCredentials.getUsername(), userCredentials.getPassword());
    }

    private static String basicAuth(String username, String password) {
        return encodeString(String.format("%s:%s", username, password));
    }

    private static String encodeString(String s) {
        return new String(Base64.encodeBase64(s.getBytes()));
    }
}
