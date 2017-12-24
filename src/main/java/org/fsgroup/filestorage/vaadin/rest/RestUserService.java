package org.fsgroup.filestorage.vaadin.rest;

import org.fsgroup.filestorage.vaadin.auth.AuthenticationService;
import org.fsgroup.filestorage.vaadin.auth.AuthorizationService;
import org.fsgroup.filestorage.vaadin.auth.Credentials;
import org.fsgroup.filestorage.vaadin.model.User;
import org.fsgroup.filestorage.vaadin.service.RequestExecutor;
import org.fsgroup.filestorage.vaadin.service.RequestResults;
import org.fsgroup.filestorage.vaadin.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;

@Service
public class RestUserService implements UserService {

    private static final Logger log = LoggerFactory.getLogger(RestUserService.class);

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
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.POST, urls.signUp());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);
        request.setBody(params);

        requestExecutor.execute(request);
        log.info("Sign up done");
    }

    @Override
    public void get(RequestResults<User> requestResults) {
        Credentials credentials = authenticationService.getUserCredentials();
        log.info(String.format("Get user: %s", credentials.getUsername()));
        RestRequest<User> request = new RestRequest<>(requestResults, HttpMethod.GET, urls.user(credentials.getUsername()));
        authorizationService.addAuthHeader(request.getHeaders());
        requestExecutor.execute(request);
        log.info("Get user done");
    }

    @Override
    public void edit(RequestResults<?> requestResults, String password) {
        Credentials credentials = authenticationService.getUserCredentials();
        log.info(String.format("Edit user: %s", credentials.getUsername()));
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.PUT, urls.user(credentials.getUsername()));
        authorizationService.addAuthHeader(request.getHeaders());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("password", password);
        request.setBody(params);

        requestExecutor.execute(request);
        log.info("Edit user done");
    }

    @Override
    public void delete(RequestResults<?> requestResults) {
        Credentials credentials = authenticationService.getUserCredentials();
        log.info(String.format("Delete user: %s", credentials.getUsername()));
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.DELETE, urls.user(credentials.getUsername()));
        authorizationService.addAuthHeader(request.getHeaders());
        requestExecutor.execute(request);
        log.info("Delete user done");
    }
}
