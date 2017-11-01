package org.fsgroup.filestorage.client.web.vaadin.rest;

import org.apache.log4j.Logger;
import org.fsgroup.filestorage.client.web.vaadin.auth.AuthenticationService;
import org.fsgroup.filestorage.client.web.vaadin.auth.AuthorizationService;
import org.fsgroup.filestorage.client.web.vaadin.auth.Credentials;
import org.fsgroup.filestorage.client.web.vaadin.model.User;
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
    private AuthenticationService authenticationService;

    @Resource
    private AuthorizationService authorizationService;

    @Resource
    private RequestExecutor requestExecutor;

    @Override
    public void signUp(RequestResults<?> requestResults, String username, String password) {
        log.info(String.format("Sign up: %s", username));
        String url = urls.signUp(username, password);
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.POST, url);
        requestExecutor.execute(request);
        log.info("Sign up done");
    }

    @Override
    public void get(RequestResults<User> requestResults) {
        Credentials credentials = authenticationService.getUserCredentials();
        log.info(String.format("Get user: %s", credentials.getUsername()));
        String url = urls.user(credentials.getUsername());
        RestRequest<User> request = new RestRequest<>(requestResults, HttpMethod.GET, url);
        authorizationService.addAuthHeader(request.getHeaders());
        requestExecutor.execute(request);
        log.info("Get user done");
    }

    @Override
    public void edit(RequestResults<?> requestResults, String password) {
        Credentials credentials = authenticationService.getUserCredentials();
        log.info(String.format("Edit user: %s", credentials.getUsername()));
        String url = urls.editUser(credentials.getUsername(), password);
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.PUT, url);
        authorizationService.addAuthHeader(request.getHeaders());
        requestExecutor.execute(request);
        log.info("Edit user done");
    }

    @Override
    public void delete(RequestResults<?> requestResults) {
        Credentials credentials = authenticationService.getUserCredentials();
        log.info(String.format("Delete user: %s", credentials.getUsername()));
        String url = urls.user(credentials.getUsername());
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.DELETE, url);
        authorizationService.addAuthHeader(request.getHeaders());
        requestExecutor.execute(request);
        log.info("Delete user done");
    }
}
