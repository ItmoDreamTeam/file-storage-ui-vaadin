package org.fsgroup.filestorage.client.web.vaadin.service;

import org.fsgroup.filestorage.client.web.vaadin.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void signUp(RequestResults<?> requestResults, String username, String password);

    void get(RequestResults<User> requestResults);

    void edit(RequestResults<?> requestResults, String password);

    void delete(RequestResults<?> requestResults);
}
