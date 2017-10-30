package org.fsgroup.filestorage.client.web.vaadin.rest;

import org.apache.log4j.Logger;
import org.fsgroup.filestorage.client.web.vaadin.model.User;
import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestExecutor;
import org.fsgroup.filestorage.client.web.vaadin.service.RequestResults;
import org.fsgroup.filestorage.client.web.vaadin.service.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RestUserService implements UserService {

    private static final Logger log = Logger.getLogger(RestUserService.class);

    @Resource
    private RestApiUrls urls;

    @Resource
    private RestAuthorization auth;

    @Resource
    private RequestExecutor requestExecutor;

    @Override
    public void signUp(RequestResults<?> requestResults, String username, String password) {
        log.info(String.format("Sign up: %s", username));
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.POST, urls.signUp());
        request.addParameter("username", username);
        request.addParameter("password", password);
        requestExecutor.execute(request);
        log.info("Sign up done");
    }

    @Override
    public void get(RequestResults<User> requestResults, UserCredentials userCredentials) {
        log.info(String.format("Get user: %s", userCredentials.getUsername()));
        RestRequest<User> request = new RestRequest<>(requestResults, HttpMethod.GET, urls.user(userCredentials.getUsername()));
        auth.addAuthHeader(request, userCredentials);
        requestExecutor.execute(request);
        log.info("Get user done");
    }

    @Override
    public void edit(RequestResults<?> requestResults, UserCredentials userCredentials, String password) {
        log.info(String.format("Edit user: %s", userCredentials.getUsername()));
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.PUT, urls.user(userCredentials.getUsername()));
        request.addParameter("password", password);
        auth.addAuthHeader(request, userCredentials);
        requestExecutor.execute(request);
        log.info("Edit user done");
    }

    @Override
    public void delete(RequestResults<?> requestResults, UserCredentials userCredentials) {
        log.info(String.format("Delete user: %s", userCredentials.getUsername()));
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.DELETE, urls.user(userCredentials.getUsername()));
        auth.addAuthHeader(request, userCredentials);
        requestExecutor.execute(request);
        log.info("Delete user done");
    }
}
