package org.fsgroup.filestorage.client.web.vaadin.rest;

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

    @Resource
    private RestApiUrls urls;

    @Resource
    private RestAuthorization auth;

    @Resource
    private RequestExecutor requestExecutor;

    @Override
    public void signUp(RequestResults<?> requestResults, String username, String password) {
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.POST, urls.signUp());
        request.addParameter("username", username);
        request.addParameter("password", password);
        requestExecutor.execute(request);
    }

    @Override
    public void get(RequestResults<User> requestResults, UserCredentials userCredentials) {
        RestRequest<User> request = new RestRequest<>(requestResults, HttpMethod.GET, urls.user(userCredentials.getUsername()));
        auth.addAuthHeader(request, userCredentials);
        requestExecutor.execute(request);
    }

    @Override
    public void edit(RequestResults<?> requestResults, UserCredentials userCredentials, String password) {
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.PUT, urls.user(userCredentials.getUsername()));
        request.addParameter("password", password);
        auth.addAuthHeader(request, userCredentials);
        requestExecutor.execute(request);
    }

    @Override
    public void delete(RequestResults<?> requestResults, UserCredentials userCredentials) {
        RestRequest<?> request = new RestRequest<>(requestResults, HttpMethod.DELETE, urls.user(userCredentials.getUsername()));
        auth.addAuthHeader(request, userCredentials);
        requestExecutor.execute(request);
    }
}
