package org.fsgroup.filestorage.vaadin.auth;

import com.vaadin.server.VaadinSession;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Override
    public boolean isUserAuthenticated() {
        return VaadinSession.getCurrent().getAttribute(Credentials.class) != null;
    }

    @Override
    public Credentials getUserCredentials() {
        return VaadinSession.getCurrent().getAttribute(Credentials.class);
    }

    @Override
    public void authenticate(Credentials credentials) {
        VaadinSession.getCurrent().setAttribute(Credentials.class, credentials);
    }

    @Override
    public void clear() {
        VaadinSession.getCurrent().setAttribute(Credentials.class, null);
    }
}
