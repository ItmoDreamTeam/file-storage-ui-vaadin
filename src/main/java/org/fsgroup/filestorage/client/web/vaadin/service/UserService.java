package org.fsgroup.filestorage.client.web.vaadin.service;

import org.fsgroup.filestorage.client.web.vaadin.model.User;
import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void signUp(RequestResults<?> requestResults, String username, String password);

    void get(RequestResults<User> requestResults, UserCredentials userCredentials);

    void edit(RequestResults<?> requestResults, UserCredentials userCredentials, String password);

    void delete(RequestResults<?> requestResults, UserCredentials userCredentials);
}
