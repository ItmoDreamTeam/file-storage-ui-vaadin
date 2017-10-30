package org.fsgroup.filestorage.client.web.vaadin.rest;

import org.fsgroup.filestorage.client.web.vaadin.security.Authorization;
import org.fsgroup.filestorage.client.web.vaadin.security.UserCredentials;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RestAuthorization {

    @Resource
    private Authorization authorization;

    public <T> void addAuthHeader(RestRequest<T> request, UserCredentials userCredentials) {
        request.addHeader("Authorization", authorization.authString(userCredentials));
    }
}
