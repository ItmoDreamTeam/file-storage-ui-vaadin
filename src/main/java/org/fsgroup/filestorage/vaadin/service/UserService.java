package org.fsgroup.filestorage.vaadin.service;

import org.fsgroup.filestorage.vaadin.model.User;

public interface UserService {

    void signUp(RequestResults<?> requestResults, String username, String password);

    void get(RequestResults<User> requestResults);

    void edit(RequestResults<?> requestResults, String password);

    void delete(RequestResults<?> requestResults);
}
